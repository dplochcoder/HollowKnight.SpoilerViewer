package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Obtain {
  public abstract ObtainIndices indices();

  public abstract Location location();

  public abstract ImmutableList<ItemPlacement> itemPlacements();

  public static Obtain create(RandoContext ctx, ObtainIndices obtainIndices) {
    Location location = ctx.location(obtainIndices.locationIndex());
    return new AutoValue_Obtain(obtainIndices, location,
        obtainIndices.itemPlacementIndices().stream()
            .map(ipi -> ItemPlacement.create(ctx, location, ipi, location.itemPlacementData(ipi)))
            .collect(ImmutableList.toImmutableList()));
  }
}
