using RandomizerMod.RC;
using System;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading;

namespace SpoilerViewerMod.Server
{
    using JsonUtil = PurenailCore.SystemUtil.JsonUtil<SpoilerViewerMod>;

    public class Server
    {
        private readonly RandoModContext ctx;
        private Thread? thread;

        public Server(RandoModContext ctx)
        {
            this.ctx = ctx;
        }

        private byte[] buffer = new byte[1 << 20];

        public void RunAsync(int port)
        {
            if (thread != null)
            {
                throw new ArgumentException("Cannot start multiple servers");
            }

            thread = new(() => RunSync(port));
            thread.Start();
        }

        private void RunSync(int port) {
            string url = $"http://localhost:{port}/";
            HttpListener listener = new();
            listener.Prefixes.Add(url);
            listener.Start();

            // FIXME: Start JAR
            while (true)
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
            }
            catch (Exception e)
            {
                ctx.Response.StatusCode = 500;
                var bytes = Encoding.UTF8.GetBytes(e.Message);
                ctx.Response.OutputStream.Write(bytes, 0, bytes.Length);
            }
            finally
            {
                ctx.Response.Close();
            }
        }

        private API.RandoContext getRandoContext(API.RandoContextRequest request)
        {
            IndexedList<API.ItemName, API.Item> items = new(i => i.name);
            IndexedList<API.LocationName, API.Location> locations = new(l => l.name);

            foreach (var p in ctx.itemPlacements)
            {

            }
            foreach (var p in ctx.transitionPlacements)
            {

            }

            // TODO: obtains and logic
            API.RandoContext rc = new();

            return rc;
        }
    }
}
