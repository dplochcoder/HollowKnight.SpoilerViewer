package hkspoilerviewer.lib;

// Attaches to UpdateSessions to do work at the end of them.
@FunctionalInterface
public interface UpdateSubscriber {
  void onUpdateSessionComplete();
}
