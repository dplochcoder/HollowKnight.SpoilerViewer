package hkspoilerviewer.lib;

public final class DataProvider<T> extends ListenerManager<UpdateReceiver<T>> {
  private T data;

  public DataProvider(T data) {
    this.data = data;
  }

  public synchronized T get() {
    return data;
  }

  public void update(UpdateSession session, T data) {
    synchronized (this) {
      this.data = data;
      forEach(l -> l.receive(session, data));
    }
  }
}
