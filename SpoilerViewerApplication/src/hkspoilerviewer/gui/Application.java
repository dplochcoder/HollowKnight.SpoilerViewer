package hkspoilerviewer.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
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
        searchDocumentFilter, searchDocumentSorter, () -> pack());

    Container cPane = getContentPane();
    cPane.setLayout(new BorderLayout());

    JScrollPane srPanel = new JScrollPane(this.searchResultsPanel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    srPanel.setPreferredSize(new Dimension(400, 600));
    cPane.add(srPanel, BorderLayout.CENTER);

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
