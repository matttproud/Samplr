package org.samplr.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.samplr.client.SamplrService;
import org.samplr.server.storage.DAO;
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
  @Inject
  private DAO dao;

  @SuppressWarnings("unchecked")
  @Override
  public List<SampleSourceType> getSampleSourceTypes() {
    final Query<SampleSourceType> q = dao.ofy().query(SampleSourceType.class);
    final List<SampleSourceType> l = new ArrayList<SampleSourceType>(q.count());

    for (final SampleSourceType e : q) {
      l.add(e);
    }

    return l;
  }

  @Override
  public boolean createSampleSourceType() {
    final SampleSourceType a = new SampleSourceType("Politician", "politician");
    final SampleSourceType b = new SampleSourceType("Crook", "crook");

    final Map<Key<SampleSourceType>, SampleSourceType> r = dao.ofy().put(a, b);

    return r.size() == 2;
  }
}
