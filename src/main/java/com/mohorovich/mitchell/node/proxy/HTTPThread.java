package com.mohorovich.mitchell.node.proxy;

import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A HTTP thread that is used in the concurrent implementations of the HTTP server
 * for use in the concurrent implementations of the HTTP/CoAP proxy.
 */
public class HTTPThread implements Runnable {

	private static final Logger logger = LogManager.getLogger(SingleCoAPClient.class);

	private Socket client;
	private HTTPServer server;

	HTTPThread(Socket client, HTTPServer server) {
		this.client = client;
		this.server = server;
		logger.trace("New HTTPThread created.");
	}

	@Override
	public void run() {
		logger.trace("HTTPThread running...");
		logger.trace("Creating CoAPClient...");
		SingleCoAPClient singleCoAPClient = new SingleCoAPClient();
		logger.trace("Writing response.");
		this.server.writeResponse(this.client, singleCoAPClient.sendRequestAndGetResponse().getPayload());
	}
}
