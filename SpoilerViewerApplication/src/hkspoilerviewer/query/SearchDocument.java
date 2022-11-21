package hkspoilerviewer.query;

import java.util.stream.Collectors;
import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import hkspoilerviewer.api.ItemPlacement;
import hkspoilerviewer.api.Location;
import hkspoilerviewer.api.Logic;
import hkspoilerviewer.api.Obtain;
import hkspoilerviewer.gui.ListItemRender;

@AutoValue
public abstract class SearchDocument {

  public abstract SearchContext ctx();

  public abstract Obtain obtain();

  public final Location location() {
    return obtain().location();
  }

  public final ImmutableList<ItemPlacement> itemPlacements() {
    return obtain().itemPlacements();
  }

  @Memoized
  public ImmutableSet<String> itemNames() {
    return itemPlacements().stream().flatMap(ip -> ip.item().aliases().stream())
        .collect(ImmutableSet.toImmutableSet());
  }

  public final ImmutableList<String> locationNames() {
    return location().aliases();
  }

  public final String mapAreaName() {
    return location().mapAreaName().orElse("Unknown");
  }

  public final String titleAreaName() {
    return location().titleAreaName().orElse("Unknown");
  }

  public final Logic logic() {
    return ctx().routeLogicMap().logic(obtain().indices());
  }

  public final boolean transition() {
    return location().isTransition();
  }

  @Memoized
  public boolean vanilla() {
    return itemPlacements().stream().allMatch(ip -> ip.data().vanilla());
  }

  public final boolean routed() {
    return ctx().routeInfo().isObtained(obtain().indices());
  }

  public final ListItemRender render() {
    StringBuilder sb = new StringBuilder();
    if (routed()) {
      sb.append("(R) ");
    }
    if (vanilla()) {
      sb.append("#");
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
      case UNKNOWN:
        sb.append("(?) ");
        break;
    }

    boolean paren = itemPlacements().size() > 1;
    sb.append(paren ? "(" : "");
    sb.append(itemPlacements().stream().map(i -> i.item().name().name())
        .collect(Collectors.joining(", ")));
    sb.append(paren ? ")" : "");
    sb.append(" @ ");
    sb.append(location().name().name());

    // TODO: Costs
    return ListItemRender.builder(sb.toString()).setItalics(transition()).build();
  }

  // FIXME: Costs

  public static SearchDocument create(SearchContext ctx, Obtain obtain) {
    return new AutoValue_SearchDocument(ctx, obtain);
  }
}
