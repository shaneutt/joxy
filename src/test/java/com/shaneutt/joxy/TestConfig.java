package com.shaneutt.joxy;

import com.shaneutt.joxy.Config.Route;
import com.shaneutt.joxy.Config.Service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class TestConfig {
	@Test
	public void testConfig() throws FileNotFoundException, IOException {
    Config config = new Config("script/joxy-test-config.yml");

		// make sure we have a proper RoundRobinRouter
		assertThat(config, instanceOf(Config.class));

		// get the server config
		String expectedAddress = "127.0.0.1";
		Integer expectedPort   = 8080;
		assertEquals(expectedAddress, config.serverAddress);
		assertEquals(expectedPort, config.serverPort);

		// get the services
		ArrayList<Service> services = config.services;
		Service testService1 = services.get(0);
		Service testService2 = services.get(1);
		assertThat(testService1, instanceOf(Service.class));
		assertThat(testService2, instanceOf(Service.class));

		// check that the services are what we expect
		assertEquals("testservice1", testService1.name);
		assertEquals("/testing/",    testService1.path);
		assertEquals("testservice2", testService2.name);
		assertEquals("/testing2/",   testService2.path);

		// get the services' routes
		ArrayList<Route> testService1Routes = testService1.routes;
		ArrayList<Route> testService2Routes = testService2.routes;
		Route testService1Route1 = testService1Routes.get(0);
		Route testService1Route2 = testService1Routes.get(1);
		Route testService2Route1 = testService2Routes.get(0);
		Route testService2Route2 = testService2Routes.get(1);
		assertThat(testService1Route1, instanceOf(Route.class));
		assertThat(testService1Route2, instanceOf(Route.class));
		assertThat(testService2Route1, instanceOf(Route.class));
		assertThat(testService2Route2, instanceOf(Route.class));

		// make sure there are no unexpected routes
		assertEquals(2, testService1Routes.size());
		assertEquals(2, testService2Routes.size());

		// make sure the routes contain the addresses and ports expected
		assertEquals(testService1Route1.address, "127.0.0.1");
		assertEquals(testService1Route1.port, (Integer) 8081);
		assertEquals(testService1Route2.address, "127.0.0.1");
		assertEquals(testService1Route2.port, (Integer) 8082);
		assertEquals(testService2Route1.address, "127.0.0.1");
		assertEquals(testService2Route1.port, (Integer) 8083);
		assertEquals(testService2Route2.address, "127.0.0.1");
		assertEquals(testService2Route2.port, (Integer) 8084);
	}
}
