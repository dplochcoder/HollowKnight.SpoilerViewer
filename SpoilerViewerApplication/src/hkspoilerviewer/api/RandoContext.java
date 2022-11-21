package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class RandoContext {

  public abstract ImmutableList<Item> items();

  @Memoized
  public ImmutableMap<ItemName, Item> itemsByName() {
    return Maps.uniqueIndex(items(), Item::name);
  }

  public final Item item(ItemName itemName) {
    return itemsByName().get(itemName);
  }

  public abstract ImmutableList<Location> locations();

  @Memoized
  public ImmutableMap<LocationName, Location> locationsByName() {
    return Maps.uniqueIndex(locations(), Location::name);
  }

  public final Location location(LocationName locationName) {
    return locationsByName().get(locationName);
  }

  @Memoized
  public ImmutableMap<ObtainIndices, Obtain> obtainsByIndices() {
    return locations().stream().flatMap(Location::obtainIndices)
        .collect(ImmutableMap.toImmutableMap(oi -> oi, oi -> Obtain.create(this, oi)));
  }

  public final ImmutableSet<ObtainIndices> obtainIndices() {
    return obtainsByIndices().keySet();
  }

  public final ImmutableList<Obtain> obtains() {
    return obtainsByIndices().values().asList();
  }

  public abstract LogicMapSequence logic();

  private static final RandoContext EMPTY = builder().build();

  public static RandoContext empty() {
    return EMPTY;
  }

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_RandoContext.Builder().setItems(ImmutableList.of())
        .setLocations(ImmutableList.of()).setLogic(LogicMapSequence.empty());
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setItems(ImmutableList<Item> items);

    public abstract Builder setLocations(ImmutableList<Location> locations);

    public abstract Builder setLogic(LogicMapSequence logic);

    public abstract RandoContext build();
  }

  public static TypeAdapter<RandoContext> typeAdapter(Gson gson) {
    return new AutoValue_RandoContext.GsonTypeAdapter(gson);
  }

  static {
    TypeAdapterRegistry.register(RandoContext.class, RandoContext::typeAdapter);
  }
}
