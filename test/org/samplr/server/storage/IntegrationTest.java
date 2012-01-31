/**
 * 
 */
package org.samplr.server.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
  private final LocalServiceTestHelper localServiceTestHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
  private Objectify objectify;

  @Before
  public void setUp() {
    localServiceTestHelper.setUp();

    objectify = ObjectifyService.begin();
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
  public void testSampleSourceType_Query_Id() {
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
  public void testSampleSourceType_Query_TitleNormalized() {
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
}