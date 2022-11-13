using Modding;
using System;

namespace SpoilerViewerMod
{
    public class SpoilerViewerModMod : Mod
    {
        private static SpoilerViewerModMod? _instance;

        internal static SpoilerViewerModMod Instance
        {
            get
            {
                if (_instance == null)
                {
                    throw new InvalidOperationException($"An instance of {nameof(SpoilerViewerModMod)} was never constructed");
                }
                return _instance;
            }
        }

        public override string GetVersion() => GetType().Assembly.GetName().Version.ToString();

        public SpoilerViewerModMod() : base("SpoilerViewerMod")
        {
            _instance = this;
        }

        // if you need preloads, you will need to implement GetPreloadNames and use the other signature of Initialize.
        public override void Initialize()
        {
            Log("Initializing");

            // put additional initialization logic here

            Log("Initialized");
        }
    }
}
