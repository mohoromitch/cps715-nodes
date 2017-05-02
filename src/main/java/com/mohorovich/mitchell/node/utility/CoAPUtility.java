package com.mohorovich.mitchell.node.utility;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by mitchellmohorovich on 2017-05-01.
 */
public class CoAPUtility {

	private static final Logger logger = LogManager.getLogger(CoAPUtility.class);

	public static String formattedURL(String url) {
		logger.trace("Formatting CoAP url");
		if(url.startsWith("coap://")) {
			logger.trace(String.format("%s begins with coap://", url));
			return url;
		} else {
			logger.trace(String.format("%s is being prepended with coap://", url));
			return "coap://" + url;
		}
	}

}
