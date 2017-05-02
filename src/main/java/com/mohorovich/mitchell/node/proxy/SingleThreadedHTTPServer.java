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
import java.util.Arrays;

/**
 * Created by mitchellmohorovich on 2017-05-01.
 */
public class SingleThreadedHTTPServer implements Node {

	private static final Logger logger = LogManager.getLogger(SingleThreadedHTTPCoAPProxy.class);

	SingleThreadedHTTPCoAPProxy parentProxy;

	int port;

	SingleThreadedHTTPServer(int port, SingleThreadedHTTPCoAPProxy parent) {
		this.port = port;
		this.parentProxy = parent;
	}

	@Override
	public void start() {
		logger.trace("Starting Single Threaded HTTP CoAP Proxy...");
		try {
			ServerSocket serverSocket = new ServerSocket(this.port);
			logger.trace("Created socket, listening...");
			for(;;) {
				Socket client = serverSocket.accept();
				byte[] payload = this.parentProxy.forwardRequest(client); //send request to proxy to handle.
				PrintWriter printWriter = new PrintWriter(client.getOutputStream());
				printWriter.print("HTTP/1.1 200\r\n");
				printWriter.print("Content-Type: text/plain\r\n");
				printWriter.print("Connection: close\r\n");
				printWriter.print("\r\n");
				printWriter.write(new String(payload));
				printWriter.close();
				client.close();
				/*
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter printWriter = new PrintWriter(client.getOutputStream());


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
				*/
			}
		} catch (IOException e) {
			logger.error("Could not open ServerSocket.");
		}
	}

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
