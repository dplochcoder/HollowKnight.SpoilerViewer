package hkspoilerviewer.api;

import java.util.HashMap;
import java.util.Map;
import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class LogicMap {

  public abstract ImmutableList<LogicMapEntry> entries();

  @Memoized
  public ImmutableMap<ObtainIndices, Logic> map() {
    return entries().stream()
        .collect(ImmutableMap.toImmutableMap(LogicMapEntry::key, LogicMapEntry::value));
  }

  public final Logic logic(ObtainIndices obtainIndices) {
    return map().get(obtainIndices);
  }

  public final LogicMap apply(Iterable<LogicMapDelta> deltas) {
    Map<ObtainIndices, Logic> map = new HashMap<>(map());
    deltas.forEach(delta -> map.putAll(delta.map()));
    return create(map);
  }

  public static LogicMap create(Map<ObtainIndices, Logic> logicByObtainIndices) {
    return new AutoValue_LogicMap(logicByObtainIndices.entrySet().stream()
        .map(LogicMapEntry::create).collect(ImmutableList.toImmutableList()));
  }

  private static final LogicMap EMPTY = create(ImmutableMap.of());

  public static LogicMap empty() {
    return EMPTY;
  }

  public static TypeAdapter<LogicMap> typeAdapter(Gson gson) {
    return new AutoValue_LogicMap.GsonTypeAdapter(gson);
  }
}
