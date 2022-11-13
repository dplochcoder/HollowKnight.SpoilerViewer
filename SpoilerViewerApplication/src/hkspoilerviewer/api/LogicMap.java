package hkspoilerviewer.api;

import java.util.HashMap;
import java.util.Map;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;

@AutoValue
public abstract class LogicMap {
  public abstract ImmutableMap<Integer, Logic> logicByPlacementId();

  public final Logic logic(int placementId) {
    return logicByPlacementId().get(placementId);
  }

  public final LogicMap apply(Iterable<LogicMapDelta> deltas) {
    Map<Integer, Logic> map = new HashMap<>(logicByPlacementId());
    deltas.forEach(delta -> map.putAll(delta.deltaByPlacementId()));
    return create(map);
  }

  public static LogicMap create(Map<Integer, Logic> logicByPlacementId) {
    return new AutoValue_LogicMap(ImmutableMap.copyOf(logicByPlacementId));
  }
}
