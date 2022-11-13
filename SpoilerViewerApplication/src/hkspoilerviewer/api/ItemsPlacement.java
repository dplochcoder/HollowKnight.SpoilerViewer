package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class ItemsPlacement {
  public abstract ItemLocation location();

  public abstract ImmutableList<ItemPlacement> items();
}
