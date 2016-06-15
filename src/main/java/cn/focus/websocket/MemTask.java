package cn.focus.websocket;

import java.util.Random;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: noah
 * Date: 8/8/12
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class MemTask extends TimerTask {

    private MyWebSocketHandler.MyWebSocket myWebSocket;

    public MemTask(MyWebSocketHandler.MyWebSocket myWebSocket) {
        this.myWebSocket = myWebSocket;
    }

    @Override
    public void run() {
        myWebSocket.send("" + new Random().nextInt(100));

    }

}