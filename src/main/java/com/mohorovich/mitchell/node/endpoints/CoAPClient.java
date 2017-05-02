package com.mohorovich.mitchell.node.endpoints;

import com.mohorovich.mitchell.node.Node;
import com.mohorovich.mitchell.node.utility.CoAPUtility;
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
		super(CoAPUtility.formattedURL(args[URL_INDEX]));
		this.url = CoAPUtility.formattedURL(args[URL_INDEX]);
		this.useNONs();
		logger.trace("CoAPClient using NON's.");
		this.rate = Integer.parseInt(args[RATE_INDEX]);
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
