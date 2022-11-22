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

  private static Map<Class<?>, Function<Gson, ? extends TypeAdapter<?>>> adapters =
      Maps.newConcurrentMap();

  public static <T> void register(Class<T> clazz, Function<Gson, TypeAdapter<T>> adapter) {
    adapters.put(clazz, adapter);
  }

  static {
    register(Item.class, Item::typeAdapter);
    register(ItemName.class, ItemName::typeAdapter);
    register(ItemPlacementData.class, ItemPlacementData::typeAdapter);
    register(ItemPlacementIndex.class, ItemPlacementIndex::typeAdapter);
    register(Location.class, Location::typeAdapter);
    register(LocationName.class, LocationName::typeAdapter);
    register(LogicMap.class, LogicMap::typeAdapter);
    register(LogicMapDelta.class, LogicMapDelta::typeAdapter);
    register(LogicMapDeltaEntry.class, LogicMapDeltaEntry::typeAdapter);
    register(LogicMapEntry.class, LogicMapEntry::typeAdapter);
    register(LogicMapSequence.class, LogicMapSequence::typeAdapter);
    register(LogicMapSequenceEntry.class, LogicMapSequenceEntry::typeAdapter);
    register(ObtainIndices.class, ObtainIndices::typeAdapter);
    register(RandoContext.class, RandoContext::typeAdapter);
    register(RandoContextRequest.class, RandoContextRequest::typeAdapter);
    register(RouteIntent.class, RouteIntent::typeAdapter);
  }

  public static TypeAdapterFactory factory() {
    return new TypeAdapterFactory() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<?> clazz = type.getRawType();
        while (clazz != null) {
          Function<Gson, ? extends TypeAdapter<?>> fn = adapters.get(clazz);
          if (fn != null) {
            return (TypeAdapter<T>) fn.apply(gson);
          }

          clazz = clazz.getSuperclass();
        }

        return null;
      }
    };
  }
}
