package hkspoilerviewer.query;

import com.google.common.collect.ImmutableSet;
import hkspoilerviewer.api.ObtainIndices;

public abstract class Bookmarks {
  public abstract ImmutableSet<ObtainIndices> bookmarkedDocs();

  public abstract ImmutableSet<ObtainIndices> hiddenDocs();
}
