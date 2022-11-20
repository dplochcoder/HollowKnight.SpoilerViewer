package hkspoilerviewer.query;

import com.google.auto.value.AutoValue;
import autovaluegson.shaded.com.google.common.collect.ImmutableList;

@AutoValue
public abstract class SearchResults {
  public abstract ImmutableList<SearchDocument> documents();

  public final SearchDocument document(int index) {
    return documents().get(index);
  }

  public static SearchResults create(Iterable<SearchDocument> documents) {
    return new AutoValue_SearchResults(ImmutableList.copyOf(documents));
  }
}
