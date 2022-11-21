package hkspoilerviewer.api;

import java.util.Map;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

// This should be an inner class of LogicMap but Eclipse is being a butt
@AutoValue
public abstract class LogicMapEntry {
  public abstract ObtainIndices key();

  public abstract Logic value();

  public static LogicMapEntry create(Map.Entry<ObtainIndices, Logic> entry) {
    return new AutoValue_LogicMapEntry(entry.getKey(), entry.getValue());
  }

  public static TypeAdapter<LogicMapEntry> typeAdapter(Gson gson) {
    return new AutoValue_LogicMapEntry.GsonTypeAdapter(gson);
  }
}
