using RandomizerMod.RC;
using System;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace SpoilerViewerMod.Server
{
    public class Server
    {
        private readonly RandoModContext context;
        private Thread? thread;

        public Server(RandoModContext context)
        {
            this.context = context;
        }

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

            // FIXME: Protocol
        }
    }
}
