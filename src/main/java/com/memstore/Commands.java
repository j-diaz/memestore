package com.memstore;

public interface Commands {

  void set(String key, String value);

  String get(String key);
}
