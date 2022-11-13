package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class RandoContext {
  public abstract int id();

  public abstract ImmutableList<Placement> placements();

  public abstract InLogicSetDeltaChain inLogic();
}
