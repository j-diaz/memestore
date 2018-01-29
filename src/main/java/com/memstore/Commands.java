package com.memstore;

/**
 * Created by josediaz on 1/26/18.
 */
public interface Commands {

  void set(String key, String value);

  String get(String key);
}
