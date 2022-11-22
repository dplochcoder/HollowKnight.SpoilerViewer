package hkspoilerviewer.api;

import java.util.Map;
import java.util.function.Function;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import autovalue.shaded.com.google.common.collect.Maps;

public final class TypeAdapterRegistry {
  private TypeAdapterRegistry() {}

  private static Map<String, Function<Gson, ? extends TypeAdapter<?>>> adapters =
      Maps.newConcurrentMap();

  public static <T> void register(Class<T> clazz, Function<Gson, TypeAdapter<T>> adapter) {
    adapters.put(clazz.getCanonicalName(), adapter);
  }

  public static TypeAdapterFactory factory() {
    return new TypeAdapterFactory() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Function<Gson, ? extends TypeAdapter<?>> fn =
            adapters.get(type.getRawType().getCanonicalName());
        return (TypeAdapter<T>) (fn != null ? fn.apply(gson) : null);
      }
    };
  }
}
