package com.richard.eventsourcing.inmemory;

import io.vavr.control.Try;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TypeCache {

  private static TypeCache instance;
  private static final Map<String, Class<?>> cache = new HashMap<>();

  private TypeCache() {
  }

  public static TypeCache getInstance() {
    if (instance == null) {
      instance = new TypeCache();
    }
    return instance;
  }

  public Optional<? extends Class<?>> get(String clzz) {
    Class<?> aClass = cache.get(clzz);
    if (aClass != null) {
      return Optional.of(aClass);
    }

    return Try.of(() -> Class.forName(clzz))
        .andThen(item -> cache.put(clzz, item))
        .toJavaOptional();
  }

}
