package com.mohorovich.mitchell;

import com.mohorovich.mitchell.node.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
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
		Node node;
		try {
			logger.trace("Creating node from arguments");
			node = createNodeFrom(args);
			node.start();
		} catch (Exception e) {
			logger.error("Node could not be made. Error thrown.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Given arguments, returns the node that this application will run.
	 * @param args The program arguments passed in.
	 * @return A Node corresponding to the given arguments.
	 * @throws Exception If the arguments passed in are invalid.
	 */
	private static Node createNodeFrom(String[] args) throws Exception {
		if(args.length == 0) {
			logger.error("No mode flag provided.");
			throw new Exception("No mode flag provided");
		}
		String mode = args[0].toLowerCase();
		switch (mode) {
			case HTTP_MODE:
				logger.trace("HTTP mode passed.");
				return new HTTPClient(args);
			case COAP_MODE:
				logger.trace("CoAP mode passed.");
				break;
			default:
				throw new Exception("No such recognized mode: " + mode);
		}
		return null;
	}
}
