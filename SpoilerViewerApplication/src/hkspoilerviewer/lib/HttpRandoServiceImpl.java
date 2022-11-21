package hkspoilerviewer.lib;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.concurrent.Flow.Subscriber;
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
    HttpRequest httpRequest = HttpRequest.newBuilder()
        .POST(BodyPublishers.ofString("TODO"))
        .build();
    this.client.sendAsync(httpRequest, new BodyHandler<String>() {

		@Override
		public BodySubscriber<String> apply(ResponseInfo responseInfo) {
			// TODO Auto-generated method stub
			return null;
		}
	});
  }

  private <A, B> void doRequestSync(A request, Callback<B> cb) {

  }

  @Override
  public void getRandoContext(RandoContextRequest request, Callback<RandoContext> cb) {}

}
