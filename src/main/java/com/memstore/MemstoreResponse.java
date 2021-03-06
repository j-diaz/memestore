package com.memstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemstoreResponse {

  Logger log = LoggerFactory.getLogger(MemstoreResponse.class);

  private ResponseType type;
  private String[] parts;
  private String value;
  private String endStatement;

  public MemstoreResponse setType(ResponseType type) {
    this.type = type;
    return this;
  }

  public MemstoreResponse setParts(String[] parts) {
    this.parts = parts;
    return this;
  }

  public MemstoreResponse setValue(String value) {
    this.value = value;
    return this;
  }

  public MemstoreResponse setEndStatement(String endStatement) {
    this.endStatement = endStatement;
    return this;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    switch (type) {
      case GET: {
        if (value.equals("")) {
          sb.append("NOT_FOUND\r\n");
          break;
        }
	sb.append(
	  String.format(
	    "%s %s %s %s\n%s\n%s\r\n",
	    parts[0], parts[1], parts[2], parts[3], value, endStatement
          )
        );
        break;
      }
      case SET: {
        sb.append(endStatement);
        sb.append("\r\n");
        break;
      }
      default:
        break;
    }
    log.debug(String.format("MemstoreResponse: %s", sb.toString()));
    return sb.toString();
  }

}
