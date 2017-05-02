package com.mohorovich.mitchell.node.proxy;

import com.mohorovich.mitchell.node.Node;
import com.mohorovich.mitchell.node.utility.HTTPCoAPConverter;
import com.mohorovich.mitchell.node.utility.ProxyResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by mitchellmohorovich on 2017-05-01.
 */
public class SingleThreadedHTTPCoAPProxy implements Node {

	private static final Logger logger = LogManager.getLogger(SingleThreadedHTTPCoAPProxy.class);
	private static final int DEFAULT_HTTP_SERVER_PORT = 8000;
	private static final int DEFAULT_COAP_CLIENT_PORT = 5683;

	private int httpPort;
	private int coApPort;
	private SingleThreadedHTTPServer singleThreadedHTTPServer;

	public SingleThreadedHTTPCoAPProxy() {
		this(DEFAULT_HTTP_SERVER_PORT, DEFAULT_COAP_CLIENT_PORT);
	}

	private SingleThreadedHTTPCoAPProxy(int httpPort, int coApPort) {
		this.httpPort = httpPort;
		this.coApPort = coApPort;
		this.singleThreadedHTTPServer = new SingleThreadedHTTPServer(this.httpPort, this);
		logger.trace(String.format("Created Single Threaded HTTP/CoAP Proxy listening on httpPort %d", this.httpPort));
	}

	@Override
	public void start() {
		this.singleThreadedHTTPServer.start();
	}

	/**
	 * When the HTTP server receives a request, it forwards it to the proxy.
	 * The proxy then:
	 * - Forwards the request to the CoAP server (Californium)
	 * 	- (For this implementation we're just using one CoAP client for feasibility)
	 * - Gets the response from the CoAP server
	 * - Writes the body of the response back to the HTTP request
	 * @param client
	 */
	byte[] forwardRequest(Socket client) {
		SingleThreadedCoAPClient singleThreadedCoAPClient = new SingleThreadedCoAPClient();
		return singleThreadedCoAPClient.sendRequestAndGetResponse().getPayload();
	}
}
