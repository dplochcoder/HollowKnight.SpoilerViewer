package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class LogicMapDelta {

  public abstract ImmutableList<LogicMapDeltaEntry> entries();

  @Memoized
  public ImmutableMap<ObtainIndices, Logic> map() {
    return entries().stream()
        .collect(ImmutableMap.toImmutableMap(LogicMapDeltaEntry::key, LogicMapDeltaEntry::value));
  }

  public static TypeAdapter<LogicMapDelta> typeAdapter(Gson gson) {
    return new AutoValue_LogicMapDelta.GsonTypeAdapter(gson);
  }

  static {
    TypeAdapterRegistry.register(LogicMapDelta.class, LogicMapDelta::typeAdapter);
  }
}
