package com.mohorovich.mitchell.node.proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A baseline, super simple single threaded HTTP threadedHTTPServer.
 * Has a loop that listens at the socket, the thread then blocks when it awaits the response
 * from the proxy -> CoAP client module -> CoAP node threadedHTTPServer -> CoAP client module -> proxy -> HTTP threadedHTTPServer
 */
public class SingleThreadedHTTPServer extends HTTPServer {

	private static final Logger logger = LogManager.getLogger(SingleThreadProxy.class);

	SingleThreadProxy parentProxy;

	SingleThreadedHTTPServer(int port, SingleThreadProxy parent) {
		super(port);
		this.parentProxy = parent;
	}

	@Override
	public void listen() {
		logger.trace("Starting Single Threaded HTTP CoAP Proxy...");
		try {
			ServerSocket serverSocket = new ServerSocket(this.port);
			logger.trace("Created socket, listening...");
			for(;;) {
				Socket client = serverSocket.accept();
				byte[] payload = this.parentProxy.forwardRequest(client); //send request to proxy to handle.
				logger.trace("Writing response...");
				PrintWriter printWriter = new PrintWriter(client.getOutputStream());
				printWriter.print("HTTP/1.1 200\r\n");
				printWriter.print("Content-Type: text/plain\r\n");
				printWriter.print("Connection: close\r\n");
				printWriter.print("\r\n");
				printWriter.write(new String(payload));
				printWriter.write("\n\n");
				printWriter.close();
				client.close();
				logger.trace("Client connection closed.");
			}
		} catch (IOException e) {
			logger.error("Could not open ServerSocket.");
		}
	}

}
