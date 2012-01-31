package org.samplr.server.inject;

import java.util.logging.Logger;

import org.samplr.server.storage.DAO;
import org.samplr.server.storage.ModelRegistry;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class ServerModule extends ServletModule {
  private static final Logger log = Logger.getLogger(ServerModule.class.getName());

  @Provides
  @Singleton
  DatastoreService getDatastoreService() {
    log.info("Provisioning DatastoreService...");

    return DatastoreServiceFactory.getDatastoreService();
  }

  @Provides
  @Singleton
  @Inject
  DAO getDAO(final ModelRegistry modelRegistry) {
    log.info("Provisioning DAO...");

    modelRegistry.register();

    return new DAO();
  }
}
