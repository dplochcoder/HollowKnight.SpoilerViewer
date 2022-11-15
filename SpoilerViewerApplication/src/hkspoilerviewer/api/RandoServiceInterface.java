package hkspoilerviewer.api;

public interface RandoServiceInterface {
  interface Callback<T> {
    void success(T response);

    void error(Exception e);
  }

  void getRandoContext(RandoContextRequest request, Callback<RandoContext> cb);
}
