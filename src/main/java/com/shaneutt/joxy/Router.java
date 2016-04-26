package com.shaneutt.joxy;

import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * The joxy Router interface defines a routing mechanism wherein
 * the consumer can add, remove, check for route existence and look at the
 * entire routing list and most importantly get() the next route without having
 * to be exposed to any of the underlying mechanisms of the routing algorithm.
 */
interface Router {
  // add an address to the router
  void add(String hostname, int port);
  void add(InetSocketAddress address);

  // remove an address from the router
  void remove(String hostname, int port);
  void remove(InetSocketAddress address);

  // check if a route exists in the router
  boolean routeExists(String hostname, int port);
  boolean routeExists(InetSocketAddress address);

  // get the next address via the router
  InetSocketAddress get();

  // return a list of all the current addresses being routed to
  ArrayList<InetSocketAddress> getAllRoutes();
}
