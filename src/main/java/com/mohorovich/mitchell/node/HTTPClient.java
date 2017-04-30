package com.mohorovich.mitchell.node;

import javafx.concurrent.Task;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This node is the HTTP dummy node of the network topology,
 * it sends simple GET request to an IP with a URI at a constant rate.
 */
public class HTTPClient implements Node {

	private final int RATE_INDEX = 1;
	private final int URL_INDEX = 2;

	private static final Logger logger = LogManager.getLogger(HTTPClient.class);

	private int ratePerSecond;
	private String url;

	public HTTPClient(String[] args) {
		this.ratePerSecond = Integer.parseInt(args[RATE_INDEX]);
		this.url = args[URL_INDEX];
	}

	/**
	 * Start sending requests
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
