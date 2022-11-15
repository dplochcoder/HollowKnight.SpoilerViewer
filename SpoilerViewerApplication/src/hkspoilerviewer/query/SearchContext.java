package hkspoilerviewer.query;

import java.util.HashMap;
import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MoreCollectors;
import hkspoilerviewer.api.ItemPlacement;
import hkspoilerviewer.api.LogicMap;
import hkspoilerviewer.api.Placement;
import hkspoilerviewer.api.RandoContext;

@AutoValue
public abstract class SearchContext {

  public abstract RandoContext rando();

  public abstract ImmutableSet<SearchItemKey> allSearchItemKeys();

  public static ImmutableSet<SearchItemKey> calculateKeys(RandoContext rando) {
    var builder = ImmutableSet.<SearchItemKey>builder();
    for (var p : rando.placements()) {
      if (p.location().isShop()) {
        for (var i : p.items()) {
          builder.add(SearchItemKey.create(i.placementId()));
        }
      } else {
        builder.add(SearchItemKey.create(p.items().stream().map(ItemPlacement::placementId)
            .collect(ImmutableSet.toImmutableSet())));
      }
    }
    return builder.build();
  }

  public abstract ImmutableSet<SearchItemKey> bookmarks();

  public abstract ImmutableSet<SearchItemKey> hidden();

  public abstract ImmutableSet<SearchItemKey> route();

  public abstract int routePlacementIndex();

  @Memoized
  public ImmutableMap<Integer, ItemPlacement> itemPlacements() {
    return rando().placements().stream().flatMap(p -> p.items().stream())
        .collect(ImmutableMap.toImmutableMap(i -> i.placementId(), i -> i));
  }

  public final ItemPlacement itemPlacement(int id) {
    return itemPlacements().get(id);
  }

  @Memoized
  public ImmutableMap<Integer, Placement> placements() {
    ImmutableMap.Builder<Integer, Placement> builder = ImmutableMap.builder();
    for (var p : rando().placements()) {
      p.items().forEach(i -> builder.put(i.placementId(), p));
    }
    return builder.build();
  }

  public final Placement placement(SearchItemKey key) {
    var map = new HashMap<String, Placement>();
    key.placementIds().forEach(i -> {
      var p = placements().get(i);
      map.put(p.name(), p);
    });
    return map.values().stream().collect(MoreCollectors.onlyElement());
  }

  @Memoized
  public LogicMap routeLogicMap() {
    return rando().logic().getByIndex(routePlacementIndex());
  }

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_SearchContext.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setRando(RandoContext rando);

    public abstract Builder setAllSearchItemKeys(ImmutableSet<SearchItemKey> allSearchItemKeys);

    public abstract Builder setBookmarks(ImmutableSet<SearchItemKey> bookmarks);

    public abstract Builder setHidden(ImmutableSet<SearchItemKey> hidden);

    public abstract Builder setRoute(ImmutableSet<SearchItemKey> route);

    public abstract Builder setRoutePlacementIndex(int routePlacementIndex);

    public abstract SearchContext build();
  }

}
