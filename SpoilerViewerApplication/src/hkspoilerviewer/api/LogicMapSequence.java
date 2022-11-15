package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;

@AutoValue
public abstract class LogicMapSequence {
  public abstract LogicMap base();

  // Maps each obtain to a set of logic changes, in order.
  public abstract ImmutableMap<Integer, LogicMapDelta> deltas();

  public final boolean isObtained(int itemPlacementId) {
    return deltas().containsKey(itemPlacementId);
  }

  public final LogicMap getByIndex(int index) {
    return base().apply(deltas().values().asList().subList(0, index));
  }

  public final LogicMap getByObtain(int itemPlacementId) {
    return getByIndex(deltas().keySet().asList().indexOf(itemPlacementId) + 1);
  }

  public final int size() {
    return deltas().size() + 1;
  }
}
