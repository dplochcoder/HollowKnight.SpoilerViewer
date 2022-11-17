package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

@AutoValue
public abstract class LocationIndex {
  private static final Interner<LocationIndex> INTERNER = Interners.newStrongInterner();

  public abstract int index();

  public static LocationIndex of(int index) {
    return INTERNER.intern(new AutoValue_LocationIndex(index));
  }
}
