package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class LocationName {
  private static final Interner<LocationName> INTERNER = Interners.newStrongInterner();

  public abstract String name();

  public static LocationName of(String name) {
    return INTERNER.intern(new AutoValue_LocationName(name));
  }

  public static TypeAdapter<LocationName> typeAdapter(Gson gson) {
    return new AutoValue_LocationName.GsonTypeAdapter(gson);
  }
}
