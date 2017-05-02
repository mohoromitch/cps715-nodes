package com.mohorovich.mitchell.node.proxy;

import com.mohorovich.mitchell.node.Node;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.Socket;

/**
 * Created by mitchellmohorovich on 2017-05-02.
 */
public class ThreadedProxy extends Proxy implements Node, ConcurrentProxy {

	private static final Logger logger = LogManager.getLogger(SingleCoAPClient.class);
	ThreadedHTTPServer threadedHTTPServer;

	ThreadedProxy() {
		super();
		this.threadedHTTPServer = new ThreadedHTTPServer(this.httpPort, this);
	}

	@Override
	public void start() {
		this.threadedHTTPServer.listen();
	}

	@Override
	public void handleRequestAsynchronously(Socket client) {
		Runnable requestHandler = new HTTPThread(client, threadedHTTPServer);
		Thread thread = new Thread(requestHandler);
		thread.start();
	}
}
