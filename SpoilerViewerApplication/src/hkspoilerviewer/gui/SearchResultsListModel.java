package hkspoilerviewer.gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import com.google.common.collect.ImmutableList;
import hkspoilerviewer.api.RandoContext;
import hkspoilerviewer.lib.DataProvider;
import hkspoilerviewer.lib.ListenerManager;
import hkspoilerviewer.lib.UpdateSubscriber;
import hkspoilerviewer.query.Bookmarks;
import hkspoilerviewer.query.RouteInfo;
import hkspoilerviewer.query.SearchContext;
import hkspoilerviewer.query.SearchDocument;
import hkspoilerviewer.query.SearchDocumentFilter;
import hkspoilerviewer.query.SearchDocumentSorter;

public final class SearchResultsListModel implements UpdateSubscriber, ListModel<String> {

  private final DataProvider<RandoContext> randoContext;
  private final DataProvider<Bookmarks> bookmarks;
  private final DataProvider<RouteInfo> routeInfo;
  private final DataProvider<SearchDocumentFilter> searchFilter;
  private final DataProvider<SearchDocumentSorter> searchSorter;

  private final ListenerManager<ListDataListener> listeners = new ListenerManager<>();

  public SearchResultsListModel(DataProvider<RandoContext> randoContext,
      DataProvider<Bookmarks> bookmarks, DataProvider<RouteInfo> routeInfo,
      DataProvider<SearchDocumentFilter> searchFilter,
      DataProvider<SearchDocumentSorter> searchSorter) {
    this.randoContext = randoContext;
    this.bookmarks = bookmarks;
    this.routeInfo = routeInfo;
    this.searchFilter = searchFilter;
    this.searchSorter = searchSorter;

    randoContext.addListener((s, d) -> s.addSubscriber(this));
    bookmarks.addListener((s, d) -> s.addSubscriber(this));
    routeInfo.addListener((s, d) -> s.addSubscriber(this));
    searchFilter.addListener((s, d) -> s.addSubscriber(this));
    searchSorter.addListener((s, d) -> s.addSubscriber(this));
  }

  private final List<SearchResultsListModelItem> items = new ArrayList<>();

  private ImmutableList<SearchDocument> docs(SearchContext ctx) {
    var filter = searchFilter.get();
    var sorter = searchSorter.get();
    return ctx.rando().obtains().stream().map(obtain -> SearchDocument.create(ctx, obtain))
        .filter(d -> filter.accept(d)).sorted(sorter).collect(ImmutableList.toImmutableList());
  }

  @Override
  public void onUpdateSessionComplete() {
    // TODO: Update cursor.
    int oldMax = items.size();
    items.clear();

    SearchContext ctx = SearchContext.builder().setRando(randoContext.get())
        .setBookmarks(bookmarks.get()).setRouteInfo(routeInfo.get()).build();

    // TODO: Bookmarks, hidden.
    docs(ctx).forEach(d -> items.add(SearchResultsListModelItem.result(d, false)));

    int newMax = items.size();

    ListDataEvent event =
        new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, Math.max(oldMax, newMax));
    listeners.forEach(l -> l.contentsChanged(event));
  }

  @Override
  public synchronized int getSize() {
    return items.size();
  }

  @Override
  public synchronized String getElementAt(int index) {
    return items.get(index).render().text();
  }

  public synchronized SearchResultsListModelItem getItemAt(int index) {
    return items.get(index);
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listeners.addListener(l);
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listeners.removeListener(l);
  }

}
