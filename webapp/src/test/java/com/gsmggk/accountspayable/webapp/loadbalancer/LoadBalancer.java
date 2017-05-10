package com.gsmggk.accountspayable.webapp.loadbalancer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoadBalancer {

	public static void main(String[] args) throws IOException {
		
		 PortForwardConfig config = PortForwardConfig.getConfig(args);
		 ServerSocket serverSocket =
	                new ServerSocket(config.getSourcePort());
		 while (true) {
	            Socket clientSocket = serverSocket.accept();
	            ClientThread clientThread =
	                    new ClientThread(clientSocket, config);
	            clientThread.start();
	        }
	}

}
