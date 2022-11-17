package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import autovaluegson.factory.shaded.com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Item {
  public abstract ItemName name();

  public abstract ItemIndex index();

  @Memoized
  public ImmutableList<String> aliases() {
    // TODO: Add more aliases
    return ImmutableList.of(name().name());
  }

  public abstract boolean isTransition();
}
