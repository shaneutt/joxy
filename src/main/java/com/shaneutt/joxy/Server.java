package com.shaneutt.joxy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
		public static void main(String[] args) throws Exception {
				System.out.println("Starting Joxy Server...");

				// set up address and server
				// TODO - Take Inet address and port from a configuration file
				InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);
				HttpServer server = HttpServer.create(address, 0);

				// set up route handling
				// TODO - take multiple routes via configuration and handle separately
				server.createContext("/test", new MyHandler());

				// set up defaults
				server.setExecutor(null); // creates a default executor

				// run the server
				server.start();

				System.out.format(
					"started the server on address [%s] on port [%s]%n",
					address.getAddress(),
					address.getPort()
				);
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
