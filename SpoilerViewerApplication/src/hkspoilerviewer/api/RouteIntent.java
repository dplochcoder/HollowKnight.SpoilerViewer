package hkspoilerviewer.api;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class RouteIntent {
  public abstract ImmutableSet<ObtainIndices> obtains();

  public static RouteIntent create(Iterable<ObtainIndices> obtains) {
    return new AutoValue_RouteIntent(ImmutableSet.copyOf(obtains));
  }

  private static final RouteIntent EMPTY = create(ImmutableSet.of());

  public static RouteIntent empty() {
    return EMPTY;
  }

  public static TypeAdapter<RouteIntent> typeAdapter(Gson gson) {
    return new AutoValue_RouteIntent.GsonTypeAdapter(gson);
  }

  static {
    TypeAdapterRegistry.register(RouteIntent.class, RouteIntent::typeAdapter);
  }
}
