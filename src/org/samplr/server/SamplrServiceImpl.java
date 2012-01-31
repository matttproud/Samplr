package org.samplr.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.samplr.client.SamplrService;
import org.samplr.shared.model.SampleSourceType;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@Singleton
public class SamplrServiceImpl extends GuiceRemoteServiceServlet implements SamplrService {
  @Inject
  private PersistenceManagerFactory persistenceManagerFactory;

  @SuppressWarnings("unchecked")
  @Override
  public List<SampleSourceType> getSampleSourceTypes() {
    final PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();
    try {
      final Query query = persistenceManager.newQuery(SampleSourceType.class);

      return new ArrayList<SampleSourceType>((List<SampleSourceType>)query.execute());
    } finally {
      persistenceManager.close();
    }
  }

  @Override
  public boolean createSampleSourceType() {
    final PersistenceManager persistenceManager = persistenceManagerFactory.getPersistenceManager();

    try {
      final SampleSourceType a = new SampleSourceType("Politician", "politician");
      final SampleSourceType b = new SampleSourceType("Crook", "crook");

      persistenceManager.makePersistentAll(a, b);

      return true;
    } finally {
      persistenceManager.close();
    }
  }
}
