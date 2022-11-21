package hkspoilerviewer.gui;

import hkspoilerviewer.api.RandoContext;
import hkspoilerviewer.api.RandoContextRequest;
import hkspoilerviewer.api.RandoServiceInterface;
import hkspoilerviewer.api.RouteIntent;
import hkspoilerviewer.lib.DataProvider;
import hkspoilerviewer.lib.UpdateSession;

public final class RandoContextUpdater implements Runnable {
  private final RandoServiceInterface randoService;
  private final DataProvider<RouteIntent> routeIntent;
  private final DataProvider<RandoContext> randoContext;

  private boolean dirty = true;
  private boolean awaitingResponse = false;

  public RandoContextUpdater(RandoServiceInterface randoService,
      DataProvider<RouteIntent> routeIntent, DataProvider<RandoContext> randoContext) {
    this.randoService = randoService;
    this.routeIntent = routeIntent;
    this.randoContext = randoContext;
  }

  public void start() {
    new Thread(this).start();
  }

  @Override
  public void run() {
    while (true) {
      synchronized (this) {
        while (!dirty) {
          try {
            wait();
          } catch (InterruptedException e) {
            return;
          }
        }

        dirty = false;
      }

      RandoContextRequest request =
          RandoContextRequest.builder().setRouteIntent(routeIntent.get()).build();
      awaitingResponse = true;
      randoService.getRandoContext(request, newCallback());

      synchronized (this) {
        while (awaitingResponse) {
          try {
            wait();
          } catch (InterruptedException e) {
            return;
          }
        }
      }
    }
  }

  private RandoServiceInterface.Callback<RandoContext> newCallback() {
    return new RandoServiceInterface.Callback<RandoContext>() {
      @Override
      public void success(RandoContext response) {
        synchronized (this) {
          awaitingResponse = false;
          notifyAll();
        }

        try (UpdateSession session = new UpdateSession(RandoContextUpdater.this)) {
          randoContext.update(session, response);
        }
      }

      @Override
      public void error(Exception e) {
        // TODO: Error notification
        System.err.println("Error in RandoContextUpdater: " + e.getMessage());
        e.printStackTrace();
      }
    };
  }
}
