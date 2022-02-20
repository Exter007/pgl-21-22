package com.pgl.server;

public interface AppServer {

    void create();
    void configure();
    void start();
    void restart();
    void stop();

    default void lauch() {
        this.configure();
        this.create();
        this.start();
    }
}
