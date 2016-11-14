package water.rp;

import jline.internal.Log;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class WaveServlet extends WebSocketServlet {
  public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
    return new WaveSocket();
  }

  private class WaveSocket implements WebSocket.OnTextMessage {
    Connection _conn;
    @Override public void onOpen(Connection connection) {
      _conn = connection;
      Log.info("websocket open");
    }

    @Override public void onClose(int closeCode, String message) {
      _conn=null;
      Log.info("websocket close");
    }

    @Override public void onMessage(String data) {
      assert _conn!=null : "websocket not open";
      try {
        _conn.sendMessage(data);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}