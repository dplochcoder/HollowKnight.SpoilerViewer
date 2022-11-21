package hkspoilerviewer.lib;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hkspoilerviewer.api.RandoContext;
import hkspoilerviewer.api.RandoContextRequest;
import hkspoilerviewer.api.RandoServiceInterface;

public final class HttpRandoServiceImpl implements RandoServiceInterface {

  private final Gson gson = new GsonBuilder().create();

  private final int port;
  private final HttpClient client;

  public HttpRandoServiceImpl(int port) {
    this.port = port;
    this.client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
        .connectTimeout(Duration.ofSeconds(5)).build();
  }

  private <T> void invokeCallback(HttpResponse<String> resp, Class<T> clazz, Callback<T> cb) {
    if (resp == null) {
      return;
    }
    if (resp.statusCode() != 200) {
      cb.error(new Exception(
          String.format("Status code: %d; Error: %s", resp.statusCode(), resp.body())));
      return;
    }

    T obj;
    try {
      obj = gson.fromJson(resp.body(), clazz);
    } catch (Exception e) {
      cb.error(e);
      return;
    }

    cb.success(obj);
  }

  private <A, B> void doRequestAsync(String methodName, A request, Class<B> clazz, Callback<B> cb) {
    String body;
    try {
      body = gson.toJson(request);
    } catch (Exception e) {
      cb.error(e);
      return;
    }

    HttpRequest httpRequest = HttpRequest.newBuilder().POST(BodyPublishers.ofString(body))
        .uri(URI.create(String.format("http://localhost:%d/%s", port, methodName))).build();
    CompletableFuture<HttpResponse<String>> future =
        client.sendAsync(httpRequest, BodyHandlers.ofString(StandardCharsets.UTF_8));
    future.thenAccept(resp -> invokeCallback(resp, clazz, cb));
    future.exceptionally(t -> {
      cb.error(t);
      return null;
    });
  }

  @Override
  public void getRandoContext(RandoContextRequest request, Callback<RandoContext> cb) {
    doRequestAsync("getRandoContext", request, RandoContext.class, cb);
  }

}
