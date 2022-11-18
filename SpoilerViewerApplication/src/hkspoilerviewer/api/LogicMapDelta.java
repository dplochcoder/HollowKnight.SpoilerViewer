package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@AutoValue
public abstract class LogicMapDelta {

  public abstract ImmutableList<LogicMapDeltaEntry> entries();

  @Memoized
  public ImmutableMap<ObtainIndices, Logic> map() {
    return entries().stream()
        .collect(ImmutableMap.toImmutableMap(LogicMapDeltaEntry::key, LogicMapDeltaEntry::value));
  }
}
