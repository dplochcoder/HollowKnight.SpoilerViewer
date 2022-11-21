package hkspoilerviewer.lib;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import hkspoilerviewer.api.RandoContext;
import hkspoilerviewer.api.RandoContextRequest;
import hkspoilerviewer.api.RandoServiceInterface;

public final class HttpRandoServiceImpl implements RandoServiceInterface {

  private final int port;
  private final HttpClient client;

  public HttpRandoServiceImpl(int port) {
    this.port = port;
    this.client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
        .connectTimeout(Duration.ofSeconds(60)).build();
  }

  private <A, B> void doRequestAsync(A request, Callback<B> cb) {
    HttpRequest request = HttpRequest.newBuilder()
        .POST(bodyPublisher)
        .build();
    this.client.sendAsync(request, responseBodyHandler)
  }

  private <A, B> void doRequestSync(A request, Callback<B> cb) {

  }

  @Override
  public void getRandoContext(RandoContextRequest request, Callback<RandoContext> cb) {}

}
