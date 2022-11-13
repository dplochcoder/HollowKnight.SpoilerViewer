package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Location {
  public abstract String name();

  public abstract boolean isShop();

  public abstract boolean isTransition();

  // TODO: Scene data, costs
}
