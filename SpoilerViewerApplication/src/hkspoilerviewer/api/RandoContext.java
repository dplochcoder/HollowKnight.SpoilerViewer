package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

@AutoValue
public abstract class RandoContext {

  public abstract ImmutableList<Item> items();

  @Memoized
  public ImmutableMap<ItemName, Item> itemsByName() {
    return Maps.uniqueIndex(items(), Item::name);
  }

  public final Item item(ItemIndex itemIndex) {
    return items().get(itemIndex.index());
  }

  public final Item item(ItemName itemName) {
    return itemsByName().get(itemName);
  }

  public final ItemIndex itemIndex(Item item) {
    return ItemIndex.of(itemsByName().keySet().asList().indexOf(item.name()));
  }

  public abstract ImmutableList<Location> locations();

  @Memoized
  public ImmutableMap<LocationName, Location> locationsByName() {
    return Maps.uniqueIndex(locations(), Location::name);
  }

  public final Location location(LocationIndex locationIndex) {
    return locations().get(locationIndex.index());
  }

  public final Location location(LocationName locationName) {
    return locationsByName().get(locationName);
  }

  public final LocationIndex locationIndex(Location location) {
    return LocationIndex.of(locationsByName().keySet().asList().indexOf(location.name()));
  }

  @Memoized
  public ImmutableMap<ObtainIndices, Obtain> obtainsByIndices() {
    return locations().stream().flatMap(Location::obtainIndices)
        .collect(ImmutableMap.toImmutableMap(oi -> oi, oi -> Obtain.create(this, oi)));
  }

  public final ImmutableSet<ObtainIndices> obtainIndices() {
    return obtainsByIndices().keySet();
  }

  public abstract LogicMapSequence logic();
}
