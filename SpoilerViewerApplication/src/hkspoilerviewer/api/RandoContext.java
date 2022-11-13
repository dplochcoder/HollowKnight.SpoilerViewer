package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class RandoContext {
  public abstract String randoContextId();

  public abstract ImmutableList<ItemsPlacement> itemsPlacements();

  public abstract ImmutableList<TransitionPlacement> transitionPlacements();

  public abstract InLogicSetDeltaChain inLogic();
}
