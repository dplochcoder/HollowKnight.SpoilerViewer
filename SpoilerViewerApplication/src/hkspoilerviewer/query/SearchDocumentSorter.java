package hkspoilerviewer.query;

import java.util.Comparator;
import hkspoilerviewer.lib.DataProvider;
import hkspoilerviewer.lib.UpdateSubscriber;

public final class SearchDocumentSorter implements Comparator<SearchDocument> {
  private final DataProvider<SearchDocumentSorter> dataProvider;

  public SearchDocumentSorter() {
    this.dataProvider = new DataProvider<>(this);
  }

  public void addSubscriber(UpdateSubscriber subscriber) {
    dataProvider.addListener((s, d) -> s.addSubscriber(subscriber));
  }

  @Override
  public int compare(SearchDocument o1, SearchDocument o2) {
    return 0;
  }
}
