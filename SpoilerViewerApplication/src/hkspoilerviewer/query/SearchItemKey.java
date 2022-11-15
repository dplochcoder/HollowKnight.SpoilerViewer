package hkspoilerviewer.query;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class SearchItemKey {
  public abstract ImmutableSet<Integer> placementIds();

  public static SearchItemKey create(int id) {
    return new AutoValue_SearchItemKey(ImmutableSet.of(id));
  }

  public static SearchItemKey create(Iterable<Integer> ids) {
    return new AutoValue_SearchItemKey(ImmutableSet.copyOf(ids));
  }
}
