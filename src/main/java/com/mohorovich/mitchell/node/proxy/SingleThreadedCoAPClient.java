package com.mohorovich.mitchell.node.proxy;

import com.mohorovich.mitchell.node.Node;

import com.mohorovich.mitchell.node.utility.CoAPUtility;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by mitchellmohorovich on 2017-05-01.
 */
public class SingleThreadedCoAPClient extends CoapClient {

	private String url;
	private static final String DEFAULT_URL = "localhost:5683";

	private static final Logger logger = LogManager.getLogger(SingleThreadedCoAPClient.class);

	SingleThreadedCoAPClient() {
		this(DEFAULT_URL);
	}

	SingleThreadedCoAPClient(String url) {
		super(CoAPUtility.formattedURL(url));
		this.url = CoAPUtility.formattedURL(url);
		this.useNONs();
		logger.trace(String.format("Created SingleThreadedCoAPClient pointed to %s", url));
	}

	public CoapResponse sendRequestAndGetResponse() {
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
