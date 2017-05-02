package com.mohorovich.mitchell.node.proxy;

import com.mohorovich.mitchell.node.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

/**
 * This is the baseline, low throughout single threaded proxy.
 *
 * It runs a single loop that listens and opens up sockets.
 * When the socket is read, the HTTP request is used to create a SingleCoAPClient.
 * This SingleCoAPClient is a thin wrapper around Californium's CoapClient.
 * This SingleCoAPClient is then forwarded to the proxy reference, and the response is waited for.
 * When the response is received at the proxy, the payload is unpackaged and returned in a HTTP response.
 *
 */
public class SingleThreadProxy extends Proxy implements Node{

	private static final Logger logger = LogManager.getLogger(SingleThreadProxy.class);

	private SingleThreadedHTTPServer singleThreadedHTTPServer;

	public SingleThreadProxy() {
		super();
	}

	private SingleThreadProxy(int httpPort, int coApPort) {
		super(httpPort, coApPort);
		this.singleThreadedHTTPServer = new SingleThreadedHTTPServer(this.httpPort, this);
		logger.trace(String.format("Created Single Threaded HTTP/CoAP Proxy listening on httpPort %d", this.httpPort));
	}

	@Override
	public void start() {
		this.singleThreadedHTTPServer.listen();
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
		SingleCoAPClient singleCoAPClient = new SingleCoAPClient();
		return singleCoAPClient.sendRequestAndGetResponse().getPayload();
	}
}
