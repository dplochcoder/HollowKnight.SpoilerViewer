package hkspoilerviewer.query;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import hkspoilerviewer.api.ObtainIndices;

@AutoValue
public abstract class RouteInfo {
  // Obtains implicitly routed before anything else.
  public abstract ImmutableSet<ObtainIndices> startObtains();

  // Obtains explicitly routed by the user.
  public abstract ImmutableSet<ObtainIndices> explicitObtains();

  // Obtains implicitly routed by the logic engine.
  public abstract ImmutableSetMultimap<ObtainIndices, ObtainIndices> implicitObtains();

  // Insertion point for new obtains, and the current logic map.
  // 0 for after `startObtains()`, `explicitObtains().size()` for after all other obtains.
  public abstract int routeIndex();
}
