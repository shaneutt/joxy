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

/**
 * Config provides the configuration and setup of the services and their routes
 * via configuration file, and the actual routing of those routes via an HttpHandler.
 *
 * <p>
 * Joxy uses Config to determine whether certain paths or all requests are routed
 * to various nodes underneath the load balancer.
 *
 * An app with a path of "/" configured would forward all http requests to its
 * configured routes. Conflicts are resolved by erroring on overlapped routes.
 *
 * @author      Shane Utt
 * @version     %I%, %G%
 * @since       1.0
 */
public class Config {
  // server config
  public String  serverAddress;
  public Integer serverPort;
  public ArrayList<Service> services = new ArrayList<Service>();

  /**
   * The constructor for Config will take the name of a YAML configuration file
   * and parse that file for server configuration and services and routes configuration.
   *
   * @param filename A String of the name of a YAML configuration file for Joxy
   * @see   script/joxy-test-config.yml
   */
  public Config(String filename) throws FileNotFoundException, IOException {
    Yaml yaml = new Yaml();

    // deal with the configuration file
    InputStream configFile = new FileInputStream(filename);
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

  /**
   * Loops through each configured service and starts an HttpHandler for its
   * path and routes.
   *
   * @param server The HttpServer you want routing for
   */
  public void routerSetup(HttpServer server) {
    for (Service service: services) {
      // get the path and the routes
      String           path   = service.path;
      ArrayList<Route> routes = service.routes;

      // give the routes, get a Router for this service
      RoundRobinRouter router = new RoundRobinRouter();

      // add each route available to the router
      for (Route route: routes) {
          router.add(route.address, route.port);
      }

      server.createContext(path, new MyHandler(router));
    }
  }

  /**
   * MyHandler is a simple HttpHandler that takes incoming requests and via a Router
   * routes those requests to the appropriate route.
   */
  static class MyHandler implements HttpHandler {
    private Router router;

    /**
     * Returns a new MyHandler
     *
     * @param router The Router class that implements a policy for determining the
     *               next route to use.
     */
    public MyHandler(Router newRouter) {
      router = newRouter;
    }

    /**
     * Handles HTTP requests and route distribution
     *
     * @param t An HttpExchange that will be used to forward the incoming request
     */
    @Override
    public void handle(HttpExchange t) throws IOException {
      String response = router.toString();
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }

  /**
   * A Route has an address and port for the purposes of adding to a Router
   */
  public class Route {
    public String  address;
    public Integer port;

    public Route(String routeAddress, Integer routePort) {
      address = routeAddress;
      port    = routePort;
    }
  }

  /**
   * A Service represents the underlying HTTP service we are load balancing to.
   * It has a name, an optional path, and any number of routes to the various nodes
   * for that service that we can load balance to.
   */
  public class Service {
    public String name;
    public String path;
    public ArrayList<Route> routes;

    /**
     * Returns a new Service object, if serviceRoutes is null will initiate a default empty "routes"
     */
    public Service(String serviceName, String servicePath, ArrayList<Route> serviceRoutes) {
      name   = serviceName;
      path   = servicePath;
      routes = serviceRoutes;
      if (routes == null)
        routes = new ArrayList<Route>();
    }

    /**
     * Add a route to the service
     */
    public void addRoute(Route route) {
      routes.add(route);
    }
  }
}
