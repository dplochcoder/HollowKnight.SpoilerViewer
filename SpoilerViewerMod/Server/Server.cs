﻿using ItemChanger;
using ItemChanger.Internal;
using PurenailCore.SystemUtil;
using RandomizerCore.Logic;
using RandomizerMod.RandomizerData;
using RandomizerMod.RC;
using SpoilerViewerMod.Server.API;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading;

namespace SpoilerViewerMod.Server
{
    using JsonUtil = JsonUtil<SpoilerViewerMod>;

    public class Server
    {
        private readonly RandoModContext ctx;
        private Thread? thread;
        private HttpListener? listener;
        private volatile bool terminated = false;

        public Server(RandoModContext ctx)
        {
            this.ctx = ctx;
        }

        public void RunAsync(int port)
        {
            if (thread != null)
            {
                throw new ArgumentException("Cannot start multiple servers");
            }

            thread = new(() =>
            {
                try
                {
                    RunSync(port);
                }
                catch (Exception e)
                {
                    SpoilerViewerMod.LogError($"Server error: {e}");
                }
            });
            thread.Start();
        }
        
        public void Terminate()
        {
            terminated = true;
            listener?.Abort();
            thread?.Join();
        }

        private void RunSync(int port) {
            string url = $"http://*:{port}/";
            listener = new();
            listener.Prefixes.Add(url);
            listener.Start();

            // Launch the JAR and give it the port number.
            SpoilerViewerMod.Log($"Launching JAR: {SpoilerViewerMod.JarFile}");
            Process process = new();
            process.StartInfo.FileName = "java";
            process.StartInfo.Arguments = $"-jar \"{SpoilerViewerMod.JarFile}\" {port}";
            process.StartInfo.UseShellExecute = false;
            process.StartInfo.CreateNoWindow = true;
            process.Start();

            while (!terminated)
            {
                var ctx = listener.GetContext();
                var req = ctx.Request;
                var resp = ctx.Response;
                if (req.HttpMethod != HttpMethod.Post.Method)
                {
                    resp.StatusCode = 500;
                    resp.Close();
                    continue;
                }

                switch (req.Url.AbsolutePath)
                {
                    case "/getRandoContext":
                        SpoilerViewerMod.Log("Received /getRandoContext request");
                        handleMethod<API.RandoContextRequest, API.RandoContext>(ctx, getRandoContext);
                        break;
                    default:
                        resp.StatusCode = 404;
                        resp.Close();
                        break;
                }
            }
        }

        private delegate Resp RpcMethod<Req, Resp>(Req request);

        private static byte[] SerializeObj<T>(T obj)
        {
            MemoryStream ms = new();
            StreamWriter sw = new(ms);
            JsonUtil.Serialize(obj, sw);
            sw.Flush();
            return ms.ToArray();
        }

        private void handleMethod<Req, Resp>(HttpListenerContext ctx, RpcMethod<Req, Resp> method)
        {
            try
            {
                using StreamReader sr = new(ctx.Request.InputStream);
                var req = JsonUtil.DeserializeFromString<Req>(sr.ReadToEnd());

                var data = SerializeObj(method(req));
                ctx.Response.ContentLength64 = data.Length;
                ctx.Response.OutputStream.Write(data, 0, data.Length);

                SpoilerViewerMod.Log("Successful response");
            }
            catch (Exception e)
            {
                SpoilerViewerMod.LogError($"500 Error: {e}");

                ctx.Response.StatusCode = 500;
                var bytes = Encoding.UTF8.GetBytes(e.Message);
                ctx.Response.OutputStream.Write(bytes, 0, bytes.Length);
            }
            finally
            {
                ctx.Response.Close();
            }
        }

        private API.Location GetLocation(IDictionary<API.LocationName, API.Location> locations, string name)
        {
            API.LocationName locName = new(name);
            if (locations.TryGetValue(locName, out var loc)) return loc;

            loc = new(locName);
            locations[locName] = loc;
            return loc;
        }

        private API.Item GetItem(IDictionary<API.ItemName, API.Item> items, string name)
        {
            API.ItemName itemName = new(name);
            if (items.TryGetValue(itemName, out var item)) return item;

            item = new(itemName);
            items[itemName] = item;
            return item;
        }

        private API.RandoContext getRandoContext(API.RandoContextRequest request)
        {
            Dictionary<API.ItemName, API.Item> items = new();
            Dictionary<API.LocationName, API.Location> locations = new();

            foreach (var gp in ctx.Vanilla ?? new())
            {
                bool isTransition = gp.Location is LogicTransition;

                var apiLoc = GetLocation(locations, gp.Location.Name);
                apiLoc.isTransition = isTransition;
                var fLoc = Finder.GetLocation(gp.Location.Name);
                if (fLoc?.sceneName != null) {
                    apiLoc.mapAreaName ??= Data.GetRoomDef(fLoc.sceneName)?.MapArea;
                    apiLoc.titleAreaName ??= Data.GetRoomDef(fLoc.sceneName)?.TitledArea;
                }

                var apiItem = GetItem(items, gp.Item.Name);
                apiItem.isTransition = isTransition;
                API.ItemPlacementData ipd = new(apiItem.name);
                // TODO: Costs
                apiLoc.itemPlacementDatum.Add(ipd);
            }

            foreach (var p in ctx.itemPlacements ?? new())
            {
                var apiLoc = GetLocation(locations, p.Location.Name);
                apiLoc.mapAreaName ??= p.Location.LocationDef?.MapArea;
                apiLoc.titleAreaName ??= p.Location.LocationDef?.TitledArea;
                
                var apiItem = GetItem(items, p.Item.Name);
                API.ItemPlacementData ipd = new(apiItem.name);
                // TODO: Costs
                apiLoc.itemPlacementDatum.Add(ipd);
            }

            foreach (var p in ctx.transitionPlacements ?? new())
            {
                var apiLoc = GetLocation(locations, p.Source.Name);
                apiLoc.isTransition = true;
                apiLoc.mapAreaName ??= p.Source.TransitionDef?.MapArea;
                apiLoc.titleAreaName ??= p.Source.TransitionDef?.TitledArea;

                var apiItem = GetItem(items, p.Target.Name);
                apiItem.isTransition = true;
                API.ItemPlacementData ipd = new(apiItem.name);
                apiLoc.itemPlacementDatum.Add(ipd);
            }

            API.RandoContext rc = new();
            items.Values.ForEach(i => rc.items.Add(i));
            locations.Values.ForEach(l => rc.locations.Add(l));

            var lm = ctx.LM;
            ProgressionManager pm = new(lm, ctx);
            pm.Add(ctx.InitialProgression);
            pm.mu.DoUpdates();

            Dictionary<ObtainIndices, Logic> logicMap = new();
            foreach (var loc in locations.Values)
            {
                foreach (var oi in loc.obtainIndices)
                {
                    var term = lm.GetTerm(loc.Name);
                    if (term != null)
                    {
                        logicMap[oi] = pm.Has(term.Id) ? Logic.IN_LOGIC : Logic.OUT_OF_LOGIC;
                    }
                    else
                    {
                        SpoilerViewerMod.Log($"Unknown loc: {loc.Name}");
                    }
                }
            }

            logicMap.Keys.ForEach(oi => rc.logic.baseMap.entries.Add(new(oi, logicMap[oi])));

            return rc;
        }
    }
}
