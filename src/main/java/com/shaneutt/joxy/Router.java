package com.shaneutt.joxy;

import java.util.ArrayList;
import java.net.InetSocketAddress;

/**
 * The joxy Router interface defines a routing mechanism wherein
 * the consumer can add, remove, check for route existence and look at the
 * entire routing list and most importantly get() the next route without having
 * to be exposed to any of the underlying mechanisms of the routing algorithm.
 */
interface Router {
  void add(String hostname, int port);
  void add(InetSocketAddress address);

  void remove(String hostname, int port);
  void remove(InetSocketAddress address);

  boolean routeExists(String hostname, int port);
  boolean routeExists(InetSocketAddress address);

  InetSocketAddress get();
  ArrayList<InetSocketAddress> getAllRoutes();
}
