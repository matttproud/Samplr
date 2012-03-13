package org.samplr.server.storage;

import java.util.List;

import org.samplr.shared.model.SampleSource;
import org.samplr.shared.model.SampleSourceType;
import org.samplr.shared.utility.Normalization;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Query;

@Singleton
public class Manager {
  private final DAO dao;
  private final Normalization normalization = new Normalization();
  private final SampleSourceTypeManager sampleSourceTypeManager = new SampleSourceTypeManager();
  private final SampleSourceManager sampleSourceManager = new SampleSourceManager();

  @Inject
  public Manager(final DAO dao) {
    Preconditions.checkNotNull(dao, "dao may not be null.");

    this.dao = dao;
  }

  public SampleSourceTypeManager sampleSourceType() {
    return sampleSourceTypeManager;
  }

  public SampleSourceManager sampleSource() {
    return sampleSourceManager;
  }

  public class SampleSourceTypeManager {
    private SampleSourceTypeManager() {}

    public SampleSourceType getByKey(final String key) throws NotFoundException {
      Preconditions.checkNotNull(key, "key may not be null.");
      final Key<SampleSourceType> entityKey = new Key<SampleSourceType>(SampleSourceType.class, key);

      return dao.ofy().get(entityKey);
    }

    public List<SampleSourceType> queryByTitle(final String title) {
      Preconditions.checkNotNull(title, "title may not be null.");

      final String normalizedTitle = normalization.normalize(title);

      final Query<SampleSourceType> query = dao
          .ofy().query(SampleSourceType.class).filter("normalizedTitle = ", normalizedTitle);

      return Lists.newArrayList(query);
    }

    public Key<SampleSourceType> commit(final SampleSourceType sampleSourceType) {
      Preconditions.checkNotNull(sampleSourceType, "sampleSourceType may not be null.");

      return dao.ofy().put(sampleSourceType);
    }

    public void delete(final SampleSourceType sampleSourceType) {
      Preconditions.checkNotNull(sampleSourceType, "sampleSourceType may not be null.");

      dao.ofy().delete(sampleSourceType);
    }
  }

  public class SampleSourceManager {
    private SampleSourceManager() {}

    public SampleSource getByKey(final String key) throws NotFoundException {
      Preconditions.checkNotNull(key, "key may not be null.");
      final Key<SampleSource> entityKey = new Key<SampleSource>(SampleSource.class, key);

      return dao.ofy().get(entityKey);
    }

    public ImmutableList<SampleSource> queryByTitle(final String title) {
      Preconditions.checkNotNull(title, "title may not be null.");

      final String normalizedTitle = normalization.normalize(title);

      final Query<SampleSource> query = dao
          .ofy().query(SampleSource.class).filter("normalizedTitle = ", normalizedTitle);

      return ImmutableList.copyOf(query);
    }

    public Key<SampleSource> commit(final SampleSource sampleSource) {
      Preconditions.checkNotNull(sampleSource, "sampleSource may not be null.");

      return dao.ofy().put(sampleSource);
    }

    public void delete(final SampleSource sampleSource) {
      Preconditions.checkNotNull(sampleSource, "sampleSource may not be null.");

      dao.ofy().delete(sampleSource);
    }
  }
}