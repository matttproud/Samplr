/**
 * 
 */
package org.samplr.shared.model;

import javax.persistence.Id;

import org.samplr.server.storage.DAO;
import org.samplr.server.utility.Normalization;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Query;

/**
 * @author mtp
 *
 */
public class SampleSource {
  @Id
  private String key;

  private String title;

  private String normalizedTitle;

  private Key<SampleSourceType> sampleSourceTypeKey;

  private SampleSource() {}

  SampleSource(final String title, final String normalizedTitle, final String key, final Key<SampleSourceType> sampleSourceTypeKey) {
    this.title = title;
    this.normalizedTitle = normalizedTitle;
    this.key = key;
    this.sampleSourceTypeKey = sampleSourceTypeKey;
  }

  public String getTitle() {
    return title;
  }

  public String getNormalizedTitle() {
    return normalizedTitle;
  }

  public Key<SampleSourceType> getSampleSourceTypeKey() {
    return sampleSourceTypeKey;
  }

  public String getKey() {
    return key;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(key, title, normalizedTitle, sampleSourceTypeKey);
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof SampleSource)) {
      return false;
    }

    final SampleSource casted = (SampleSource)other;

    return (Objects.equal(key, casted.getKey()) && Objects.equal(title, casted.getTitle()) && Objects.equal(normalizedTitle, casted.getNormalizedTitle()) && Objects.equal(sampleSourceTypeKey, casted.getSampleSourceTypeKey()));
  }

  @Singleton
  public static class StorageManager {
    private final DAO dao;
    private final Normalization normalization;

    @Inject
    public StorageManager(final DAO dao, final Normalization normalization) {
      Preconditions.checkNotNull(dao, "dao may not be null.");
      Preconditions.checkNotNull(normalization, "normalization may not be null.");

      this.dao = dao;
      this.normalization = normalization;
    }

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

    public Mutator from(final SampleSource original) {
      return new Mutator(original);
    }

    public Builder create() {
      return new Builder();
    }

    public class Builder {
      private Optional<String> title = Optional.absent();
      private Optional<Key<SampleSourceType>> sampleSourceTypeKey = Optional.absent();

      Builder() {}

      public Builder withTitle(final String title) {
        this.title = Optional.of(title);

        return this;
      }

      public Builder withSampleSourceType(final SampleSourceType sampleSourceType) {
        Preconditions.checkNotNull(sampleSourceType, "sampleSourceType may not be null.");

        sampleSourceTypeKey = Optional.of(new Key<SampleSourceType>(SampleSourceType.class, sampleSourceType.getKey()));

        return this;
      }

      public SampleSource build() {
        Preconditions.checkNotNull("title", "title may not be null.");

        final String futureTitle = title.get();
        final String normalizedTitle = normalization.normalize(futureTitle);
        final String key = normalizedTitle;
        final Key<SampleSourceType> futureSampleSourceTypeKey = sampleSourceTypeKey.get();

        return new SampleSource(futureTitle, normalizedTitle, key, futureSampleSourceTypeKey);
      }
    }

    public class Mutator {
      private final SampleSource original;
      private Optional<String> newTitle = Optional.absent();
      private Optional<Key<SampleSourceType>> newSampleSourceTypeKey = Optional.absent();

      Mutator(final SampleSource original) {
        Preconditions.checkNotNull(original, "original may not be null.");

        this.original = original;
      }

      public Mutator withTitle(final String newTitle) {
        this.newTitle = Optional.of(newTitle);

        return this;
      }

      public Mutator withSampleSourceType(final SampleSourceType sampleSourceType) {
        Preconditions.checkNotNull(sampleSourceType, "sampleSourceType may not be null.");

        this.newSampleSourceTypeKey = Optional.of(new Key<SampleSourceType>(SampleSourceType.class, sampleSourceType.getKey()));

        return this;
      }

      public SampleSource generate() {
        final String futureTitle = newTitle.or(original.getTitle());
        final String futureNormalizedTitle = normalization.normalize(futureTitle);
        final Key<SampleSourceType> futureKey = newSampleSourceTypeKey.or(original.getSampleSourceTypeKey());

        return new SampleSource(futureTitle, futureNormalizedTitle, original.getKey(), futureKey);
      }
    }
  }
}