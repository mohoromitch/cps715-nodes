package com.mohorovich.mitchell;

import com.mohorovich.mitchell.node.*;
import com.sun.istack.internal.NotNull;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Objects;

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
	private final static String SERVER_MODE = "server";
	private final static String CLIENT_MODE = "client";
	private static final Logger logger = LogManager.getLogger(Main.class);


	public static void main(String[] args) {
		logger.traceEntry();
		Node node;
		try {
			logger.trace("Creating Node from arguments");
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
	@NotNull
	private static Node createNodeFrom(String[] args) throws Exception {
		if(args.length <= 1) {
			logger.error("Protocol and mode flags required.");
			throw new Exception("Not enough arguments provided.");
		}
		String protocol = args[0].toLowerCase();
		String mode = args[1].toLowerCase();
		switch (protocol) {
			case HTTP_MODE:
				logger.trace("HTTP protocol passed.");
				return new HTTPClient(args);
			case COAP_MODE:
				logger.trace("CoAP protocol passed");
				return (mode.equals(CLIENT_MODE)) ? new CoAPClient(args) : new CoAPServer(args);
			default:
				throw new Exception("No such recognized mode: " + protocol);
		}
	}
}
