package hkspoilerviewer.gui;

import java.util.Optional;
import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import hkspoilerviewer.query.SearchDocument;

@AutoValue
public abstract class SearchResultsListModelItem {

  public abstract Optional<SearchDocument> document();

  public abstract boolean matchesFilter();

  public abstract Optional<Integer> bookmarkIndex();

  public abstract boolean isHidden();

  private static final ListItemRender SEPARATOR_RENDER =
      ListItemRender.builder("----------------------------------------").build();

  @Memoized
  public ListItemRender render() {
    if (document().isPresent()) {
      ListItemRender r = document().get().render();
      if (!matchesFilter() || isHidden()) {
        return r.toBuilder().setFaded(true).build();
      }
      return r;
    } else {
      return SEPARATOR_RENDER;
    }
  }

  private static final SearchResultsListModelItem SEPARATOR =
      new AutoValue_SearchResultsListModelItem(Optional.empty(), false, Optional.empty(), false);

  public static SearchResultsListModelItem separator() {
    return SEPARATOR;
  }

  public static SearchResultsListModelItem bookmark(SearchDocument doc, boolean matchesFilter,
      int index) {
    return new AutoValue_SearchResultsListModelItem(Optional.of(doc), matchesFilter,
        Optional.of(index), false);
  }

  public static SearchResultsListModelItem result(SearchDocument doc, boolean hidden) {
    return new AutoValue_SearchResultsListModelItem(Optional.of(doc), true, Optional.empty(),
        hidden);
  }
}
