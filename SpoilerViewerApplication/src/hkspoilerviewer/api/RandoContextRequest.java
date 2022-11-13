package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class RandoContextRequest {
  public static final int DEFAULT_BASE_ID = 0;

  public abstract int baseId();

  public abstract ImmutableList<Integer> placementAcquisitions();

  // TODO: Edits.
}
