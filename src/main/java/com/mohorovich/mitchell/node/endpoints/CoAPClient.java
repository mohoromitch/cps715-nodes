package com.mohorovich.mitchell.node.endpoints;

import com.mohorovich.mitchell.node.Node;
import org.eclipse.californium.core.CoapClient;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.californium.core.CoapResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This CoAP client allows for nodes to be made that send out CoAP requests.
 */
public class CoAPClient extends CoapClient implements Node {

	private final static int URL_INDEX = 3;
	private final static int RATE_INDEX = 2;

	private static final Logger logger = LogManager.getLogger(CoAPClient.class);

	private int rate;
	private String url;

	public CoAPClient(String[] args) {
		super(formattedURL(args[URL_INDEX]));
		this.url = formattedURL(args[URL_INDEX]);
		this.useNONs();
		logger.trace("CoAPClient using NON's.");
		this.rate = Integer.parseInt(args[RATE_INDEX]);
	}

	private static String formattedURL(String url) {
		logger.trace("Formatting CoAP url");
		if(url.startsWith("coap://")) {
			logger.trace(String.format("%s begins with coap://", url));
			return url;
		} else {
			logger.trace(String.format("%s is being prepended with coap://", url));
			return "coap://" + url;
		}
	}

	@Override
	public void start() {
		final ExecutorService es = Executors.newCachedThreadPool();
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(() -> es.submit(this::sendRequest), 0, 1000/this.rate, TimeUnit.MILLISECONDS);
	}

	private void sendRequest() {
		long startTime, endTime;
		CoapResponse coapResponse;
		logger.trace(String.format("Sent request to %s...", this.url));
		startTime = System.nanoTime();
		coapResponse = this.get();
		endTime = System.nanoTime();
		if(coapResponse.isSuccess()) {
			logger.trace(String.format("Successful CoAP response received from %s in %d ns", this.url, endTime - startTime));
		} else {
			logger.trace(String.format("Failed to receive CoAP response from %s in %d ns", this.url, endTime - startTime));
		}
	}
}
