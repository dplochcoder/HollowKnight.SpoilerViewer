package hkspoilerviewer.lib;

@FunctionalInterface
public interface UpdateReceiver<T> {
  void receive(UpdateSession session, T data);
}
