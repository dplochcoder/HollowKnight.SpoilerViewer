package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class InLogicSetDelta {
  public abstract ImmutableSet<Integer> newlyObtainedPlacements();

  public abstract ImmutableSet<Integer> newlyInLogicPlacements();
}
