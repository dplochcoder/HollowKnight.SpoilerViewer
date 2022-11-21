package hkspoilerviewer.query;

import java.util.function.Predicate;
import hkspoilerviewer.lib.DataProvider;
import hkspoilerviewer.lib.UpdateSubscriber;

public final class SearchDocumentFilter implements Predicate<SearchDocument> {
  private final DataProvider<SearchDocumentFilter> dataProvider;

  public SearchDocumentFilter() {
    this.dataProvider = new DataProvider<>(this);
  }

  public void addSubscriber(UpdateSubscriber subscriber) {
    dataProvider.addListener((s, d) -> s.addSubscriber(subscriber));
  }

  @Override
  public boolean test(SearchDocument t) {
    // TODO: Add filters.
    return true;
  }
}
