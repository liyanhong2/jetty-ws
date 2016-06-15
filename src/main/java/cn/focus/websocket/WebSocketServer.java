package cn.focus.websocket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: noah
 * Date: 8/8/12
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebSocketServer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;


    private FlashPolicyServer fpServer;

    private int port;

    public static void main(String[] args) {

        WebSocketServer server = new WebSocketServer(8085);
        server.start();

    }

    public WebSocketServer(int port) {
        this.port = port;
    }

    public void start() {

        fpServer = new FlashPolicyServer(10843);
        fpServer.start();
        server = new Server(port);

        MyWebSocketHandler myWebSocketHandler = new MyWebSocketHandler();

        URL url = this.getClass().getClassLoader().getResource("org/noahx/websocket/http");

        ResourceHandler resourceHandler = new ResourceHandler();
        try {
            resourceHandler.setBaseResource(new FileResource(url));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        myWebSocketHandler.setHandler(resourceHandler);

        server.setHandler(myWebSocketHandler);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}