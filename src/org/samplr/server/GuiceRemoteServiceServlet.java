package org.samplr.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Injector;

//package org.samplr.server;
//
//import java.util.logging.Logger;
//
//import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
//import com.google.gwt.user.client.rpc.RemoteService;
//import com.google.gwt.user.client.rpc.SerializationException;
//import com.google.gwt.user.server.rpc.RPC;
//import com.google.gwt.user.server.rpc.RPCRequest;
//import com.google.gwt.user.server.rpc.RemoteServiceServlet;
//import com.google.inject.Inject;
//import com.google.inject.Injector;
//
//@SuppressWarnings("serial")
//public class GuiceRemoteServiceServlet extends RemoteServiceServlet {
//  private static final Logger log = Logger.getLogger(GuiceRemoteServiceServlet.class.getCanonicalName());
//
//  @Inject
//  private Injector injector;
//
//  @Override
//  public String processCall(final String payload) throws SerializationException {
//    log.info("--- processCall: " + payload);
//
//    try {
//      final RPCRequest req = RPC.decodeRequest(payload, null, this);
//
//      final RemoteService service = getServiceInstance(
//          req.getMethod().getDeclaringClass());
//
//      return RPC.invokeAndEncodeResponse(service, req.getMethod(),
//          req.getParameters(), req.getSerializationPolicy());
//    } catch (final IncompatibleRemoteServiceException ex) {
//      log("IncompatibleRemoteServiceException in the processCall(String) method.",
//          ex);
//      return RPC.encodeResponseForFailure(null, ex);
//    }
//  }
//
//  @SuppressWarnings({"unchecked"})
//  private RemoteService getServiceInstance(final Class serviceClass) {
//    log.info("--- getServiceInstance " + serviceClass);
//
//    return (RemoteService) injector.getInstance(serviceClass);
//  }
//}

public abstract class GuiceRemoteServiceServlet extends RemoteServiceServlet {
  @Override
  public void init(final ServletConfig config) throws ServletException {
    super.init(config);
    final Injector injector = (Injector)config.getServletContext().
        getAttribute(Injector.class.getName());
    injector.injectMembers(this);
  }
}
