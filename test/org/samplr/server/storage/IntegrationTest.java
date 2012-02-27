/**
 * 
 */
package org.samplr.server.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.samplr.server.utility.Normalization;
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
  private final LocalServiceTestHelper localServiceTestHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
  private Objectify objectify;
  private Normalization normalization;
  private DAO dao;
  private SampleSourceType.StorageManager sstFactory;
  private SampleSource.StorageManager ssFactory;

  @Before
  public void setUp() {
    localServiceTestHelper.setUp();

    objectify = ObjectifyService.begin();
    normalization = new Normalization();
    dao = new DAO();
    sstFactory = new SampleSourceType.StorageManager(dao, normalization);
    ssFactory = new SampleSource.StorageManager(dao, normalization);
  }

  @After
  public void tearDown() {
    localServiceTestHelper.tearDown();
  }

  @Test
  public void testSampleSourceType_Create() {
    final SampleSourceType pending = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);
  }

  @Test
  public void testSampleSourceType_Update() {
    final SampleSourceType pending = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    final SampleSourceType pendingMutation = sstFactory.from(retrieved).withTitle("Liar").generate();

    objectify.put(pendingMutation);

    final SampleSourceType mutated = objectify.get(key);

    assertEquals(pendingMutation, mutated);
  }

  @Test(expected = NotFoundException.class)
  public void testSampleSourceType_Delete() {
    final SampleSourceType pending = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    objectify.delete(key);

    objectify.get(key);
  }

  @Test
  public void testSampleSourceType_Query_Key() {
    final SampleSourceType pending = sstFactory.create().withTitle("Politician").build();
    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    final Query<SampleSourceType> query = objectify.query(SampleSourceType.class).filter("key = ", retrieved.getNormalizedTitle());

    assertEquals(1, query.count());
    assertEquals(retrieved, query.get());
  }


  @Test
  public void testSampleSourceType_Query_Title() {
    final SampleSourceType pending = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    final Query<SampleSourceType> query = objectify.query(SampleSourceType.class).filter("title = ", "Politician");

    assertEquals(1, query.count());
    assertEquals(retrieved, query.get());
  }

  @Test
  public void testSampleSourceType_Query_NormalizedTitle() {
    final SampleSourceType pending = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    final Query<SampleSourceType> query = objectify.query(SampleSourceType.class).filter("normalizedTitle = ", "politician");

    assertEquals(1, query.count());
    assertEquals(retrieved, query.get());
  }

  @Test
  public void testSampleSource_Create() {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssFactory.create().withTitle("George W. Bush").withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);
  }

  @Test
  public void testSampleSource_Update() {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssFactory.create().withTitle("George W. Bush").withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final SampleSource pendingSSMutation = ssFactory.from(retrievedSS).withTitle("Woodrow Wilson").generate();

    objectify.put(pendingSSMutation);

    final SampleSource mutated = objectify.get(ssKey);

    assertEquals(pendingSSMutation, mutated);
  }

  @Test(expected = NotFoundException.class)
  public void testSampleSource_Delete() {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssFactory.create().withTitle("George W. Bush").withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    objectify.delete(ssKey);

    objectify.get(ssKey);
  }

  @Test
  public void testSampleSource_Query_Key() {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssFactory.create().withTitle("George W. Bush").withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter("key =", retrievedSS.getKey());

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  @Test
  public void testSampleSource_Query_Title() {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssFactory.create().withTitle("George W. Bush").withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter("title =", "George W. Bush");

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  @Test
  public void testSampleSource_Query_NormalizedTitle() {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssFactory.create().withTitle("George W. Bush").withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter("normalizedTitle =", "george w. bush");

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  @Test
  public void testSampleSource_Query_SampleSourceTypeKey() {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssFactory.create().withTitle("George W. Bush").withSampleSourceType(retrievedSST).build();

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter("sampleSourceTypeKey =", retrievedSST);

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  // TODO: Mock out D.S. for further tests and let direct large tests suffice.

  @Test
  public void testSampleSourceType_StorageManager_getByKey() {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);
    assertEquals(retrievedSST, sstFactory.getByKey(retrievedSST.getKey()));
  }

  @Test
  public void testSampleSourceTypeFactory_queryByTitle() throws NotFoundException {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssFactory.create().withTitle("George W. Bush").withSampleSourceType(retrievedSST).build();

    assertEquals(1, sstFactory.queryByTitle(pendingSST.getTitle()).size());
    assertEquals(pendingSST, sstFactory.queryByTitle(pendingSST.getTitle()).get(0));
  }

  @Test
  public void testSampleSourceTypeFactory_from() throws NotFoundException {
    final SampleSourceType pendingSST = sstFactory.create().withTitle("Politician").build();

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = ssFactory.create().withTitle("George W. Bush").withSampleSourceType(retrievedSST).build();

    final SampleSourceType mutated = sstFactory.from(retrievedSST).withTitle("Liar").generate();

    assertEquals("Liar", mutated.getTitle());
    assertEquals("liar", mutated.getNormalizedTitle());
  }
  
  @Test
  @Ignore
  public void testSampleSourceTypeFactory_commit() {
    fail("Not implemented.");
  }
  
  @Test
  @Ignore
  public void testSampleSourceTypeFactory_delete() {
    fail("Not implemented.");
  }
}