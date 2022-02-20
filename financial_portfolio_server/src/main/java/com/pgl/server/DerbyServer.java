package com.pgl.server;

import org.apache.commons.lang3.StringUtils;
import org.apache.derby.drda.NetworkServerControl;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class DerbyServer extends PropertyServer implements AppServer {

    private String _dbHome;
    private String _host;
    private int _port;

    @Override
    public void create() {
        try {
            DerbyServer.address = InetAddress.getByName(this._host);
            DerbyServer.port = this._port;

            DerbyServer.server = new NetworkServerControl(address, port);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void configure() {
        this._dbHome = System.getProperty("pgl.db.server.home");
        this._host = System.getProperty("pgl.db.server.host");
        this._port = Integer.parseInt(System.getProperty("pgl.db.server.port"));

        System.setProperty("derby.system.home", this._dbHome);

        if (StringUtils.isBlank(this._host)) {
            this._host = "127.0.0.1";
        }
    }

    @Override
    public void start() {
        try {
            DerbyServer.server.start(new PrintWriter(System.out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restart() {
        if (server == null){
            try {
                DerbyServer.server = new NetworkServerControl(address, port);
                DerbyServer.server.start(new PrintWriter(System.out));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        try {
            DerbyServer.server.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
