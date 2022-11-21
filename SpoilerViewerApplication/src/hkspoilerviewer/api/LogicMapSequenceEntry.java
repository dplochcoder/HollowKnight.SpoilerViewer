package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

// This should be an inner class of LogicMapSequence but Eclipse is being a butt
@AutoValue
public abstract class LogicMapSequenceEntry {
  public abstract ObtainIndices key();

  public abstract LogicMapDelta value();

  public static TypeAdapter<LogicMapSequenceEntry> typeAdapter(Gson gson) {
    return new AutoValue_LogicMapSequenceEntry.GsonTypeAdapter(gson);
  }
}
