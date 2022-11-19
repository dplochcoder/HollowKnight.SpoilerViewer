package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ItemPlacement {
  public abstract Location location();

  public abstract ItemPlacementIndex itemPlacementIndex();

  public abstract ItemPlacementData data();

  public abstract Item item();

  public static ItemPlacement create(RandoContext ctx, Location location, ItemPlacementIndex index,
      ItemPlacementData data) {
    return new AutoValue_ItemPlacement(location, index, data, ctx.item(data.itemName()));
  }
}
