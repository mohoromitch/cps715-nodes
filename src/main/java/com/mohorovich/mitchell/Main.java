package com.mohorovich.mitchell;

import com.mohorovich.mitchell.node.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *  +------+        +------------+     +------+
 *  | HTTP +---1---->  HTTP/CoAP +--2--> CoAP |
 *  | Node <---4----+  Proxy     <--3--+ Node |
 *  +------+        +------------+     +------+
 *
 *  This application contains code to build a topology of nodes to test the performance of the concurrent HTTP/CoAP
 *  proxy for CPS716 - Computer Networks II
 *
 *  This gives the ability to create many nodes sending multiple requests a second.
 *  The results of the increasing load are to be objectively measured using the logged latency data.
 *
 */
public class Main {

	private final static String HTTP_MODE = "http";
	private final static String COAP_MODE = "coap";
	private static final Logger logger = LogManager.getLogger(Main.class);


	public static void main(String[] args) {
		logger.traceEntry();
		try {
			logger.trace("Creating node from arguments");
			createNodeFrom(args);
		} catch (Exception e) {
			logger.error("Node could not be made. Error thrown.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static Node createNodeFrom(String[] args) throws Exception {
		String mode = args[0].toLowerCase();
		switch (mode) {
			case HTTP_MODE:
				logger.trace("HTTP mode passed.");
				break;
			case COAP_MODE:
				logger.trace("CoAP mode passed.");
				break;
			default:
				throw new Exception("No such recognized mode: " + mode);
		}
		return null;
	}
}
