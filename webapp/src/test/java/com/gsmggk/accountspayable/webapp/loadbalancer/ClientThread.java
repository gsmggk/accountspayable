package com.gsmggk.accountspayable.webapp.loadbalancer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * ed.port.forward.ClientThread is responsible for starting forwarding between
 * the client and the server. It keeps track of the client and
 * servers sockets that are both closed on input/output error
 * durinf the forwarding. The forwarding is bidirectional and
 * is performed by two ed.port.forward.ForwardThread instances.
 */
class ClientThread extends Thread {
    private Socket clientSocket;
    private Socket serverSocket;
    private boolean forwardingActive = false;
    private PortForwardConfig config;

    public ClientThread(Socket clientSocket, PortForwardConfig config) {
        this.clientSocket = clientSocket;
        this.config = config;
    }

    /**
     * Establishes connection to the destination server and
     * starts bidirectional forwarding ot data between the
     * client and the server.
     */
    public void run() {
        InputStream clientIn;
        OutputStream clientOut;
        InputStream serverIn;
        OutputStream serverOut;
        try {
            // Connect to the destination server 
            serverSocket = new Socket(
                    config.getDestinationHost(),
                    config.getDestinationPort());

            // Turn on keep-alive for both the sockets 
            serverSocket.setKeepAlive(true);
            clientSocket.setKeepAlive(true);

            // Obtain client & server input & output streams 
            clientIn = clientSocket.getInputStream();
            clientOut = clientSocket.getOutputStream();
            serverIn = serverSocket.getInputStream();
            serverOut = serverSocket.getOutputStream();
        } catch (IOException ioe) {
            System.err.println("Can not connect to " +
                    config.getDestinationHost() + ":" +
                    config.getDestinationPort());
            connectionBroken();
            return;
        }

        // Start forwarding data between server and client 
        forwardingActive = true;
        ForwardThread clientForward =
                new ForwardThread(this, clientIn, serverOut);
        clientForward.start();
        ForwardThread serverForward =
                new ForwardThread(this, serverIn, clientOut);
        serverForward.start();

        System.out.println("TCP Forwarding " +
                clientSocket.getInetAddress().getHostAddress() +
                ":" + clientSocket.getPort() + " <--> " +
                serverSocket.getInetAddress().getHostAddress() +
                ":" + serverSocket.getPort() + " started.");
    }

    /**
     * Called by some of the forwarding threads to indicate
     * that its socket connection is brokean and both client
     * and server sockets should be closed. Closing the client
     * and server sockets causes all threads blocked on reading
     * or writing to these sockets to get an exception and to
     * finish their execution.
     */
    public synchronized void connectionBroken() {
        try {
            serverSocket.close();
        } catch (Exception e) {
        }
        try {
            clientSocket.close();
        } catch (Exception e) {
        }

        if (forwardingActive) {
            System.out.println("TCP Forwarding " +
                    clientSocket.getInetAddress().getHostAddress()
                    + ":" + clientSocket.getPort() + " <--> " +
                    serverSocket.getInetAddress().getHostAddress()
                    + ":" + serverSocket.getPort() + " stopped.");
            forwardingActive = false;
        }
    }
}