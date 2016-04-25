package com.shaneutt.joxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.net.InetSocketAddress;

/**
 * RoundRobinRouter is a simple router for InetSocketAddress addresses via
 * the "round-robin" mechanism. Each time a get() operation is performed within
 * the program, the router moves to the next address in the list sequintially.
 *
 * @author      Shane Utt
 * @version     %I%, %G%
 * @since       1.0
 */
public class RoundRobinRouter implements Router {
  // routeList is the list of routes we will loop through via get()
  private ArrayList<InetSocketAddress> routeList = new ArrayList<InetSocketAddress>();

  // index is the current route, or last route returned via get()
  private Integer index = 0;

  /**
   * Adds a given address to the routing table via hostname and port.
   *<p>
   * If the address already exists in the routing table, nothing is added.
   *
   * @param hostname a string of the hostname of the server, or IP address.
   * @param port     an int of the port number the host is listening on.
   */
  public synchronized void add(String hostname, int port) {
    add(new InetSocketAddress(hostname, port));
  }

  /**
   * Adds a given address to the routing table via InetSocketAddress.
   *
   * @param address InetSocketAddress that the host is listening on.
   */
  public synchronized void add(InetSocketAddress address) {
    if (!routeList.contains(address))
      routeList.add(address);
  }

  /**
   * Removes a given address from the routing table via hostname and port.
   *
   * @param hostname a string of the hostname of the server, or IP address.
   * @param port     an int of the port number the host is listening on.
   */
  public synchronized void remove(String hostname, int port) {
    remove(new InetSocketAddress(hostname, port));
  }

  /**
   * Removes a given address from the routing table via hostname and port.
   *
   * @param address InetSocketAddress that the host is listening on.
   */
  public synchronized void remove(InetSocketAddress address) {
    routeList.remove(address);
  }

  /**
   * Checks the existence of a an address in the routing table
   *
   * @param hostname a string of the hostname of the server, or IP address.
   * @param port     an int of the port number the host is listening on.
   */
  public synchronized boolean routeExists(String hostname, int port) {
    InetSocketAddress address = new InetSocketAddress(hostname, port);
    return routeExists(address);
  }

  /**
   * Checks the existence of a an address in the routing table
   *
   *
   * @param address InetSocketAddress that the host is listening on.
   */
  public synchronized boolean routeExists(InetSocketAddress address) {
    if (routeList.contains(address)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns the next InetSocketAddress via roundrobin for routing.
   *
   * <p>
   * This method is synchronized and expects that once you've called get()
   * that you're intending to use the address provided for a route.
   *
   * @return InetSocketAddress
   * @see    java.net.InetSocketAddress
   */
  public synchronized InetSocketAddress get() {
    index++;
    if (index + 1 > routeList.size())
      index = 0;
    return routeList.get(index);
  }

  /**
   * Returns a list of all the current routes in the routeList
   */
  public synchronized ArrayList<InetSocketAddress> getAllRoutes() {
    return routeList;
  }
}
