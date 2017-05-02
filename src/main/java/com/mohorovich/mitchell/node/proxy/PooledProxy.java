package com.mohorovich.mitchell.node.proxy;

import com.mohorovich.mitchell.node.Node;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Concurrent proxy, but uses a thread pool instead of thread per request.
 *
 * Has a much higher performance ceiling than implementation 2.
 */
public class PooledProxy extends Proxy implements Node, ConcurrentProxy {

	private static final Logger logger = LogManager.getLogger(SingleCoAPClient.class);

	ThreadedHTTPServer threadedHTTPServer;
	ExecutorService executorService = Executors.newCachedThreadPool();

	PooledProxy() {
		super();
		this.threadedHTTPServer = new ThreadedHTTPServer(this.httpPort, this);
	}

	@Override
	public void start() {
		this.threadedHTTPServer.listen();
	}

	@Override
	public void handleRequestAsynchronously(Socket client) {
		executorService.submit(new HTTPThread(client, threadedHTTPServer));
	}
}
