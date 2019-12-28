package cn.shuyiio.wire;

import cn.shuyiio.wire.server.Server;

import java.util.concurrent.ExecutionException;

public class Application {

    private Server server;



    public void start() {

        server = new Server();

        try {
            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



}
