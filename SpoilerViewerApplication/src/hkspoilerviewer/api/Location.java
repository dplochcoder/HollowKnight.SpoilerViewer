package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Location {
  public abstract String name();

  @Memoized
  public ImmutableList<String> aliases() {
    // TODO
    return ImmutableList.of(name());
  }

  public abstract boolean isShop();

  public abstract boolean isTransition();

  public abstract String mapArea();

  public abstract String titleArea();

  // TODO: Scene data, costs
}
