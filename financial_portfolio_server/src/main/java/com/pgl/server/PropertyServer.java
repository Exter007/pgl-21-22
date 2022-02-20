package com.pgl.server;

import org.apache.derby.drda.NetworkServerControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class PropertyServer {

    private static Logger logger = LoggerFactory.getLogger(DerbyServer.class.getName());

    public static NetworkServerControl server;

    public static int port;

    public static InetAddress address;

    public static InetAddress getIpAddress() {
        try {

            logger.debug(InetAddress.getLocalHost().toString());
            logger.debug("Host Address: " + InetAddress.getLocalHost().getHostAddress());
            logger.debug("Canonical Host Name : " + InetAddress.getLocalHost().getCanonicalHostName());
            logger.debug("Address: " + InetAddress.getLocalHost().getAddress());
            logger.debug("localhost : " + InetAddress.getByName("localhost"));

            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getServerHost(HttpServletRequest request, String url) {
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String scheme = request.getScheme();
        return  scheme + "://" + serverName + ":" + port + "/" + url;
    }
}
