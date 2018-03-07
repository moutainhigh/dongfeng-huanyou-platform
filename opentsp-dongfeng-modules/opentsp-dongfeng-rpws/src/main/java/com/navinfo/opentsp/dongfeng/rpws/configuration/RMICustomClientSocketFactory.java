package com.navinfo.opentsp.dongfeng.rpws.configuration;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

public class RMICustomClientSocketFactory implements RMIClientSocketFactory, Serializable {
	
	private int timeout;
	
	public RMICustomClientSocketFactory(int timeout) {
		this.timeout = timeout;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Socket createSocket(String host, int port) throws IOException {
		Socket socket = new Socket();
		socket.setSoTimeout(timeout);
		socket.setSoLinger(true , 0);
		socket.setKeepAlive(false);
		socket.connect(new InetSocketAddress(host , port), timeout);
		return socket;
	}

}
