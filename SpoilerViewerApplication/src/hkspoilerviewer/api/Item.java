package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Item {
  public abstract String name();

  public abstract boolean isTransition();
}
