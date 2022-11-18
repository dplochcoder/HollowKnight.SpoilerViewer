package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;

// This should be an inner class of LogicMapSequence but Eclipse is being a butt
@AutoValue
public abstract class LogicMapSequenceEntry {
  public abstract ObtainIndices key();

  public abstract LogicMapDelta value();
}
