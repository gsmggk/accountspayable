package com.gsmggk.accountspayable.webapp;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Separate startup class for people that want to run the examples directly. Use
 * parameter -Dcom.sun.management.jmxremote to startup JMX (and e.g. connect
 * with jconsole).
 */
public class StartJetty {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StartJetty.class);
    /**
     * Main function, starts the jetty server.
     *
     * @param args
     */
    public static void main(String[] args) {
    	
    
        startInstance(8081);
       //  startInstance(8082);
        // startInstance(8083);
        // startInstance(8084);
    }

    private static void startInstance(int port) {
    	
    	
    	System.setProperty("org.jboss.logging.provider", "slf4j");
    	
    	
    	
        Server server = new Server();
        
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setOutputBufferSize(32768);
       
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
        http.setPort(port);
        http.setIdleTimeout(1000 * 60 * 60);

        server.addConnector(http);

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath("/");
        bb.setWar("src/main/webapp");
       
        server.setHandler(bb);

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
        server.addEventListener(mBeanContainer);
        server.addBean(mBeanContainer);

        try {
        
        	LOGGER.info("Jetty server start");
        
            server.start();
          
        } catch (Exception e) {
        	LOGGER.error("Jetty server error:", e.getMessage());
            e.printStackTrace();
            System.exit(100);
        }
    }
}
