package com.memstore;

import static com.memstore.ResponseType.*;

/**
 * Created by josediaz on 1/28/18.
 */
public class MemstoreResponse {

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
        sb.append(parts[0]);
        sb.append(" ");
        sb.append(parts[1]);
        sb.append(" ");
        sb.append(parts[2]);
        sb.append(" ");
        sb.append(parts[3]);
        sb.append("\n");
        sb.append(value);
        sb.append(endStatement);
        sb.append("\r\n");
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
    return sb.toString();
  }

}
