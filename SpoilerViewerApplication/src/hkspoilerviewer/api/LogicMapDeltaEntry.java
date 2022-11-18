package hkspoilerviewer.api;

import java.util.Map;
import com.google.auto.value.AutoValue;

// This should be an inner class of LogicMapDelta but Eclipse is being a butt
@AutoValue
public abstract class LogicMapDeltaEntry {
  public abstract ObtainIndices key();

  public abstract Logic value();

  public static LogicMapDeltaEntry create(Map.Entry<ObtainIndices, Logic> entry) {
    return new AutoValue_LogicMapDeltaEntry(entry.getKey(), entry.getValue());
  }
}
