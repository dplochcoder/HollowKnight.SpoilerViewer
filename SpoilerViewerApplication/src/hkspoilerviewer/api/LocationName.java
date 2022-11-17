package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

@AutoValue
public abstract class LocationName {
  private static final Interner<LocationName> INTERNER = Interners.newStrongInterner();

  public abstract String name();

  public static LocationName of(String name) {
    return INTERNER.intern(new AutoValue_LocationName(name));
  }
}
