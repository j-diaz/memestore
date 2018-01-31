package com.memstore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KVStore implements Commands {

  protected final static Map<String, String> cache = new ConcurrentHashMap<>();

  public void set(String key, String value) {
    cache.put(key, value);
  }

  public String get(String key) {
    return cache.get(key);
  }
}
