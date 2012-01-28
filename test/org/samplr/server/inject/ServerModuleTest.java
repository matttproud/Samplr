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

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    injector = Guice.createInjector(new ServerModule());
  }

  @Test
  public void testProvision_Injector() {
    assertNotNull(injector);
  }

  /**
   * Test method for {@link org.samplr.server.inject.ServerModule#getDatastoreService()}.
   */
  @Test
  public void testGetDatastoreService() {
    assertNotNull(injector.getInstance(DatastoreService.class));
  }

  /**
   * Test method for {@link org.samplr.server.inject.ServerModule#getPersistenceManagerFactory()}.
   */
  @Test
  public void testGetPersistenceManagerFactory() {
    assertNotNull(injector.getInstance(PersistenceManagerFactory.class));
  }

}
