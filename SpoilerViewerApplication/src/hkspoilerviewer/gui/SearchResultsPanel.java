package hkspoilerviewer.gui;

import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
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
      SearchDocumentFilter searchDocumentFilter, SearchDocumentSorter searchDocumentSorter) {
    this.model = new SearchResultsListModel(randoContext, bookmarks, routeInfo,
        searchDocumentFilter, searchDocumentSorter);
    this.model.addListDataListener(new ListDataListener() {
      @Override
      public void intervalRemoved(ListDataEvent e) {
        SearchResultsPanel.this.repaint();
      }

      @Override
      public void intervalAdded(ListDataEvent e) {
        SearchResultsPanel.this.repaint();
      }

      @Override
      public void contentsChanged(ListDataEvent e) {
        SearchResultsPanel.this.repaint();
      }
    });

    this.setModel(this.model);
  }

  // TODO: Key events, repaint.
}
