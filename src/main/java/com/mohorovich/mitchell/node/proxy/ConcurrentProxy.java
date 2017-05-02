package com.mohorovich.mitchell.node.proxy;

import java.net.Socket;

/**
 * Created by mitchellmohorovich on 2017-05-02.
 */
public interface ConcurrentProxy {
	public void handleRequestAsynchronously(Socket client);
}
