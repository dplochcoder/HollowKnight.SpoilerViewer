using RandomizerMod.RC;
using System;
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

            // FIXME
        }
    }
}
