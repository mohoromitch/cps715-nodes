package com.mohorovich.mitchell.node.proxy;

import com.mohorovich.mitchell.node.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mitchellmohorovich on 2017-05-01.
 */
public class SingleThreadedHTTPCoAPProxy implements Node {

	private static final Logger logger = LogManager.getLogger(SingleThreadedHTTPCoAPProxy.class);
	private static final int DEFAULT_PORT = 8000;

	int port;

	public SingleThreadedHTTPCoAPProxy() {
		this.port = DEFAULT_PORT;
		logger.trace(String.format("Created proxy listening on port %d", this.port));
	}

	public SingleThreadedHTTPCoAPProxy(int port) {
		this.port = port;
		logger.trace(String.format("Created proxy listening on port %d", this.port));
	}

	@Override
	public void start() {
		logger.trace("Starting Single Threaded HTTP CoAP Proxy...");
		try {
			ServerSocket serverSocket = new ServerSocket(this.port);
			logger.trace("Created socket, listening...");
			for(;;) {
				Socket client = serverSocket.accept();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter printWriter = new PrintWriter(client.getOutputStream());

				printWriter.print("HTTP/1.1 200 \r\n");
				printWriter.print("Content-Type: text/plain\r\n");
				printWriter.print("Connection: close\r\n");
				printWriter.print("\r\n");

				//echo the request
				String line;
				while( (line = bufferedReader.readLine()) != null) {
					if(line.length() == 0) {
						break;
					}
					printWriter.print(line + "\r\n");
				}
				logger.trace(String.format("Successfully responded to: %s %s", client.getInetAddress(), client.getPort()));
				printWriter.close();
				bufferedReader.close();
				client.close();
			}
		} catch (IOException e) {
			logger.error("Could not open ServerSocket.");
		}
	}
}
