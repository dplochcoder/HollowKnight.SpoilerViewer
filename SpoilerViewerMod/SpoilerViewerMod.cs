using ItemChanger.Internal.Menu;
using Modding;
using RandomizerMod;
using PurenailCore.ModUtil;
using RandomizerMod;
using System.IO;
using RandomizerMod.RC;

namespace SpoilerViewerMod
{
    public class SpoilerViewerMod : Mod, ICustomMenuMod
    {
        public static SpoilerViewerMod Instance { get; private set; }

        private static string ComputeJarFile()
        {
            return Path.Combine(Path.GetDirectoryName(typeof(SpoilerViewerMod).Assembly.Location), "SpoilerViewerApplication.jar");
        }

        public static readonly string JarFile = ComputeJarFile();

        public static readonly string Version = VersionUtil.ComputeVersion<SpoilerViewerMod>(new() { JarFile });

        public override string GetVersion() => Version;

        public SpoilerViewerMod() : base("SpoilerViewerMod")
        {
            Instance = this;
        }

        public override void Initialize() { }

        public bool ToggleButtonInsideMenu => false;

        public MenuScreen GetMenuScreen(MenuScreen modListMenu, ModToggleDelegates? _)
        {
            ModMenuScreenBuilder builder = new(Localization.Localize("Spoiler Viewer"), modListMenu);
            builder.AddButton(Localization.Localize("Open Application"), null, () => LaunchApplication());
            return builder.CreateMenuScreen();
        }

        private void LaunchApplication()
        {
            var ctx = RandomizerMod.RandomizerMod.RS?.Context;
            if (ctx == null) return;

            // TODO: Global settings for port.
            Server.Server server = new(ctx);
            server.RunAsync(6698);
        }
    }
}
