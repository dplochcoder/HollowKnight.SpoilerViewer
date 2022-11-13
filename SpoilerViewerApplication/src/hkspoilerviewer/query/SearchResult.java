package hkspoilerviewer.query;

import com.google.common.collect.ImmutableSet;
import hkspoilerviewer.api.Logic;
import hkspoilerviewer.api.RandoContext;

public interface SearchResult {
  enum LogicType {
    IN_LOGIC, IN_PURCHASE_LOGIC, OUT_OF_LOGIC
  }

  String render(RandoContext ctx);

  ImmutableSet<Integer> itemPlacementIds();

  Logic logic();
}
