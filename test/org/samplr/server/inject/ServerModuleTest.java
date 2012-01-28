/**
 * 
 */
package org.samplr.server.inject;

import static org.junit.Assert.assertNotNull;

import javax.jdo.PersistenceManagerFactory;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author mtp
 *
 */
public class ServerModuleTest {
  private Injector injector;

  @Before
  public void setUp() throws Exception {
    injector = Guice.createInjector(new ServerModule());
  }

  @Test
  public void testProvision_Injector() {
    assertNotNull(injector);
  }

  @Test
  public void testGetDatastoreService() {
    assertNotNull(injector.getInstance(DatastoreService.class));
  }

  @Test
  public void testGetPersistenceManagerFactory() {
    final PersistenceManagerFactory persistenceManagerFactory = injector.getInstance(PersistenceManagerFactory.class);

    try {
      assertNotNull(persistenceManagerFactory);
    } finally {
      if (persistenceManagerFactory != null) {
        persistenceManagerFactory.close();
      }
    }
  }

}
