package com.mohorovich.mitchell.node.utility;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * Created by mitchellmohorovich on 2017-05-01.
 */
public class ProxyResource {

	private static final String DEFAULT_NAME = "ProxyResource";
	private static final String[] DEFAULT_PAYLOAD = new String[]{"hello!"};

	private static final Logger logger = LogManager.getLogger(ProxyResource.class);

	private String[] payload;
	String name;

	public ProxyResource(String name) {
		this(DEFAULT_PAYLOAD, name);
	}

	ProxyResource(String[] payload) {
		this(payload, DEFAULT_NAME);
	}

	ProxyResource(String[] payload, String name) {
		this.payload = payload;
		this.name = name;
	}

	public void handleGET(CoapExchange exchange) {
		logger.trace("Received GET...");
		exchange.respond(this.payload[0]);
	}

	public String getName() {
		return this.name;
	}

}
