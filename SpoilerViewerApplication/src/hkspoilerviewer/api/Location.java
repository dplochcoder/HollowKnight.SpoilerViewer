package hkspoilerviewer.api;

import java.util.Optional;
import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Location {
  public abstract LocationName name();

  @Memoized
  public ImmutableList<String> aliases() {
    // TODO
    return ImmutableList.of(name().name());
  }

  public abstract boolean isShop();

  public abstract boolean isTransition();

  public abstract Optional<String> mapAreaName();

  public abstract Optional<String> titleAreaName();

  public abstract ImmutableList<ItemPlacementData> itemPlacementDatum();

  public final ItemPlacementData itemPlacementData(ItemPlacementIndex itemPlacementIndex) {
    return itemPlacementDatum().get(itemPlacementIndex.index());
  }

  public abstract ImmutableList<ObtainIndices> obtainIndices();

  // TODO: Scene data, costs

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_Location.Builder().setMapAreaName(Optional.empty())
        .setTitleAreaName(Optional.empty());
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(LocationName name);

    public abstract Builder setIsShop(boolean isShop);

    public abstract Builder setIsTransition(boolean isTransition);

    public abstract Builder setMapAreaName(Optional<String> mapAreaName);

    public abstract Builder setTitleAreaName(Optional<String> mapAreaName);

    public abstract Builder setItemPlacementDatum(
        ImmutableList<ItemPlacementData> itemPlacementDatum);

    public abstract Builder setObtainIndices(ImmutableList<ObtainIndices> obtainIndices);

    public abstract Location build();
  }

  public static TypeAdapter<Location> typeAdapter(Gson gson) {
    return new AutoValue_Location.GsonTypeAdapter(gson);
  }
}
