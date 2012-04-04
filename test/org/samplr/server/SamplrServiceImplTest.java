package org.samplr.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.samplr.server.storage.DAO;
import org.samplr.server.storage.Manager;
import org.samplr.shared.model.SampleSourceType;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

public class SamplrServiceImplTest {
  private SamplrServiceImpl samplrServiceImpl;

  private DAO mockDao;
  private Objectify mockObjectify;
  private Manager mockManager;
  private SampleSourceType.MutationManager mockSampleSourceTypeMutationManager;

  @Before
  public void setUp() throws Exception {
    mockDao = mock(DAO.class);
    mockObjectify = mock(Objectify.class);
    mockManager = mock(Manager.class);
    mockSampleSourceTypeMutationManager = mock(SampleSourceType.MutationManager.class);

    samplrServiceImpl = new SamplrServiceImpl(mockDao, mockManager, mockSampleSourceTypeMutationManager);
  }

  @Test
  public void testGetSampleSourceTypes() {
    final Query<SampleSourceType> mockSampleSourceTypeQuery = mock(Query.class);

    when(mockDao.ofy()).thenReturn(mockObjectify);
    when(mockObjectify.query(SampleSourceType.class)).thenReturn(mockSampleSourceTypeQuery);
    when(mockSampleSourceTypeQuery.count()).thenReturn(1);

    final QueryResultIterator<SampleSourceType> mockIterator = mock(QueryResultIterator.class);

    when(mockSampleSourceTypeQuery.iterator()).thenReturn(mockIterator);
    when(mockIterator.hasNext()).thenReturn(true).thenReturn(false);

    final SampleSourceType canonicalSST = new SampleSourceType.MutationManager().create().withTitle("Foo").build();

    when(mockIterator.next()).thenReturn(canonicalSST);

    assertEquals(canonicalSST, samplrServiceImpl.getSampleSourceTypes().get(0));
  }

  @Test
  public void testCreateSampleSourceType() {
    final SampleSourceType canonicalSST = new SampleSourceType.MutationManager().create().withTitle("Foo").build();

    assertEquals(canonicalSST, samplrServiceImpl.createSampleSourceType(canonicalSST));
  }

  @Test
  public void testCreateSampleSourceType_Commits() {
    final SampleSourceType canonicalSST = new SampleSourceType.MutationManager().create().withTitle("Foo").build();
    final SampleSourceType.Mutator mockMutator = mock(SampleSourceType.Mutator.class);

    when(mockSampleSourceTypeMutationManager.from(canonicalSST)).thenReturn(mockMutator);
    when(mockMutator.generate()).thenReturn(canonicalSST);

    final SampleSourceType returned = samplrServiceImpl.createSampleSourceType(canonicalSST);
    assertEquals(canonicalSST, returned);
    assertEquals("die", returned.getKey());
  }
}
