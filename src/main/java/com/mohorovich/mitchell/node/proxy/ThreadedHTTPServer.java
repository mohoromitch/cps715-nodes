package com.mohorovich.mitchell.node.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An HTTP server that is embedded in a concurrent proxy.
 *
 * When it receives a request, it gets pushed to the ConcurrentProxy and
 * further handling is taken care of by that.
 *
 * It also extends HTTPServer, so has the ability to respond to received requests.
 */
public class ThreadedHTTPServer extends HTTPServer {

	private static final Logger logger = LogManager.getLogger(SingleThreadProxy.class);

	private ConcurrentProxy concurrentProxy;

	ThreadedHTTPServer(int port, ConcurrentProxy concurrentProxy) {
		super(port);
		this.concurrentProxy = concurrentProxy;
	}

	@Override
	public void listen() {
		logger.trace("Starting Single Threaded HTTP CoAP Proxy...");
		try {
			ServerSocket serverSocket = new ServerSocket(this.port);
			logger.trace("Created socket, listening...");
			for (;;) {
				Socket client = serverSocket.accept();
				concurrentProxy.handleRequestAsynchronously(client);
			}
		} catch (IOException e) {
			logger.error("Could not open socket.");
		}
	}

}
