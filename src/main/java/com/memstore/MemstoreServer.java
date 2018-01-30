package com.memstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MemstoreServer extends KVStore {

  final static Logger log = LoggerFactory.getLogger(MemstoreServer.class);

  private final static int DEFAULT_PORT = 11211;
  private ServerSocket serverSocket;

  public void listen() throws IOException {
    log.debug("Attempting to startup...");
    try {
      serverSocket = new ServerSocket(DEFAULT_PORT);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    log.info(String.format("Server listening on port: %d ...", DEFAULT_PORT));
    while (true) {
      try {
        Socket conn = serverSocket.accept();
        handleConnection(conn);
      } catch (IOException e) {
        log.error(e.getMessage());
      }
    }
  }

  private void handleConnection(Socket conn) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
    while (true) {
      String line = in.readLine();
      if ("".equals(line)) {
        break;
      }
      MemstoreResponse resp = handleCommand(line.split(" "));
      if (resp == null) {
        break;
      }
      out.print(resp.toString());
      out.flush();
    }
  }


  private MemstoreResponse handleCommand(String[] cmds) {
    String first = cmds[0];
    switch (first) {
      case "get": {
        String key = cmds[1];
        if (key == null) {
          break;
        }
        String value = get(key);
        log.info(String.format("Got command: %s, key: %s, value: %s", first, key, value));

        return (new MemstoreResponse())
          .setType(ResponseType.GET)
          .setParts(new String[] {"VALUE", cmds[1], "0", String.format("%d", value.length()) })
          .setValue(value)
          .setEndStatement("END");
      }
      case "set": {
        String key = cmds[1];
        String value = cmds[2];
        set(key, value);
        log.info(String.format("Got command: %s, key: %s, value: %s", first, key, value));

        return (new MemstoreResponse())
          .setType(ResponseType.SET)
          .setEndStatement("STORED");
      }
      default:
        // unrecognized command
        break;
    }
    return null;
  }

  public void stop() throws IOException {
    serverSocket.close();
    log.info("Server stopped listening");
  }

  public static void main(String[] args) throws IOException {
    MemstoreServer memcache = new MemstoreServer();
    memcache.listen();
  }
}
