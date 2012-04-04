package org.samplr.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.samplr.client.SamplrService;
import org.samplr.server.storage.DAO;
import org.samplr.server.storage.Manager;
import org.samplr.shared.model.SampleSourceType;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@Singleton
public class SamplrServiceImpl extends GuiceRemoteServiceServlet implements SamplrService {
  private static final Logger log = Logger.getLogger(SamplrServiceImpl.class.getName());

  @Inject
  private DAO dao;

  @Inject
  Manager persistenceManager;

  @Inject
  SampleSourceType.MutationManager sampleSourceTypeMutationManager;

  SamplrServiceImpl(final DAO dao, final Manager persistenceManager,
      final SampleSourceType.MutationManager sampleSourceTypeMutationManager) {
    this.dao = dao;
    this.persistenceManager = persistenceManager;
    this.sampleSourceTypeMutationManager = sampleSourceTypeMutationManager;
  }

  @Override
  public List<SampleSourceType> getSampleSourceTypes() {
    final Query<SampleSourceType> query = dao.ofy().query(SampleSourceType.class);
    final List<SampleSourceType> emission = new ArrayList<SampleSourceType>(query.count());

    for (final SampleSourceType entry : query) {
      emission.add(entry);
    }

    return emission;
  }

  @Override
  public SampleSourceType createSampleSourceType(final SampleSourceType future) {
    final SampleSourceType proposed = sampleSourceTypeMutationManager.from(future).generate();

    final Key<SampleSourceType> key = persistenceManager.sampleSourceType().commit(proposed);
    return persistenceManager.sampleSourceType().getByKey(key.getName());
  }
}
