package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

@AutoValue
public abstract class ItemPlacementIndex {
  private static final Interner<ItemPlacementIndex> INTERNER = Interners.newStrongInterner();

  public abstract int index();

  public static ItemPlacementIndex of(int index) {
    return INTERNER.intern(new AutoValue_ItemPlacementIndex(index));
  }
}
