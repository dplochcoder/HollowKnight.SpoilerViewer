package hkspoilerviewer.api;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class Location {
  public abstract LocationName name();

  @Memoized
  public ImmutableList<String> aliases() {
    // TODO
    return ImmutableList.of(name().name());
  }

  public abstract boolean isShop();

  public abstract boolean isTransition();

  public abstract Optional<String> mapAreaName();

  public abstract Optional<String> titleAreaName();

  public abstract ImmutableList<ItemPlacementData> itemPlacementDatum();

  public final ItemPlacementData itemPlacementData(ItemPlacementIndex itemPlacementIndex) {
    return itemPlacementDatum().get(itemPlacementIndex.index());
  }

  public final Stream<ObtainIndices> obtainIndices() {
    if (isShop()) {
      return IntStream.range(0, itemPlacementDatum().size())
          .mapToObj(i -> ObtainIndices.create(name(), ImmutableSet.of(ItemPlacementIndex.of(i))));
    } else {
      return Stream.of(ObtainIndices.create(name(), IntStream.range(0, itemPlacementDatum().size())
          .mapToObj(ItemPlacementIndex::of).collect(ImmutableSet.toImmutableSet())));
    }
  }

  // TODO: Scene data, costs
}
