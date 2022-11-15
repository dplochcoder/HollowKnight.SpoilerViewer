package hkspoilerviewer.query;

import java.util.stream.Collectors;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import hkspoilerviewer.api.ItemPlacement;
import hkspoilerviewer.api.Logic;
import hkspoilerviewer.api.Placement;
import hkspoilerviewer.gui.ListItemStyle;

@AutoValue
public abstract class SearchDocument {

  public abstract SearchItemKey key();

  public abstract Placement placement();

  public abstract ImmutableList<ItemPlacement> itemPlacements();

  public abstract ImmutableList<String> itemNames();

  public abstract ImmutableList<String> locationNames();

  public abstract String mapAreaName();

  public abstract String titleAreaName();

  public abstract Logic logic();

  public abstract boolean transition();

  public abstract boolean vanilla();

  public abstract boolean routed();

  public final ListItemStyle render() {
    var sb = new StringBuilder();
    if (routed()) {
      sb.append("(R) ");
    }
    switch (logic()) {
      case IN_LOGIC:
        break;
      case IN_PURCHASE_LOGIC:
        sb.append("$");
        break;
      case OUT_OF_LOGIC:
        sb.append("*");
        break;
    }

    boolean paren = itemPlacements().size() > 1;
    sb.append(paren ? "(" : "");
    sb.append(
        itemPlacements().stream().map(i -> i.item().name()).collect(Collectors.joining(", ")));
    sb.append(paren ? ")" : "");
    sb.append(" @ ");
    sb.append(placement().location().name());

    // TODO: Costs
    return ListItemStyle.create(sb.toString(), false, false, transition());
  }

  // FIXME: Costs

  private static <C extends Comparable<C>> C max(C c1, C c2) {
    return c1.compareTo(c2) < 0 ? c2 : c1;
  }

  public static SearchDocument create(SearchContext ctx, SearchItemKey key) {
    var itemPlacements = key.placementIds().stream().map(ctx::itemPlacement)
        .collect(ImmutableList.toImmutableList());
    var placement = ctx.placement(key);

    Builder b = new AutoValue_SearchDocument.Builder();
    b.setKey(key);
    b.setPlacement(placement);
    b.setItemPlacements(itemPlacements);
    var loc = placement.location();
    loc.aliases().forEach(b::addLocationName);
    b.setMapAreaName(loc.mapArea());
    b.setTitleAreaName(loc.titleArea());
    b.setTransition(placement.isTransition());
    b.setRouted(key.placementIds().stream().allMatch(id -> ctx.rando().logic().isObtained(id)));

    boolean vanilla = true;
    var logic = Logic.IN_LOGIC;
    var logicMap = ctx.routeLogicMap();
    for (var i : itemPlacements) {
      i.item().aliases().forEach(b::addItemName);
      vanilla &= i.isVanilla();
      logic = max(logic, logicMap.logic(i.placementId()));
    }

    b.setVanilla(vanilla);
    return b.build();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setKey(SearchItemKey key);

    public abstract Builder setPlacement(Placement placement);

    public abstract Builder setItemPlacements(ImmutableList<ItemPlacement> itemPlacements);

    public abstract ImmutableList.Builder<String> itemNamesBuilder();

    public final Builder addItemName(String itemName) {
      itemNamesBuilder().add(itemName);
      return this;
    }

    public abstract ImmutableList.Builder<String> locationNamesBuilder();

    public final Builder addLocationName(String locationName) {
      itemNamesBuilder().add(locationName);
      return this;
    }

    public abstract Builder setMapAreaName(String mapAreaName);

    public abstract Builder setTitleAreaName(String titleAreaName);

    public abstract Builder setLogic(Logic logic);

    public abstract Builder setTransition(boolean transition);

    public abstract Builder setVanilla(boolean vanilla);

    public abstract Builder setRouted(boolean routed);

    public abstract SearchDocument build();
  }
}
