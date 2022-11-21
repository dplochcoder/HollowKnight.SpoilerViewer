package hkspoilerviewer.api;

import java.util.Map;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

// This should be an inner class of LogicMapDelta but Eclipse is being a butt
@AutoValue
public abstract class LogicMapDeltaEntry {
  public abstract ObtainIndices key();

  public abstract Logic value();

  public static LogicMapDeltaEntry create(Map.Entry<ObtainIndices, Logic> entry) {
    return new AutoValue_LogicMapDeltaEntry(entry.getKey(), entry.getValue());
  }

  public static TypeAdapter<LogicMapDeltaEntry> typeAdapter(Gson gson) {
    return new AutoValue_LogicMapDeltaEntry.GsonTypeAdapter(gson);
  }
}
