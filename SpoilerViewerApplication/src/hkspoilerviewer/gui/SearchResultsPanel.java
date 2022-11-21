package hkspoilerviewer.gui;

import javax.swing.JList;
import hkspoilerviewer.api.RandoContext;
import hkspoilerviewer.lib.DataProvider;
import hkspoilerviewer.query.Bookmarks;
import hkspoilerviewer.query.RouteInfo;
import hkspoilerviewer.query.SearchDocumentFilter;
import hkspoilerviewer.query.SearchDocumentSorter;

public final class SearchResultsPanel extends JList<String> {
  private static final long serialVersionUID = 1L;

  private final SearchResultsListModel model;

  public SearchResultsPanel(DataProvider<RandoContext> randoContext,
      DataProvider<Bookmarks> bookmarks, DataProvider<RouteInfo> routeInfo,
      DataProvider<SearchDocumentFilter> searchFilter,
      DataProvider<SearchDocumentSorter> searchSorter) {
    this.model =
        new SearchResultsListModel(randoContext, bookmarks, routeInfo, searchFilter, searchSorter);

    this.setModel(this.model);
  }

  // TODO: Key events, repaint.
}
