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

}
