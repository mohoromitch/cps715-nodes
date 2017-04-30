package com.mohorovich.mitchell.node;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This node is the HTTP dummy node of the network topology,
 * it sends simple empty GET request to a URL.
 *
 * In the diagram below, the destination URL corresponds to the HTTP/CoAP proxy.
 * The behavior is labelled by order of execution.
 *
 *  +------+        +------------+     +------+
 *  | HTTP +---1---->  HTTP/CoAP +--2--> CoAP |
 *  | Node <---4----+  Proxy     <--3--+ Node |
 *  +------+        +------------+     +------+
 *
 */
public class HTTPClient implements Node {

	private final int RATE_INDEX = 1;
	private final int URL_INDEX = 2;

	private static final Logger logger = LogManager.getLogger(HTTPClient.class);

	private int ratePerSecond;
	private String url;

	public HTTPClient(String[] args) {
		this.ratePerSecond = Integer.parseInt(args[RATE_INDEX]);
		this.url = this.preparedURL(args[URL_INDEX]);
	}

	private String preparedURL(String url) {
		if(!url.startsWith("http://")) {
			return "http://" + url;
		} else {
			return url;
		}
	}

	/**
	 * Start sending requests to the given URL, at the set rate from the arguments.
	 */
	@Override
	public void start() {
		final ExecutorService es = Executors.newCachedThreadPool();
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				es.submit(new Runnable() {
					@Override
					public void run() {
						sendRequest();
					}
				});
			}
		}, 0, 1000/this.ratePerSecond, TimeUnit.MILLISECONDS);
	}

	/**
	 * Sends dummy request to URL assigned to HTTPClient.
	 * Passed as thread Runnable in start().
	 */
	private void sendRequest() {
		URL url;
		try {
			url = new URL(this.url);
			url.openStream();
		} catch (MalformedURLException e) {
			logger.error("Malformed URL: " + this.url);
		} catch (IOException e) {
			logger.error("Failed opening stream from url: " + this.url);
		}
		logger.trace("Successful request sent to " + this.url);
	}
}
