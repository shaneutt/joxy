package com.shaneutt.joxy;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class TestRouter {
	@Test
	public void testRoundRobinRouter() {
    RoundRobinRouter router = new RoundRobinRouter();

		// make sure we have a proper RoundRobinRouter
		assertThat(router, instanceOf(RoundRobinRouter.class));

		// lets add localhost to the router
		InetSocketAddress localhost = new InetSocketAddress("127.0.0.1", 8080);
		router.add(localhost);
		assertTrue(router.routeExists(localhost));

		// we should not be able to add a route more than once
		ArrayList<InetSocketAddress> expectedRouteList = new ArrayList<InetSocketAddress>();
		expectedRouteList.add(localhost);
		router.add(localhost);
		router.add(localhost);
		assertEquals(expectedRouteList, router.getAllRoutes());

		// we should loop properly with one address in the routeTable
		assertEquals(localhost, router.get());
		assertEquals(localhost, router.get());

		// lets add another new host
		InetSocketAddress newHost = new InetSocketAddress("192.168.1.1", 8080);
		router.add(newHost);
		expectedRouteList.add(newHost);
		assertEquals(expectedRouteList, router.getAllRoutes());

		// now that we have another host, lets make sure the round-robin is working
		assertEquals(newHost, router.get());
		assertEquals(localhost, router.get());

		// lets add yet another new host
		InetSocketAddress newerHost = new InetSocketAddress("192.168.1.2", 8080);
		router.add(newerHost);
		expectedRouteList.add(newerHost);
		assertEquals(expectedRouteList, router.getAllRoutes());

		// and ensure round-robin is working
		assertEquals(newHost, router.get());
		assertEquals(newerHost, router.get());
	}
}
