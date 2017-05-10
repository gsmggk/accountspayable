package com.gsmggk.accountspayable.webapp.loadbalancer;

public class PortForwardConfig {
    
    private int sourcePort;
    private int destinationPort;
    private String destinationHost;

    public PortForwardConfig(int sourcePort, int destinationPort, String destinationHost) {
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.destinationHost = destinationHost;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public String getDestinationHost() {
        return destinationHost;
    }
    
    public static PortForwardConfig getConfig(String[] args) {
        int sourcePort = Integer.parseInt(args[0]);
        int destinationPort;
        String destinationHost;
        
        if (args[1].indexOf(':') > -1) {
            String[] parts = args[1].split(":");
            destinationHost = parts[0];
            destinationPort = Integer.parseInt(parts[1]);
        } else {
            destinationHost = args[1];
            destinationPort = sourcePort;
        }
        
        return new PortForwardConfig(sourcePort, destinationPort, destinationHost);
    }
}