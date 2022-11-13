package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;

@AutoValue
public abstract class LogicMapDelta {
  public abstract ImmutableMap<Integer, Logic> deltaByPlacementId();
}
