package hkspoilerviewer.gui;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import hkspoilerviewer.lib.DataProvider;
import hkspoilerviewer.query.Bookmarks;
import hkspoilerviewer.query.RouteInfo;
import hkspoilerviewer.query.SearchResults;

public final class SearchResultsListModel implements ListModel<String> {

  private final DataProvider<Bookmarks> bookmarks;
  private final DataProvider<RouteInfo> routeInfo;
  private final DataProvider<SearchResults> searchResults;

  public SearchResultsListModel(DataProvider<Bookmarks> bookmarks,
      DataProvider<RouteInfo> routeInfo, DataProvider<SearchResults> searchResults) {
    this.bookmarks = bookmarks;
    this.routeInfo = routeInfo;
    this.searchResults = searchResults;
  }

  private static final String SEPARATOR = "----------------------------------------";

  @Override
  public int getSize() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getElementAt(int index) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    // TODO Auto-generated method stub

  }

}
