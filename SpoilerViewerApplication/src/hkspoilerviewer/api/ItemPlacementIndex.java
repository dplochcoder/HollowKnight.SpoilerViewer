package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ItemPlacementIndex {
  private static final Interner<ItemPlacementIndex> INTERNER = Interners.newStrongInterner();

  public abstract int index();

  public static ItemPlacementIndex of(int index) {
    return INTERNER.intern(new AutoValue_ItemPlacementIndex(index));
  }

  public static TypeAdapter<ItemPlacementIndex> typeAdapter(Gson gson) {
    return new AutoValue_ItemPlacementIndex.GsonTypeAdapter(gson);
  }

  static {
    TypeAdapterRegistry.register(ItemPlacementIndex.class, ItemPlacementIndex::typeAdapter);
  }
}
