package com.shaneutt.joxy;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class Server {
  public static void main(String[] args) throws Exception {
		// set up the server, get the server config
		System.out.println("Starting Joxy Server...");
		Config config = new Config("FIXME");

		// set up address and server
		InetSocketAddress address = new InetSocketAddress(config.serverAddress, config.serverPort);
		HttpServer server = HttpServer.create(address, 0);

		// set up route handling
		config.routerSetup(server);

		// run the server
		server.setExecutor(null); // creates a default executor
		server.start();

		System.out.format(
		  "started the server on address [%s] on port [%s]%n",
		  address.getAddress(),
		  address.getPort()
		);
  }
}
