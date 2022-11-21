package hkspoilerviewer.main;

import com.google.common.primitives.Ints;
import hkspoilerviewer.gui.Application;
import hkspoilerviewer.lib.HttpRandoServiceImpl;

public final class Main {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Incorrect args; expected [port]");
      System.exit(1);
    }

    Integer port = Ints.tryParse(args[0]);
    if (port == null) {
      System.err.println("Invalid port: " + args[0]);
      System.exit(1);
    }

    HttpRandoServiceImpl randoService = new HttpRandoServiceImpl(port);
    new Application(randoService);
  }
}
