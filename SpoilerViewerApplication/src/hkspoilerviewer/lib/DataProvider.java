package hkspoilerviewer.lib;

import java.util.function.Consumer;

public final class DataProvider<T> extends ListenerManager<Consumer<T>> {
  private T data;

  public DataProvider(T data) {
    this.data = data;
  }

  public synchronized T get() {
    return data;
  }

  public void update(T data) {
    synchronized (this) {
      this.data = data;
      forEach(l -> l.accept(data));
    }
  }
}
