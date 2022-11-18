using Newtonsoft.Json;
using RandomizerMod.RC;
using System;
using System.ComponentModel;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace SpoilerViewerMod.Server
{
    using JsonUtil = PurenailCore.SystemUtil.JsonUtil<SpoilerViewerMod>;

    public class Server
    {
        private readonly RandoModContext context;
        private Thread? thread;

        public Server(RandoModContext context)
        {
            this.context = context;
        }

        private byte[] buffer = new byte[1 << 20];

        public void RunAsync(int port)
        {
            if (thread != null)
            {
                throw new ArgumentException("Cannot start multiple servers");
            }

            IPHostEntry host = Dns.GetHostEntry("localhost");
            var ip = host.AddressList[0];
            IPEndPoint endP = new(ip, port);

            Socket socket = new(ip.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            socket.Bind(endP);
            socket.Listen(4);

            // FIXME: Start JAR
            var handler = socket.Accept();
            while (true)
            {
                var methodName = ReceiveString(handler);
                switch (methodName)
                {
                    case "getMethodContext":
                        handleMethod<API.RandoContextRequest, API.RandoContext>(handler, getRandoContext);
                        break;
                    default:
                        throw new InvalidOperationException($"Unknown command: {methodName}");
                }
            }
        }

        private string ReceiveString(Socket socket)
        {
            int recv = socket.Receive(buffer, 4, SocketFlags.None);
            if (recv != 4)
            {
                throw new InvalidOperationException($"Expected 4 bytes; got {recv}");
            }

            int size = BitConverter.ToInt32(buffer, 0);
            if (size <= 0)
            {
                throw new InvalidOperationException($"Expected bytes; got {recv}");
            }

            recv = socket.Receive(buffer, size, SocketFlags.None);
            if (recv != size)
            {
                throw new InvalidOperationException($"Expected {size} bytes; got {recv}");
            }

            return System.Text.Encoding.UTF8.GetString(buffer, 0, size);
        }

        private delegate Resp RpcMethod<Req, Resp>(Req request);

        private void handleMethod<Req, Resp>(Socket socket, RpcMethod<Req, Resp> method)
        {
            var req = JsonUtil.DeserializeFromString<Req>(ReceiveString(socket));

            // TODO: Error API
            var resp = method(req);

            MemoryStream stream = new();
            using StreamWriter sw = new(stream);
            JsonUtil.Serialize(resp, sw);
            sw.Flush();

            Array.Copy(BitConverter.GetBytes(stream.Length), buffer, 4);
            Array.Copy(stream.GetBuffer(),0, buffer, 4, stream.Length);
            socket.Send(buffer, 0, (int)(stream.Length + 4), SocketFlags.None);
        }

        private API.RandoContext getRandoContext(API.RandoContextRequest request)
        {
            API.RandoContext rc = new();

            return rc;
        }
    }
}
