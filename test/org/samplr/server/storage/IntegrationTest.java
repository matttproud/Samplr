/**
 * 
 */
package org.samplr.server.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.samplr.shared.model.SampleSource;
import org.samplr.shared.model.SampleSourceType;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

/**
 * @author mtp
 * 
 */
public class IntegrationTest {
  private final LocalServiceTestHelper localServiceTestHelper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig());
  private Objectify objectify;

  private DAO dao;
  private Manager manager;
  private SampleSourceType.MutationManager sstMutationManager;
  private SampleSource.MutationManager ssMutationManager;

  @Before
  public void setUp() {
    localServiceTestHelper.setUp();
    objectify = ObjectifyService.begin();
    dao = new DAO();
    manager = new Manager(dao);
    sstMutationManager = new SampleSourceType.MutationManager();
    ssMutationManager = new SampleSource.MutationManager();
  }

  @After
  public void tearDown() {
    localServiceTestHelper.tearDown();
  }

  @Test
  public void testSampleSourceType_Create() {
    final SampleSourceType pending = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);
  }

  @Test
  public void testSampleSourceType_Update() {
    final SampleSourceType pending = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    final SampleSourceType pendingMutation = sstMutationManager.from(retrieved).withTitle("Liar")
        .generate();

    objectify.put(pendingMutation);

    final SampleSourceType mutated = objectify.get(key);

    assertEquals(pendingMutation, mutated);
  }

  @Test(expected = NotFoundException.class)
  public void testSampleSourceType_Delete() {
    final SampleSourceType pending = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    objectify.delete(key);

    objectify.get(key);
  }

  @Test
  public void testSampleSourceType_Query_Key() {
    final SampleSourceType pending = sstMutationManager.create().withTitle("Politician").build();
    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    final Query<SampleSourceType> query = objectify.query(SampleSourceType.class).filter("key = ",
        retrieved.getNormalizedTitle());

    assertEquals(1, query.count());
    assertEquals(retrieved, query.get());
  }

  @Test
  public void testSampleSourceType_Query_Title() {
    final SampleSourceType pending = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    final Query<SampleSourceType> query = objectify.query(SampleSourceType.class).filter(
        "title = ", "Politician");

    assertEquals(1, query.count());
    assertEquals(retrieved, query.get());
  }

  @Test
  public void testSampleSourceType_Query_NormalizedTitle() {
    final SampleSourceType pending = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    final Query<SampleSourceType> query = objectify.query(SampleSourceType.class).filter(
        "normalizedTitle = ", "politician");

    assertEquals(1, query.count());
    assertEquals(retrieved, query.get());
  }

  @Test
  public void testSampleSource_Create() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);
  }

  @Test
  public void testSampleSource_Update() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final SampleSource pendingSSMutation = ssMutationManager.from(retrievedSS).withTitle("Woodrow Wilson")
        .generate();

    objectify.put(pendingSSMutation);

    final SampleSource mutated = objectify.get(ssKey);

    assertEquals(pendingSSMutation, mutated);
  }

  @Test(expected = NotFoundException.class)
  public void testSampleSource_Delete() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    objectify.delete(ssKey);

    objectify.get(ssKey);
  }

  @Test
  public void testSampleSource_Query_Key() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter("key =",
        retrievedSS.getKey());

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  @Test
  public void testSampleSource_Query_Title() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter("title =",
        "George W. Bush");

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  @Test
  public void testSampleSource_Query_NormalizedTitle() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter(
        "normalizedTitle =", "george w. bush");

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  @Test
  public void testSampleSource_Query_SampleSourceTypeKey() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter(
        "sampleSourceTypeKey =", retrievedSST);

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  // TODO: Mock out D.S. for further tests and let direct large tests suffice.

  @Test
  public void testSampleSourceType_StorageManager_getByKey() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);
    assertEquals(retrievedSST, manager.sampleSourceType().getByKey(retrievedSST.getKey()));
  }

  @Test
  public void testSampleSourceTypeFactory_queryByTitle() throws NotFoundException {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    assertEquals(1, manager.sampleSourceType().queryByTitle(pendingSST.getTitle()).size());
    assertEquals(pendingSST, manager.sampleSourceType().queryByTitle(pendingSST.getTitle()).get(0));
  }

  @Test
  public void testSampleSourceTypeFactory_from_Mutated() throws NotFoundException {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    final SampleSourceType mutated = sstMutationManager.from(retrievedSST).withTitle("Liar").generate();

    assertEquals("Liar", mutated.getTitle());
    assertEquals("liar", mutated.getNormalizedTitle());
  }

  @Test
  public void testSampleSourceTypeFactory_from_Unmutated() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST).build();

    final SampleSourceType mutated = sstMutationManager.from(retrievedSST).generate();

    assertEquals("Politician", mutated.getTitle());
    assertEquals("politician", mutated.getNormalizedTitle());
  }

  @Test
  public void testSampleSourceTypeFactory_commit() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = manager.sampleSourceType().commit(pendingSST);

    assertNotNull(sstKey);

    assertEquals(pendingSST, objectify.get(sstKey));
  }

  @Test(expected = NotFoundException.class)
  public void testSampleSourceTypeFactory_delete() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    manager.sampleSourceType().delete(retrievedSST);

    assertNull(objectify.get(sstKey));
  }

  @Test
  public void testSampleSourceFactory_getByKey() {
    final SampleSourceType retrievedSST = createDummySampleSourceType();

    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST)
        .build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = manager.sampleSource().getByKey("george w. bush");

    assertEquals(pendingSS, retrievedSS);
  }

  @Test
  public void testSampleSourceFactory_queryByTitle() {
    final SampleSourceType retrievedSST = createDummySampleSourceType();
    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST)
        .build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final List<SampleSource> retrievedSS = manager.sampleSource().queryByTitle("George W. Bush");

    assertEquals(1, retrievedSS.size());
    assertEquals(pendingSS, retrievedSS.get(0));
  }

  @Test
  public void testSampleSourceFactory_from_Mutated() {
    final SampleSourceType retrievedSST = createDummySampleSourceType();
    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST)
        .build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Apologist").build();
    final Key<SampleSourceType> alternativeSSTKey = objectify.put(pendingSST);
    final SampleSourceType alternativeSST = objectify.get(alternativeSSTKey);

    final SampleSource mutatedSS = ssMutationManager.from(pendingSS).withTitle("George Walker Bush")
        .withSampleSourceType(alternativeSST)
        .generate();

    manager.sampleSource().commit(mutatedSS);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(mutatedSS, retrievedSS);
  }

  @Test
  public void testSampleSourceFactory_from_Unmutated() {
    final SampleSourceType retrievedSST = createDummySampleSourceType();
    final SampleSource pendingSS = ssMutationManager.create().withTitle("George W. Bush")
        .withSampleSourceType(retrievedSST)
        .build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource unmutatedSS = ssMutationManager.from(pendingSS).generate();

    manager.sampleSource().commit(unmutatedSS);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(unmutatedSS, retrievedSS);
  }


  @Test
  public void testSampleSourceFactory_commit() {
    final SampleSourceType retrievedSST = createDummySampleSourceType();
    final SampleSource pendingSS = ssMutationManager.create().withSampleSourceType(retrievedSST).withTitle("Foo").build();

    final Key<SampleSource> ssKey = manager.sampleSource().commit(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);
  }

  @Test(expected = NotFoundException.class)
  public void testSampleSourceFactory_delete() {
    final SampleSourceType retrievedSST = createDummySampleSourceType();
    final SampleSource pendingSS = ssMutationManager.create().withSampleSourceType(retrievedSST).withTitle("Foo").build();

    final Key<SampleSource> ssKey = manager.sampleSource().commit(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    manager.sampleSource().delete(retrievedSS);

    objectify.get(ssKey);
  }

  private SampleSourceType createDummySampleSourceType() {
    final SampleSourceType pendingSST = sstMutationManager.create().withTitle("Politician").build();
    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);
    final SampleSourceType retrievedSST = objectify.get(sstKey);
    return retrievedSST;
  }
}
