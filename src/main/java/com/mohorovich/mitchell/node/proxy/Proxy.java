package com.mohorovich.mitchell.node.proxy;

/**
 * Abstract class that holds logic and variables common to all the proxy implementations in the project.
 */
abstract class Proxy {
	private static final int DEFAULT_HTTP_SERVER_PORT = 8000;
	private static final int DEFAULT_COAP_CLIENT_PORT = 5683;

	protected int httpPort;
	protected int coApPort;

	public Proxy() {
		this.httpPort = DEFAULT_HTTP_SERVER_PORT;
		this.coApPort = DEFAULT_COAP_CLIENT_PORT;
	}

	public Proxy(int httpPort, int coApPort) {
		this.httpPort = httpPort;
		this.coApPort = coApPort;
	}

}
