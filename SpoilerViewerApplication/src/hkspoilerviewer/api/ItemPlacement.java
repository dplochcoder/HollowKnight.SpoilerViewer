package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class ItemPlacement {
  public abstract ItemLocation location();

  public abstract ImmutableList<Item> items();
}
