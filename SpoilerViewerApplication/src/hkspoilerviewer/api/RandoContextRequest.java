package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class RandoContextRequest {
  public abstract ImmutableList<ObtainIndices> obtains();

  // TODO: Edits, files.
}
