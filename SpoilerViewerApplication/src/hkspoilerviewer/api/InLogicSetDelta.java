package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class InLogicSetDelta {
  public abstract ImmutableSet<Integer> newlyObtainedItemPlacements();

  public abstract ImmutableSet<Integer> newlyInLogicItemPlacements();

  public abstract ImmutableSet<Integer> newlyObtainedTransitionPlacements();

  public abstract ImmutableSet<Integer> newlyInLogicTransitionPlacements();
}
