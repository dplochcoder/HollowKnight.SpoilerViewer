package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

@AutoValue
public abstract class ItemIndex {
  private static final Interner<ItemIndex> INTERNER = Interners.newStrongInterner();

  public abstract int index();

  public static ItemIndex of(int index) {
    return INTERNER.intern(new AutoValue_ItemIndex(index));
  }
}
