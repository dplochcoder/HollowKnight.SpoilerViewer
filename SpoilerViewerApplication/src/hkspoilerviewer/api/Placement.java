package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Placement {
  public abstract Location location();

  public final String name() {
    return location().name();
  }

  public final boolean isTransition() {
    return location().isTransition();
  }

  public abstract ImmutableList<ItemPlacement> items();
}
