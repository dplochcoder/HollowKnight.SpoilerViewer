package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ItemPlacementData {
  public abstract ItemName itemName();

  public abstract boolean vanilla();

  // TODO: Costs
}
