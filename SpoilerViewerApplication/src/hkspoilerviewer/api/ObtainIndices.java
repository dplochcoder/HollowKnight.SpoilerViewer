package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class ObtainIndices {
  public abstract LocationName locationName();

  public abstract ImmutableSet<ItemPlacementIndex> itemPlacementIndices();

  public static ObtainIndices create(LocationName locationName,
      Iterable<ItemPlacementIndex> itemPlacementIndices) {
    return new AutoValue_ObtainIndices(locationName, ImmutableSet.copyOf(itemPlacementIndices));
  }
}
