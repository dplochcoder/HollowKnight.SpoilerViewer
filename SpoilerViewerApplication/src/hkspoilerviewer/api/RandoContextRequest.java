package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class RandoContextRequest {
  public abstract ImmutableList<ObtainIndices> obtains();

  // TODO: Edits, files.

  public static TypeAdapter<RandoContextRequest> typeAdapter(Gson gson) {
    return new AutoValue_RandoContextRequest.GsonTypeAdapter(gson);
  }
}
