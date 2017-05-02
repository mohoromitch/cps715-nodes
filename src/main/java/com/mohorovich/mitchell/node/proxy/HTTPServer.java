package com.mohorovich.mitchell.node.proxy;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract class that holds common logic for all HTTPServers for the proxy implementations.
 */
public abstract class HTTPServer {

	private static final Logger logger = LogManager.getLogger(SingleThreadProxy.class);

	/**
	 * This function tells the HTTPServer to begin listening for requests.
	 * Abstract since each implementation handles requests differently.
	 */
	public abstract void listen();

	public void sendError(Socket client) {
		try {
			PrintWriter printWriter = new PrintWriter(client.getOutputStream());
			printWriter.print("HTTP/1.1 500\r\n");
			printWriter.print("Content-Type: text/plain\r\n");
			printWriter.print("Connection: close\r\n");
			printWriter.print("\r\n");
			printWriter.close();
		} catch (IOException e) {
			logger.error("Error writing to response socket.");
		}
	}
}
