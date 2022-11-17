package hkspoilerviewer.api;

import java.util.HashMap;
import java.util.Map;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;

@AutoValue
public abstract class LogicMap {
  public abstract ImmutableMap<ObtainIndices, Logic> logicByObtainIndices();

  public final Logic logic(ObtainIndices obtainIndices) {
    return logicByObtainIndices().get(obtainIndices);
  }

  public final LogicMap apply(Iterable<LogicMapDelta> deltas) {
    Map<ObtainIndices, Logic> map = new HashMap<>(logicByObtainIndices());
    deltas.forEach(delta -> map.putAll(delta.deltaByObtainIndices()));
    return create(map);
  }

  public static LogicMap create(Map<ObtainIndices, Logic> logicByObtainIndices) {
    return new AutoValue_LogicMap(ImmutableMap.copyOf(logicByObtainIndices));
  }
}
