package com.mohorovich.mitchell.node.proxy;

import com.mohorovich.mitchell.node.Node;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.Socket;

/**
 * Created by mitchellmohorovich on 2017-05-02.
 */
public class ThreadedProxy extends Proxy implements Node {

	private static final Logger logger = LogManager.getLogger(SingleCoAPClient.class);

	ThreadedProxy() {
		super();
	}

	@Override
	public void start() {
	}

	public void handleSocket(Socket client) {
		Runnable requestHandler = new HTTPThread(client);
		Thread thread = new Thread(requestHandler);
		thread.start();
	}

}
