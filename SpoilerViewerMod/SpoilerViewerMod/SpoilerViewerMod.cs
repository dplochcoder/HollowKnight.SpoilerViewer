using Modding;
using PurenailCore.ModUtil;

namespace SpoilerViewerMod
{
    public class SpoilerViewerMod : Mod
    {
        public static SpoilerViewerMod Instance { get; private set; }

        public static readonly string Version = VersionUtil.ComputeVersion<SpoilerViewerMod>();

        public override string GetVersion() => Version;

        public SpoilerViewerMod() : base("SpoilerViewerMod")
        {
            Instance = this;
        }

        public override void Initialize() { }
    }
}
