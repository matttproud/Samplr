package org.samplr.server.inject;

import org.mortbay.jetty.servlet.ServletMapping;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
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
