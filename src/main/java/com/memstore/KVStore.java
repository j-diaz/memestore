package com.memstore;

import java.util.HashMap;
import java.util.Map;

public class KVStore implements Commands {

  protected final static Map<String, String> cache = new HashMap<>();

  public void set(String key, String value) {
    cache.put(key, value);
  }

  public String get(String key) {
    return cache.get(key);
  }
}
