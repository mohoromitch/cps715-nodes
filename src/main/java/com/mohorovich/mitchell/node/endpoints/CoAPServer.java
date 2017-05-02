package com.mohorovich.mitchell.node.endpoints;

import com.mohorovich.mitchell.node.Node;
import org.eclipse.californium.core.CoapServer;

/**
 * This node is a simple dummy CoAP node that responds to every request with the same payload data.
 */
public class CoAPServer extends CoapServer implements Node {

	public CoAPServer(String[] args) {
		super();
		this.add(new HelloResource("Hello!"));

	}

}
