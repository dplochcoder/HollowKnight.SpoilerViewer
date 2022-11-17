package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ItemPlacementData {
  public abstract ItemIndex itemIndex();

  public abstract boolean vanilla();

  // TODO: Costs
}
