/**
 * 
 */
package org.samplr.server.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.samplr.server.model.SampleSource;
import org.samplr.server.model.SampleSourceType;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * @author mtp
 *
 */
public class IntegrationTest {
  private static PersistenceManagerFactory persistenceManagerFactory;

  private final LocalServiceTestHelper localServiceTestHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  private PersistenceManager persistenceManager;

  @BeforeClass
  public static void setUp_Class() {
    persistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");
  }

  @AfterClass
  public static void tearDown_Class() {
    persistenceManagerFactory.close();
  }

  @Before
  public void setUp() {
    localServiceTestHelper.setUp();

    persistenceManager = persistenceManagerFactory.getPersistenceManager();
  }

  @After
  public void tearDown() {
    localServiceTestHelper.tearDown();

    persistenceManager.flush();
    persistenceManager.evictAll();
    persistenceManager.close();
  }

  @Test
  public void testSampleSourceType_CRUD() {
    final SampleSourceType sampleSourceType = new SampleSourceType("Title", "title");

    assertNull(sampleSourceType.getKey());

    persistenceManager.makePersistent(sampleSourceType);
    persistenceManager.flush();
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final Key key = sampleSourceType.getKey();

    assertNotNull(key);
    assertEquals(sampleSourceType, persistenceManager.getObjectById(SampleSourceType.class, key));

    sampleSourceType.setTitle("Foo!");
    sampleSourceType.setNormalizedTitle("foo");
    persistenceManager.makePersistent(sampleSourceType);
    persistenceManager.flush();
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final SampleSourceType emitted = persistenceManager.getObjectById(SampleSourceType.class, key);

    assertEquals("Foo!", emitted.getTitle());
    assertEquals("foo", emitted.getNormalizedTitle());

    persistenceManager.deletePersistent(emitted);
    persistenceManager.flush();
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    try {
      assertNull(persistenceManager.getObjectById(SampleSourceType.class, key));
      fail("This should have failed.");
    } catch (final JDOObjectNotFoundException e) {
    }
  }

  @Test
  public void testSampleSource_CRUD() {
    final SampleSourceType sampleSourceType = new SampleSourceType("Politician", "politician");
    SampleSource sampleSource = new SampleSource("George Bush", "george bush", sampleSourceType);

    assertNull(sampleSource.getKey());

    persistenceManager.makePersistent(sampleSourceType);
    persistenceManager.makePersistent(sampleSource);
    persistenceManager.flush();
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final Key key = sampleSource.getKey();

    assertNotNull(key);
    assertEquals(sampleSource, persistenceManager.getObjectById(SampleSource.class, key));

    sampleSource = persistenceManager.getObjectById(SampleSource.class, key);

    sampleSource.setTitle("Ronald Reagan");
    sampleSource.setNormalizedTitle("ronald reagan");
    //persistenceManager.makePersistent(sampleSource);
    //persistenceManager.flush();
    //persistenceManager.evictAll();
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final SampleSource emitted = persistenceManager.getObjectById(SampleSource.class, key);

    assertEquals("Ronald Reagan", emitted.getTitle());
    assertEquals("ronald reagan", emitted.getNormalizedTitle());

    persistenceManager.deletePersistent(emitted);
    persistenceManager.flush();
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    try {
      assertNull(persistenceManager.getObjectById(SampleSourceType.class, key));
      fail("This should have failed.");
    } catch (final JDOObjectNotFoundException e) {
    }
  }
}
