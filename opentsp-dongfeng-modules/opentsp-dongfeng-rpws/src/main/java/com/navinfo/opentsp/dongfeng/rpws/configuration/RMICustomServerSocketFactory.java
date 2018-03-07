package com.navinfo.opentsp.dongfeng.rpws.configuration;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

public class RMICustomServerSocketFactory implements RMIServerSocketFactory, Serializable {

	private int timeout;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RMICustomServerSocketFactory(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		
		serverSocket.setSoTimeout(timeout);
		return serverSocket;
	}

}
