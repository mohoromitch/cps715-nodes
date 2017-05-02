package com.mohorovich.mitchell.node.proxy;

import com.mohorovich.mitchell.node.utility.CoAPUtility;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;

/**
 * Created by mitchellmohorovich on 2017-05-01.
 */
class SingleCoAPClient extends CoapClient {

	private String url;
	private static final String DEFAULT_URL = "localhost:5683";

	private static final Logger logger = LogManager.getLogger(SingleCoAPClient.class);

	SingleCoAPClient() {
		this(DEFAULT_URL);
	}

	SingleCoAPClient(String url) {
		super(CoAPUtility.formattedURL(url));
		this.url = CoAPUtility.formattedURL(url);
		this.useNONs();
		logger.trace(String.format("Created SingleCoAPClient pointed to %s", url));
	}

	CoapResponse sendRequestAndGetResponse() {
		CoapResponse coapResponse;
		logger.trace(String.format("Sent request to %s...", this.url));
		coapResponse = this.get();
		if(coapResponse.isSuccess()) {
			logger.trace(String.format("Successful CoAP response received from %s.", this.url));
		} else {
			logger.trace(String.format("Failed to receive CoAP response from %s", this.url));
		}
		return coapResponse;
	}

}
