package hkspoilerviewer.query;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import hkspoilerviewer.api.ObtainIndices;

@AutoValue
public abstract class Bookmarks {
  public abstract ImmutableSet<ObtainIndices> bookmarkedDocs();

  public abstract ImmutableSet<ObtainIndices> hiddenDocs();

  public static Bookmarks create(Iterable<ObtainIndices> bookmarkedDocs,
      Iterable<ObtainIndices> hiddenDocs) {
    return new AutoValue_Bookmarks(ImmutableSet.copyOf(bookmarkedDocs),
        ImmutableSet.copyOf(hiddenDocs));
  }

  private static Bookmarks EMPTY = create(ImmutableSet.of(), ImmutableSet.of());

  public static Bookmarks empty() {
    return EMPTY;
  }
}
