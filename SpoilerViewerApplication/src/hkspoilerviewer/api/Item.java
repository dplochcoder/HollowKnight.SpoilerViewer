package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Item {
  public abstract ItemName name();

  @Memoized
  public ImmutableList<String> aliases() {
    // TODO: Add more aliases
    return ImmutableList.of(name().name());
  }

  public abstract boolean isTransition();
}
