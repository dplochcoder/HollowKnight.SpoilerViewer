package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ItemPlacementData {
  public abstract ItemName itemName();

  public abstract boolean vanilla();

  // TODO: Costs

  public static TypeAdapter<ItemPlacementData> typeAdapter(Gson gson) {
    return new AutoValue_ItemPlacementData.GsonTypeAdapter(gson);
  }

  static {
    TypeAdapterRegistry.register(ItemPlacementData.class, ItemPlacementData::typeAdapter);
  }
}
