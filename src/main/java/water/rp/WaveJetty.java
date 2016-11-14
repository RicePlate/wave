package water.rp;


import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import water.JettyHTTPD;
import water.api.DatasetServlet;
import water.api.NpsBinServlet;
import water.api.PostFileServlet;
import water.api.RequestServer;


public class WaveJetty extends JettyHTTPD {

  protected void startHttp() throws Exception {
    setServer(new Server());
    Connector ws = new SelectChannelConnector();
    ws.setHost(getIp());
    ws.setPort(getPort());
    createServer(ws);
  }

  @Override public void registerHandlers(HandlerWrapper handlerWrapper) {
    ServletContextHandler context = new ServletContextHandler(3);
    context.setContextPath("/");
    context.addServlet(WaveServlet.class, "/wave");
    context.addServlet(NpsBinServlet.class, "/3/NodePersistentStorage.bin/*");
    context.addServlet(PostFileServlet.class, "/3/PostFile.bin");
    context.addServlet(PostFileServlet.class, "/3/PostFile");
    context.addServlet(DatasetServlet.class, "/3/DownloadDataset");
    context.addServlet(DatasetServlet.class, "/3/DownloadDataset.bin");
    context.addServlet(RequestServer.class, "/");

    HandlerCollection hc = new HandlerCollection();
    hc.setHandlers(new Handler[]{new JettyHTTPD.GateHandler(), new JettyHTTPD.AuthenticationHandler(), new JettyHTTPD.ExtensionHandler1(), context});
    handlerWrapper.setHandler(hc);
  }

}
