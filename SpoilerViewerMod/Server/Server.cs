using ItemChanger;
using PurenailCore.SystemUtil;
using RandomizerCore.Logic;
using RandomizerMod.RandomizerData;
using RandomizerMod.RC;
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
            string url = $"http://localhost:{port}/";
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

        private void handleMethod<Req, Resp>(HttpListenerContext ctx, RpcMethod<Req, Resp> method)
        {
            try
            {
                using StreamReader sr = new(ctx.Request.InputStream);
                var req = JsonUtil.DeserializeFromString<Req>(sr.ReadToEnd());
                JsonUtil.Serialize(method(req), new StreamWriter(ctx.Response.OutputStream));
                SpoilerViewerMod.Log("Successful response");
            }
            catch (Exception e)
            {
                SpoilerViewerMod.LogError($"500 Error: {e.Message}");
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

            foreach (var gp in ctx.Vanilla)
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

            foreach (var p in ctx.itemPlacements)
            {
                var apiLoc = GetLocation(locations, p.Location.Name);
                apiLoc.mapAreaName ??= p.Location.LocationDef?.MapArea;
                apiLoc.titleAreaName ??= p.Location.LocationDef?.TitledArea;
                
                var apiItem = GetItem(items, p.Item.Name);
                API.ItemPlacementData ipd = new(apiItem.name);
                // TODO: Costs
                apiLoc.itemPlacementDatum.Add(ipd);
            }

            foreach (var p in ctx.transitionPlacements)
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

            // TODO: obtains and logic
            return rc;
        }
    }
}
