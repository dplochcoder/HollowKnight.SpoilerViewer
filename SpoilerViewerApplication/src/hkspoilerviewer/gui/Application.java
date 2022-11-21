package hkspoilerviewer.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import hkspoilerviewer.api.RandoContext;
import hkspoilerviewer.lib.DataProvider;
import hkspoilerviewer.query.Bookmarks;
import hkspoilerviewer.query.RouteInfo;
import hkspoilerviewer.query.SearchDocumentFilter;
import hkspoilerviewer.query.SearchDocumentSorter;

public final class Application extends JFrame {
  private static final long serialVersionUID = 1L;

  private final DataProvider<RandoContext> randoContext;
  private final DataProvider<Bookmarks> bookmarks;
  private final DataProvider<RouteInfo> routeInfo;
  private final DataProvider<SearchDocumentFilter> searchFilter;
  private final DataProvider<SearchDocumentSorter> searchSorter;

  private final SearchResultsPanel searchResultsPanel;

  public Application() {
    super("Hollow Knight Spoiler Viewer 3");

    this.randoContext = new DataProvider<>(RandoContext.empty());
    this.bookmarks = new DataProvider<>(Bookmarks.empty());
    this.routeInfo = new DataProvider<>(RouteInfo.empty());

    var cPane = getContentPane();
    cPane.setLayout(new BorderLayout());
  }
}
