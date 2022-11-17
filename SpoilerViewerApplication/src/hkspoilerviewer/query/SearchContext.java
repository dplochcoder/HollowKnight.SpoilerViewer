package hkspoilerviewer.query;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableSet;
import hkspoilerviewer.api.LogicMap;
import hkspoilerviewer.api.ObtainIndices;
import hkspoilerviewer.api.RandoContext;

@AutoValue
public abstract class SearchContext {

  public abstract RandoContext rando();

  public abstract ImmutableSet<ObtainIndices> bookmarks();

  public abstract ImmutableSet<ObtainIndices> hidden();

  public abstract ImmutableSet<ObtainIndices> route();

  public abstract int routePlacementIndex();

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

    public abstract Builder setBookmarks(ImmutableSet<ObtainIndices> bookmarks);

    public abstract Builder setHidden(ImmutableSet<ObtainIndices> hidden);

    public abstract Builder setRoute(ImmutableSet<ObtainIndices> route);

    public abstract Builder setRoutePlacementIndex(int routePlacementIndex);

    public abstract SearchContext build();
  }

}
