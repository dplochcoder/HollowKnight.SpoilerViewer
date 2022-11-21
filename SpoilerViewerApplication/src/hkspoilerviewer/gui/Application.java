package hkspoilerviewer.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import hkspoilerviewer.api.RandoContext;
import hkspoilerviewer.api.RandoServiceInterface;
import hkspoilerviewer.api.RouteIntent;
import hkspoilerviewer.lib.DataProvider;
import hkspoilerviewer.query.Bookmarks;
import hkspoilerviewer.query.RouteInfo;
import hkspoilerviewer.query.SearchDocumentFilter;
import hkspoilerviewer.query.SearchDocumentSorter;

public final class Application extends JFrame {
  private static final long serialVersionUID = 1L;

  private final RandoServiceInterface randoService;

  private final DataProvider<RandoContext> randoContext;
  private final DataProvider<Bookmarks> bookmarks;
  private final DataProvider<RouteInfo> routeInfo;
  private final SearchDocumentFilter searchDocumentFilter;
  private final SearchDocumentSorter searchDocumentSorter;
  private final DataProvider<RouteIntent> routeIntent;

  private final SearchResultsPanel searchResultsPanel;

  public Application(RandoServiceInterface randoService) {
    super("Hollow Knight Spoiler Viewer 3");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.randoService = randoService;

    this.randoContext = new DataProvider<>(RandoContext.empty());
    this.bookmarks = new DataProvider<>(Bookmarks.empty());
    this.routeInfo = new DataProvider<>(RouteInfo.empty());
    this.searchDocumentFilter = new SearchDocumentFilter();
    this.searchDocumentSorter = new SearchDocumentSorter();
    this.routeIntent = new DataProvider<>(RouteIntent.empty());

    this.searchResultsPanel = new SearchResultsPanel(randoContext, bookmarks, routeInfo,
        searchDocumentFilter, searchDocumentSorter);

    Container cPane = getContentPane();
    cPane.setLayout(new BorderLayout());
    cPane.add(this.searchResultsPanel, BorderLayout.CENTER);

    JMenuBar menu = new JMenuBar();
    menu.add(aboutMenu());
    setJMenuBar(menu);

    new RandoContextUpdater(randoService, routeIntent, randoContext).start();

    pack();
    setVisible(true);
  }

  private static JMenu aboutMenu() {
    JMenu menu = new JMenu("About");

    // TOOD: Version

    menu.add(new LogMenuItem());

    return menu;
  }
}
