package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class LogicMapSequence {
  public abstract LogicMap base();

  public abstract ImmutableList<LogicMapDelta> deltas();

  public final int size() {
    return deltas().size() + 1;
  }

  public final LogicMap get(int index) {
    return base().apply(deltas().subList(0, index));
  }
}
