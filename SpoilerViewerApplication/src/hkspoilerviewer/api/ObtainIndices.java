package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ObtainIndices {
  public abstract LocationName locationName();

  public abstract ImmutableSet<ItemPlacementIndex> itemPlacementIndices();

  public static ObtainIndices create(LocationName locationName,
      Iterable<ItemPlacementIndex> itemPlacementIndices) {
    return new AutoValue_ObtainIndices(locationName, ImmutableSet.copyOf(itemPlacementIndices));
  }

  public static TypeAdapter<ObtainIndices> typeAdapter(Gson gson) {
    return new AutoValue_ObtainIndices.GsonTypeAdapter(gson);
  }

  static {
    TypeAdapterRegistry.register(ObtainIndices.class, ObtainIndices::typeAdapter);
  }
}
