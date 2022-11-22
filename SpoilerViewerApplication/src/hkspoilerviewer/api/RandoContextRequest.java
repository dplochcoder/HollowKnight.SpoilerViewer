package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class RandoContextRequest {
  public abstract RouteIntent routeIntent();

  // TODO: Edits, files.

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_RandoContextRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setRouteIntent(RouteIntent intent);

    public abstract RandoContextRequest build();
  }

  public static TypeAdapter<RandoContextRequest> typeAdapter(Gson gson) {
    return new AutoValue_RandoContextRequest.GsonTypeAdapter(gson);
  }
}
