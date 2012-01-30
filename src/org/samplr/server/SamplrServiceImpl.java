package org.samplr.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.samplr.client.SamplrService;
import org.samplr.shared.model.SampleSourceType;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@Singleton
public class SamplrServiceImpl extends RemoteServiceServlet implements SamplrService {
  private final PersistenceManager persistenceManager;

  @Inject
  public SamplrServiceImpl(final PersistenceManager persistenceManager) {
    Preconditions.checkNotNull(persistenceManager, "persistenceManager may not be null.");

    this.persistenceManager = persistenceManager;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<SampleSourceType> getSampleSourceTypes() {
    final Query query = persistenceManager.newQuery(SampleSourceType.class);

    return ImmutableList.copyOf((List<SampleSourceType>)query.execute());
  }
}
