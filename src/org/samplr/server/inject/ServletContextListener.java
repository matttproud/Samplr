/**
 * 
 */
package org.samplr.server.inject;

import java.util.logging.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author mtp
 *
 */
public class ServletContextListener extends GuiceServletContextListener {
  private static final Logger log = Logger.getLogger(ServletContextListener.class.getCanonicalName());

  @Override
  protected Injector getInjector() {
    log.info("--- Provisioning injector...");

    return Guice.createInjector(new ServerModule());
  }
}
