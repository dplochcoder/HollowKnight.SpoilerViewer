package hkspoilerviewer.lib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ListenerManager<T> {
  private final Set<T> listeners = new HashSet<>();

  public ListenerManager() {}

  public synchronized void addListener(T listener) {
    listeners.add(listener);
  }

  public synchronized void removeListener(T listener) {
    listeners.remove(listener);
  }

  public synchronized void forEach(Consumer<T> action) {
    List<T> capture = new ArrayList<>(listeners);
    capture.forEach(action);
  }
}
