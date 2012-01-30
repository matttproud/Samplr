package org.samplr.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.samplr.client.SamplrService;
import org.samplr.shared.model.SampleSourceType;

import com.google.common.collect.Lists;
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

      return Lists.newArrayList((List<SampleSourceType>) query.execute());
    } finally {
      persistenceManager.close();
    }
  }
}
