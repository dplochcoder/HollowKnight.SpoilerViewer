package hkspoilerviewer.query;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Streams;
import hkspoilerviewer.api.ObtainIndices;

@AutoValue
public abstract class RouteInfo {
  // Obtains implicitly routed before anything else.
  public abstract ImmutableSet<ObtainIndices> startObtains();

  // Obtains explicitly routed by the user.
  public abstract ImmutableSet<ObtainIndices> explicitObtains();

  // Obtains implicitly routed by the logic engine.
  public abstract ImmutableSetMultimap<ObtainIndices, ObtainIndices> implicitObtains();

  @Memoized
  public ImmutableSet<ObtainIndices> allObtains() {
    return Streams.concat(startObtains().stream(), explicitObtains().stream(),
        implicitObtains().values().stream()).collect(ImmutableSet.toImmutableSet());
  }

  public final boolean isObtained(ObtainIndices obtainIndices) {
    return allObtains().contains(obtainIndices);
  }

  // Insertion point for new obtains, and the current logic map.
  // 0 for after `startObtains()`, `explicitObtains().size()` for after all other obtains.
  public abstract int routeIndex();

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_RouteInfo.Builder().setStartObtains(ImmutableSet.of())
        .setExplicitObtains(ImmutableSet.of()).setimplicitObtains(ImmutableSetMultimap.of())
        .setRouteIndex(0);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setStartObtains(ImmutableSet<ObtainIndices> startObtains);

    public abstract Builder setExplicitObtains(ImmutableSet<ObtainIndices> explicitObtains);

    public abstract Builder setimplicitObtains(
        ImmutableSetMultimap<ObtainIndices, ObtainIndices> implicitObtains);

    public abstract Builder setRouteIndex(int routeIndex);

    public abstract RouteInfo build();
  }

  private static final RouteInfo EMPTY = builder().build();

  public static RouteInfo empty() {
    return EMPTY;
  }
}
