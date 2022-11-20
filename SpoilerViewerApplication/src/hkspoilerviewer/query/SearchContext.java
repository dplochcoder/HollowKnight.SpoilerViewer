package hkspoilerviewer.query;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import hkspoilerviewer.api.LogicMap;
import hkspoilerviewer.api.RandoContext;

@AutoValue
public abstract class SearchContext {

  public abstract RandoContext rando();

  public abstract Bookmarks bookmarks();

  public abstract RouteInfo routeInfo();

  @Memoized
  public LogicMap routeLogicMap() {
    return rando().logic().getByIndex(routeInfo().routeIndex());
  }

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_SearchContext.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setRando(RandoContext rando);

    public abstract Builder setBookmarks(Bookmarks bookmarks);

    public abstract Builder setRouteInfo(RouteInfo routeInfo);

    public abstract SearchContext build();
  }

}
