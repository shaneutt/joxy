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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class Config {
  // server config
  public String  serverAddress;
  public Integer serverPort;
  public ArrayList<Service> services = new ArrayList<Service>();

  // constructor
  public Config(String fileName) throws FileNotFoundException, IOException {
    Yaml yaml = new Yaml();

    // deal with the configuration file
    InputStream configFile = new FileInputStream(fileName);
    Map config = (Map) yaml.load(configFile);
    configFile.close();

    // get the server and route configurations
    Map serverConfig = (Map) config.get("server");
    ArrayList<Map> routeConfig = (ArrayList<Map>) config.get("routes");

    // store the server configuration and set defaults where needed
    serverAddress = (String) serverConfig.get("address");
    if (serverAddress == null)
      serverAddress = "127.0.0.1";

    serverPort = (Integer) serverConfig.get("port");
    if (serverPort == null)
      serverPort = 8080;

    // build out all the services and their routes
    for (Map serviceMap: routeConfig) {
      // build the new service
      String serviceName = (String) serviceMap.get("name");
      String servicePath = (String) serviceMap.get("path");
      Service newService = new Service(serviceName, servicePath, null);

      // build the routes
      ArrayList<Map> routeList = (ArrayList<Map>) serviceMap.get("routes");
      for (Map routeMap: routeList) {
        String  routeAddress = (String)  routeMap.get("address");
        Integer routePort    = (Integer) routeMap.get("port");
        Route   newRoute     = new Route(routeAddress, routePort);
        newService.addRoute(newRoute);
      }

      // add the service and its route to the services
      services.add(newService);
    }
  }

  public void routerSetup(HttpServer server) {
    for (Service service: services) {
      String           path   = service.path;
      ArrayList<Route> routes = service.routes;
      server.createContext(path, new MyHandler(routes));
    }
  }

  static class MyHandler implements HttpHandler {
    private ArrayList<Route> routes;

    public MyHandler(ArrayList<Route> newRoutes) {
      routes = newRoutes;
    }

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

  public class Route {
    public String  address;
    public Integer port;

    public Route(String routeAddress, Integer routePort) {
      address = routeAddress;
      port    = routePort;
    }
  }

  public class Service {
    public String name;
    public String path;
    public ArrayList<Route> routes;

    public Service(String serviceName, String servicePath, ArrayList<Route> serviceRoutes) {
      name   = serviceName;
      path   = servicePath;
      routes = serviceRoutes;
      if (routes == null)
        routes = new ArrayList<Route>();
    }

    public void addRoute(Route route) {
      routes.add(route);
    }
  }
}
