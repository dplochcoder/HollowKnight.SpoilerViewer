package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Item {
  public abstract ItemName name();

  @Memoized
  public ImmutableList<String> aliases() {
    // TODO: Add more aliases
    return ImmutableList.of(name().name());
  }

  public abstract boolean isTransition();

  public static TypeAdapter<Item> typeAdapter(Gson gson) {
    return new AutoValue_Item.GsonTypeAdapter(gson);
  }

  static {
    TypeAdapterRegistry.register(Item.class, Item::typeAdapter);
  }
}
