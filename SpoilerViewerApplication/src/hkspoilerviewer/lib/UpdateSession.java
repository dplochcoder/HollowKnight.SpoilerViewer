package hkspoilerviewer.lib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Represents a single batch of updates to various DataProviders. */
public final class UpdateSession implements AutoCloseable {
  private final Object source;
  private final Set<UpdateSubscriber> subscribers;

  public UpdateSession(Object source) {
    this.source = source;
    this.subscribers = new HashSet<>();
  }

  public Object source() {
    return source;
  }

  public void addSubscriber(UpdateSubscriber subscriber) {
    subscribers.add(subscriber);
  }

  @Override
  public void close() throws Exception {
    while (!subscribers.isEmpty()) {
      List<UpdateSubscriber> clone = new ArrayList<>(subscribers);
      subscribers.clear();
      clone.forEach(UpdateSubscriber::onUpdateSessionComplete);
    }
  }
}
