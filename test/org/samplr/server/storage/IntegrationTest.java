/**
 * 
 */
package org.samplr.server.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

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
    if (!((persistenceManager == null) || persistenceManager.isClosed())) {
      persistenceManager.close();
    }

    localServiceTestHelper.tearDown();
  }

  @Test
  public void testSampleSourceType_Create() {
    final SampleSourceType sampleSourceType = new SampleSourceType("Politician", "politician");

    assertNull(sampleSourceType.getKey());

    persistenceManager.makePersistent(sampleSourceType);

    final Key key = sampleSourceType.getKey();
    assertNotNull(key);

    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final SampleSourceType returned = persistenceManager.getObjectById(SampleSourceType.class, key);
    assertNotNull(returned);
    assertEquals(sampleSourceType, returned);
  }

  @Test
  public void testSampleSourceType_Update() {
    final SampleSourceType original = new SampleSourceType("Talking Head", "talking head");

    assertNull(original.getKey());

    persistenceManager.makePersistent(original);

    final Key key = original.getKey();
    assertNotNull(key);

    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final SampleSourceType returned = persistenceManager.getObjectById(SampleSourceType.class, key);
    assertNotNull(returned);
    assertEquals(original, returned);

    returned.setTitle("Apologist");
    returned.setNormalizedTitle("apologist");

    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final SampleSourceType mutated = persistenceManager.getObjectById(SampleSourceType.class, key);
    assertNotNull(mutated);
    assertEquals("Apologist", mutated.getTitle());
    assertEquals("apologist", mutated.getNormalizedTitle());
  }

  @Test
  public void testSampleSourceType_Delete() {
    final SampleSourceType sampleSourceType = new SampleSourceType("Legitimator", "legitimator");

    assertNull(sampleSourceType.getKey());

    persistenceManager.makePersistent(sampleSourceType);

    final Key key = sampleSourceType.getKey();
    assertNotNull(key);

    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final SampleSourceType returned = persistenceManager.getObjectById(SampleSourceType.class, key);
    assertNotNull(returned);
    assertEquals(sampleSourceType, returned);

    persistenceManager.deletePersistent(returned);
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    try {
      persistenceManager.getObjectById(SampleSourceType.class, key);
      fail("This should have raised an error.");
    } catch (final JDOObjectNotFoundException e) {
    }
  }

  @Test
  public void testSampleSourceType_Query_ByKey() {
    final SampleSourceType sampleSourceType = new SampleSourceType("Activist", "activist");

    assertNull(sampleSourceType.getKey());

    persistenceManager.makePersistent(sampleSourceType);

    final Key key = sampleSourceType.getKey();
    assertNotNull(key);

    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final SampleSourceType returned = persistenceManager.getObjectById(SampleSourceType.class, key);
    assertNotNull(returned);
    assertEquals(sampleSourceType, returned);

    final Query byKey = persistenceManager.newQuery(SampleSourceType.class);
    byKey.setFilter("key == k");
    byKey.declareParameters(key.getClass().getName() + " k");

    final List<SampleSourceType> emissions = (List<SampleSourceType>) byKey.execute(key);
    assertEquals(1, emissions.size());

    final SampleSourceType extracted = emissions.get(0);

    assertEquals(sampleSourceType, extracted);
    assertEquals(returned, extracted);
  }

  @Test
  public void testSampleSourceType_Query_ByTitle() {
    final SampleSourceType sampleSourceType = new SampleSourceType("Activist", "activist");

    assertNull(sampleSourceType.getKey());

    persistenceManager.makePersistent(sampleSourceType);

    final Key key = sampleSourceType.getKey();
    assertNotNull(key);

    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final SampleSourceType returned = persistenceManager.getObjectById(SampleSourceType.class, key);
    assertNotNull(returned);
    assertEquals(sampleSourceType, returned);

    final Query byKey = persistenceManager.newQuery(SampleSourceType.class);
    byKey.setFilter("title == t");
    byKey.declareParameters("String t");

    final List<SampleSourceType> emissions = (List<SampleSourceType>) byKey.execute("Activist");
    assertEquals(1, emissions.size());

    final SampleSourceType extracted = emissions.get(0);

    assertEquals(sampleSourceType, extracted);
    assertEquals(returned, extracted);
  }

  @Test
  public void testSampleSourceType_Query_ByNormalizedTitle() {
    final SampleSourceType sampleSourceType = new SampleSourceType("Activist", "activist");

    assertNull(sampleSourceType.getKey());

    persistenceManager.makePersistent(sampleSourceType);

    final Key key = sampleSourceType.getKey();
    assertNotNull(key);

    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final SampleSourceType returned = persistenceManager.getObjectById(SampleSourceType.class, key);
    assertNotNull(returned);
    assertEquals(sampleSourceType, returned);

    final Query byKey = persistenceManager.newQuery(SampleSourceType.class);
    byKey.setFilter("normalizedTitle == t");
    byKey.declareParameters("String t");

    final List<SampleSourceType> emissions = (List<SampleSourceType>) byKey.execute("activist");
    assertEquals(1, emissions.size());

    final SampleSourceType extracted = emissions.get(0);
    assertEquals(sampleSourceType, extracted);
    assertEquals(returned, extracted);
  }

  @Test
  public void testSampleSource_Create_NewSampleSourceType() {
    final SampleSourceType sampleSourceType = new SampleSourceType("Activist", "activist");
    final SampleSource sampleSource = new SampleSource("Noam Chomsky", "noam chomsky", sampleSourceType);

    assertNull(sampleSourceType.getKey());
    assertNull(sampleSource.getKey());

    persistenceManager.makePersistent(sampleSourceType);
    persistenceManager.makePersistent(sampleSource);
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final Key sampleSourceKey = sampleSource.getKey();
    final Key sampleSourceTypeKey = sampleSourceType.getKey();

    assertNotNull(sampleSourceKey);
    assertNotNull(sampleSourceTypeKey);

    final SampleSourceType returnedSampleSourceType = persistenceManager.getObjectById(SampleSourceType.class, sampleSourceTypeKey);
    final SampleSource returnedSampleSource = persistenceManager.getObjectById(SampleSource.class, sampleSourceKey);

    assertEquals(sampleSourceType, returnedSampleSourceType);
    assertEquals(sampleSource, returnedSampleSource);

    assertEquals(sampleSourceType, returnedSampleSource.getSampleSourceType());

    assertEquals(1, returnedSampleSourceType.getSampleSources().size());
    assertEquals(sampleSource, returnedSampleSourceType.getSampleSources().get(0));

    final Query bySampleSourceType = persistenceManager.newQuery(SampleSource.class);
    bySampleSourceType.setFilter("sampleSourceType == sst");
    bySampleSourceType.declareParameters(sampleSourceType.getClass().getName() + " sst");

    final List<SampleSource> emissionsBySampleSourceType = (List<SampleSource>) bySampleSourceType.execute(sampleSourceType);
    assertEquals(1, emissionsBySampleSourceType.size());

    final SampleSource extractedSampleSource = emissionsBySampleSourceType.get(0);
    assertEquals(sampleSource, extractedSampleSource);
    assertEquals(returnedSampleSource, extractedSampleSource);

    // TODO: Back-query SampleSource for SampleSourceType
  }

  @Ignore
  @Test
  public void testSampleSource_Create_ExistingSampleSourceType() {
    final SampleSourceType sampleSourceType = new SampleSourceType("Activist", "activist");
    assertNull(sampleSourceType.getKey());

    persistenceManager.makePersistent(sampleSourceType);
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final Key sampleSourceTypeKey = sampleSourceType.getKey();
    assertNotNull(sampleSourceTypeKey);

    final SampleSource sampleSource = new SampleSource("Noam Chomsky", "noam chomsky", sampleSourceType);
    assertNull(sampleSource.getKey());

    persistenceManager.makePersistent(sampleSource);
    persistenceManager.close();
    persistenceManager = persistenceManagerFactory.getPersistenceManager();

    final Key sampleSourceKey = sampleSource.getKey();
    assertNotNull(sampleSourceKey);


    final SampleSourceType returnedSampleSourceType = persistenceManager.getObjectById(SampleSourceType.class, sampleSourceTypeKey);
    final SampleSource returnedSampleSource = persistenceManager.getObjectById(SampleSource.class, sampleSourceKey);

    assertEquals(sampleSourceType, returnedSampleSourceType);
    assertEquals(sampleSource, returnedSampleSource);

    assertEquals(sampleSourceType, returnedSampleSource.getSampleSourceType());

    assertEquals(1, returnedSampleSourceType.getSampleSources().size());
    assertEquals(sampleSource, returnedSampleSourceType.getSampleSources().get(0));

    final Query bySampleSourceType = persistenceManager.newQuery(SampleSource.class);
    bySampleSourceType.setFilter("sampleSourceType == sst");
    bySampleSourceType.declareParameters(sampleSourceType.getClass().getName() + " sst");

    final List<SampleSource> emissionsBySampleSourceType = (List<SampleSource>) bySampleSourceType.execute(sampleSourceType);
    assertEquals(1, emissionsBySampleSourceType.size());

    final SampleSource extractedSampleSource = emissionsBySampleSourceType.get(0);
    assertEquals(sampleSource, extractedSampleSource);
    assertEquals(returnedSampleSource, extractedSampleSource);

    // TODO: Back-query SampleSource for SampleSourceType
  }
}
