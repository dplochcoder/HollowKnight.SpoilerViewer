package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class InLogicSetDeltaChain {
  public abstract InLogicSet base();

  public abstract ImmutableList<InLogicSetDelta> deltas();

  public final InLogicSet get(int index) {
    return base().apply(deltas().subList(0, index));
  }
}
