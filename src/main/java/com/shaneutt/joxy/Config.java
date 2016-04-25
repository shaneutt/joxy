package com.shaneutt.joxy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.ArrayList;
import org.yaml.snakeyaml.Yaml;

public class Config {
  // server config
  public String  serverAddress;
  public Integer serverPort;

  // config files
  private Map config = null;

  // constructor
  public Config(String fileName) throws FileNotFoundException, IOException {
    // TODO - get these values from config files
    Yaml yaml = new Yaml();

    // deal with the configuration file
    InputStream configFile = new FileInputStream(fileName);
    config = (Map) yaml.load(configFile);
    configFile.close();

    // get the server and route configurations
    Map serverConfig = (Map) config.get("server");
    ArrayList routeConfig = (ArrayList) config.get("routes");

    // store the server configuration and set defaults where needed
    serverAddress = (String) serverConfig.get("address");
    if (serverAddress == null)
      serverAddress = "127.0.0.1";

    serverPort = (Integer) serverConfig.get("port");
    if (serverPort == null)
      serverPort = 8080;

    // TODO - set the routes up
  }

  public static void routerSetup(HttpServer server) {
    // TODO - take multiple routes via configuration and handle separately
    server.createContext("/test", new MyHandler());
  }

  static class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
      // TODO - get rid of simple default and add routing layer here
      String response = "{\"success\":\"true\"}";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }
}
