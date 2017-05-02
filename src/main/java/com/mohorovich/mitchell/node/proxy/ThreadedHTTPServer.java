package com.mohorovich.mitchell.node.proxy;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by mitchellmohorovich on 2017-05-02.
 */
public class ThreadedHTTPServer extends HTTPServer {

	private static final Logger logger = LogManager.getLogger(SingleThreadProxy.class);

	private ThreadedProxy threadedProxy;

	ThreadedHTTPServer(int port, ThreadedProxy threadedProxy) {
		super(port);
		this.threadedProxy = threadedProxy;
	}

	@Override
	public void listen() {
		logger.trace("Starting Single Threaded HTTP CoAP Proxy...");
		try {
			ServerSocket serverSocket = new ServerSocket(this.port);
			logger.trace("Created socket, listening...");
			for (;;) {
				Socket client = serverSocket.accept();
				threadedProxy.handleSocket(client);
			}
		} catch (IOException e) {
			logger.error("Could not open socket.");
		}
	}

}
