package hkspoilerviewer.query;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class SearchItemKey {
  public abstract ImmutableSet<Integer> placementIds();
}
