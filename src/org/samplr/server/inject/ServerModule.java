package org.samplr.server.inject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class ServerModule extends ServletModule {
  @Provides
  @Singleton
  DatastoreService getDatastoreService() {
    return DatastoreServiceFactory.getDatastoreService();
  }
}
