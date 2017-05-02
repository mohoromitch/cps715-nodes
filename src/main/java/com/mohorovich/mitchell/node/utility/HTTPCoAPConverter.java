package com.mohorovich.mitchell.node.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by mitchellmohorovich on 2017-05-01.
 */
public class HTTPCoAPConverter {
	public static ProxyResource convertSocketToProxyResource(Socket client) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String line = bufferedReader.readLine();
		StringTokenizer stringTokenizer = new StringTokenizer(line);
		stringTokenizer.nextToken();
		String URI = stringTokenizer.nextToken();
		return new ProxyResource(URI);
	}
}
