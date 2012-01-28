package org.samplr.server.inject;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

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

  @Provides
  @Singleton
  PersistenceManagerFactory getPersistenceManagerFactory() {
    return JDOHelper.getPersistenceManagerFactory("transactions-optional");
  }
}
