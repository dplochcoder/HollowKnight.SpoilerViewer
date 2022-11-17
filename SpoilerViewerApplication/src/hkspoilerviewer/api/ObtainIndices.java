package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class ObtainIndices {
  public abstract LocationIndex locationIndex();

  public abstract ImmutableSet<ItemPlacementIndex> itemPlacementIndices();

  public static ObtainIndices create(LocationIndex locationIndex,
      Iterable<ItemPlacementIndex> itemPlacementIndices) {
    return new AutoValue_ObtainIndices(locationIndex, ImmutableSet.copyOf(itemPlacementIndices));
  }
}
