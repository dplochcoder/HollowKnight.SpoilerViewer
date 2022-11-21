package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class LogicMapSequence {
  public abstract LogicMap baseMap();

  public abstract ImmutableList<LogicMapSequenceEntry> entryList();

  @Memoized
  public ImmutableMap<ObtainIndices, LogicMapDelta> entryMap() {
    return entryList().stream().collect(
        ImmutableMap.toImmutableMap(LogicMapSequenceEntry::key, LogicMapSequenceEntry::value));
  }

  public final boolean isObtained(ObtainIndices obtainIndices) {
    return entryMap().containsKey(obtainIndices);
  }

  public final LogicMap getByIndex(int index) {
    return baseMap().apply(entryList().subList(0, index).parallelStream()
        .map(LogicMapSequenceEntry::value).collect(ImmutableList.toImmutableList()));
  }

  public final LogicMap getByObtain(ObtainIndices obtainIndices) {
    return getByIndex(entryMap().keySet().asList().indexOf(obtainIndices) + 1);
  }

  public final int size() {
    return entryList().size() + 1;
  }

  public static LogicMapSequence create(LogicMap baseMap,
      ImmutableList<LogicMapSequenceEntry> entryList) {
    return new AutoValue_LogicMapSequence(baseMap, entryList);
  }

  private static LogicMapSequence EMPTY = create(LogicMap.empty(), ImmutableList.of());

  public static LogicMapSequence empty() {
    return EMPTY;
  }

  public static TypeAdapter<LogicMapSequence> typeAdapter(Gson gson) {
    return new AutoValue_LogicMapSequence.GsonTypeAdapter(gson);
  }

  static {
    TypeAdapterRegistry.register(LogicMapSequence.class, LogicMapSequence::typeAdapter);
  }
}
