package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ItemName {
  private static final Interner<ItemName> INTERNER = Interners.newStrongInterner();

  public abstract String name();

  public static ItemName of(String name) {
    return INTERNER.intern(new AutoValue_ItemName(name));
  }

  public static TypeAdapter<ItemName> typeAdapter(Gson gson) {
    return new AutoValue_ItemName.GsonTypeAdapter(gson);
  }
}
