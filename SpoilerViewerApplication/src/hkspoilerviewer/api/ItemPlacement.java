package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ItemPlacement {
  public abstract int placementId();

  public abstract Item item();

  public abstract boolean isVanilla();
}
