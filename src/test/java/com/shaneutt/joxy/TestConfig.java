package com.shaneutt.joxy;

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

		// get the routes config
		// ArrayList routeConfig = (ArrayList) config.config.get("routes");
		// assertEquals(true, routeConfig);
	}
}
