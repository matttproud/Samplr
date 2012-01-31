/**
 * 
 */
package org.samplr.server.inject;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.samplr.server.storage.DAO;
import org.samplr.server.storage.ModelRegistry;

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
  public void testProvision_DatastoreService() {
    assertNotNull(injector.getInstance(DatastoreService.class));
  }

  @Test
  public void testProvision_ModelRegistry() {
    assertNotNull(injector.getInstance(ModelRegistry.class));
  }

  @Test
  public void testProvision_DAO() {
    final DAO dao = injector.getInstance(DAO.class);

    assertNotNull(dao);
  }
}
