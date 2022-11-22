package hkspoilerviewer.api;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public final class GsonInstance {
  private GsonInstance() {}

  private static final Gson INSTANCE =
      new GsonBuilder().registerTypeAdapterFactory(TypeAdapterRegistry.factory())
          .registerTypeAdapterFactory(immutableListFactory())
          .registerTypeAdapterFactory(immutableSetFactory())
          .registerTypeAdapterFactory(optionalFactory()).create();

  public static Gson gson() {
    return INSTANCE;
  }

  private static TypeAdapterFactory immutableListFactory() {
    return new TypeAdapterFactory() {
      @Override
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!type.getRawType().equals(ImmutableList.class)) {
          return null;
        }

        ParameterizedType pt = (ParameterizedType) type.getType();
        Type elem = pt.getActualTypeArguments()[0];
        return new TypeAdapter<T>() {
          @Override
          public void write(JsonWriter out, T value) throws IOException {
            out.beginArray();
            ImmutableList<?> list = (ImmutableList<?>) value;
            for (Object o : list) {
              gson.toJson(o, elem, out);
            }
            out.endArray();
          }

          @SuppressWarnings("unchecked")
          @Override
          public T read(JsonReader in) throws IOException {
            in.beginArray();

            ImmutableList.Builder<Object> builder = ImmutableList.builder();
            while (in.hasNext()) {
              Object o = gson.fromJson(in, elem);
              if (o instanceof Object[]) {
                o = ((Object[]) o)[0];
              }
              builder.add(o);
            }

            in.endArray();
            return (T) builder.build();
          }
        };
      }
    };
  }

  private static TypeAdapterFactory immutableSetFactory() {
    return new TypeAdapterFactory() {
      @Override
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!type.getRawType().equals(ImmutableSet.class)) {
          return null;
        }

        ParameterizedType pt = (ParameterizedType) type.getType();
        Type elem = pt.getActualTypeArguments()[0];
        return new TypeAdapter<T>() {
          @Override
          public void write(JsonWriter out, T value) throws IOException {
            out.beginArray();
            ImmutableSet<?> list = (ImmutableSet<?>) value;
            for (Object o : list) {
              gson.toJson(o, elem, out);
            }
            out.endArray();
          }

          @SuppressWarnings("unchecked")
          @Override
          public T read(JsonReader in) throws IOException {
            in.beginArray();

            ImmutableSet.Builder<Object> builder = ImmutableSet.builder();
            while (in.hasNext()) {
              Object o = gson.fromJson(in, elem);
              if (o instanceof Object[]) {
                o = ((Object[]) o)[0];
              }
              builder.add(o);
            }

            in.endArray();
            return (T) builder.build();
          }
        };
      }
    };
  }

  private static TypeAdapterFactory optionalFactory() {
    return new TypeAdapterFactory() {
      @Override
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!type.getRawType().equals(Optional.class)) {
          return null;
        }

        ParameterizedType pt = (ParameterizedType) type.getType();
        Type elem = pt.getActualTypeArguments()[0];
        return new TypeAdapter<T>() {
          @Override
          public void write(JsonWriter out, T value) throws IOException {
            Optional<?> o = (Optional<?>) value;
            if (o.isPresent()) {
              gson.toJson(o.get(), elem, out);
            } else {
              gson.toJson(JsonNull.INSTANCE);
            }
          }

          @SuppressWarnings("unchecked")
          @Override
          public T read(JsonReader in) throws IOException {
            Object o = gson.fromJson(in, elem);
            if (o instanceof Object[]) {
              o = ((Object[]) o)[0];
            }

            return (T) Optional.ofNullable(o);
          }
        };
      }
    };
  }
}
