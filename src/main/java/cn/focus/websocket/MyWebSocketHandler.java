package cn.focus.websocket;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: noah
 * Date: 8/8/12
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyWebSocketHandler extends WebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {

        if (logger.isDebugEnabled()) {
            logger.debug("url=" + request.getRequestURL() + ",protocol=" + protocol);
        }

        return new MyWebSocket();
    }

    public class MyWebSocket implements WebSocket.OnTextMessage {

        private Logger logger = LoggerFactory.getLogger(this.getClass());

        private Connection connection;

        private Timer timer = new Timer();


        @Override
        public void onMessage(String data) {
            if (logger.isDebugEnabled()) {
                logger.debug("onMessage");
            }
        }

        @Override
        public void onOpen(Connection connection) {
            if (logger.isDebugEnabled()) {
                logger.debug("onOpen");
            }
            this.connection = connection;

            timer.schedule(new MemTask(this), 0, 500);
        }

        @Override
        public void onClose(int closeCode, String message) {
            if (logger.isDebugEnabled()) {
                logger.debug("onClose");
            }
            timer.cancel();
        }

        public void send(String msg) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("send:" + msg);
                }
                connection.sendMessage(msg);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                timer.cancel();
            }
        }

    }
}
