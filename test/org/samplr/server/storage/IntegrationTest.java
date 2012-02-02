/**
 * 
 */
package org.samplr.server.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
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

  @Before
  public void setUp() {
    localServiceTestHelper.setUp();

    objectify = ObjectifyService.begin();
    normalization = new Normalization();
    dao = new DAO();
  }

  @After
  public void tearDown() {
    localServiceTestHelper.tearDown();
  }

  @Test
  public void testSampleSourceType_Create() {
    final SampleSourceType pending = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);
  }

  @Test
  public void testSampleSourceType_Update() {
    final SampleSourceType pending = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    retrieved.setTitle("Liar");
    retrieved.setNormalizedTitle("liar");

    objectify.put(retrieved);

    final SampleSourceType mutated = objectify.get(key);

    assertEquals(retrieved, mutated);
  }

  @Test(expected = NotFoundException.class)
  public void testSampleSourceType_Delete() {
    final SampleSourceType pending = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    objectify.delete(key);

    objectify.get(key);
  }

  @Test
  public void testSampleSourceType_Query_Key() {
    final SampleSourceType pending = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> key = objectify.put(pending);

    assertNotNull(key);

    final SampleSourceType retrieved = objectify.get(key);

    assertEquals(pending, retrieved);

    final Query<SampleSourceType> query = objectify.query(SampleSourceType.class).filter("key = ", key.getId());

    assertEquals(1, query.count());
    assertEquals(retrieved, query.get());
  }


  @Test
  public void testSampleSourceType_Query_Title() {
    final SampleSourceType pending = new SampleSourceType("Politician", "politician");

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
    final SampleSourceType pending = new SampleSourceType("Politician", "politician");

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
    final SampleSourceType pendingSST = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = new SampleSource("George W. Bush", "george w bush", retrievedSST);

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);
  }

  @Test
  public void testSampleSource_Update() {
    final SampleSourceType pendingSST = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = new SampleSource("George W. Bush", "george w bush", retrievedSST);

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    retrievedSS.setTitle("Woodrow Wilson");
    retrievedSS.setNormalizedTitle("woodrow wilson");

    objectify.put(retrievedSS);

    final SampleSource mutated = objectify.get(ssKey);

    assertEquals(retrievedSS, mutated);
  }

  @Test(expected = NotFoundException.class)
  public void testSampleSource_Delete() {
    final SampleSourceType pendingSST = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = new SampleSource("George W. Bush", "george w bush", retrievedSST);

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    objectify.delete(ssKey);

    objectify.get(ssKey);
  }

  @Test
  public void testSampleSource_Query_Key() {
    final SampleSourceType pendingSST = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = new SampleSource("George W. Bush", "george w bush", retrievedSST);

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter("key =", ssKey.getId());

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  @Test
  public void testSampleSource_Query_Title() {
    final SampleSourceType pendingSST = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = new SampleSource("George W. Bush", "george w bush", retrievedSST);

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
    final SampleSourceType pendingSST = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = new SampleSource("George W. Bush", "george w bush", retrievedSST);

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter("normalizedTitle =", "george w bush");

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  @Test
  public void testSampleSource_Query_SampleSourceTypeKey() {
    final SampleSourceType pendingSST = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSource pendingSS = new SampleSource("George W. Bush", "george w bush", retrievedSST);

    final Key<SampleSource> ssKey = objectify.put(pendingSS);

    assertNotNull(ssKey);

    final SampleSource retrievedSS = objectify.get(ssKey);

    assertEquals(pendingSS, retrievedSS);

    final Query<SampleSource> query = objectify.query(SampleSource.class).filter("sampleSourceTypeKey =", sstKey.getId());

    assertEquals(1, query.count());
    assertEquals(retrievedSS, query.get());
  }

  @Test
  public void testSampleSourceTypeFactory_queryByTitle() throws NotFoundException {
    final SampleSourceType pendingSST = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSourceType.Factory factory = new SampleSourceType.Factory(dao, normalization);

    assertEquals(1, factory.queryByTitle(pendingSST.getTitle()).size());
    assertEquals(pendingSST, factory.queryByTitle(pendingSST.getTitle()).get(0));
  }

  @Test
  public void testSampleSourceTypeFactory_from() throws NotFoundException {
    final SampleSourceType pendingSST = new SampleSourceType("Politician", "politician");

    final Key<SampleSourceType> sstKey = objectify.put(pendingSST);

    assertNotNull(sstKey);

    final SampleSourceType retrievedSST = objectify.get(sstKey);

    assertEquals(pendingSST, retrievedSST);

    final SampleSourceType.Factory factory = new SampleSourceType.Factory(dao, normalization);

    final SampleSourceType mutated = factory.from(retrievedSST).withTitle("Liar").build();

    assertEquals("Liar", mutated.getTitle());
    assertEquals("liar", mutated.getNormalizedTitle());
  }
}