package com.mohorovich.mitchell.node.proxy;

import com.mohorovich.mitchell.node.Node;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by mitchellmohorovich on 2017-05-02.
 */
public class ProxyFactory {

	private static final Logger logger = LogManager.getLogger(SingleCoAPClient.class);

	private static final String SINGLE_THREADED_PROXY_ARGUMENT = "single";
	private static final String MULTI_THREADED_PROXY_ARGUMENT = "threaded";
	private static final String THREAD_POOLED_PROXY_ARGUMENT = "pooled";

	public static Node getHTTPCoAPProxy(String type) {
		logger.trace(String.format("Generating proxy for argument %s", type));
		switch(type.toLowerCase()) {
			case SINGLE_THREADED_PROXY_ARGUMENT:
				logger.trace("Creating single threaded proxy.");
				return new SingleThreadProxy();
			case MULTI_THREADED_PROXY_ARGUMENT:
				logger.trace("Creating multi threaded proxy.");
				return new ThreadedProxy();
			case THREAD_POOLED_PROXY_ARGUMENT:
				logger.trace("Creating thread pooled proxy.");
				return new PooledProxy();
			default:
				logger.trace("Invalid proxy argument provided, throwing exception.");
				throw new IllegalArgumentException("Invalid proxy argument provided");
		}
	}
}
