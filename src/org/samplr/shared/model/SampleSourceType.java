/**
 * 
 */
package org.samplr.shared.model;

import java.io.Serializable;

import javax.persistence.Id;

import org.samplr.server.storage.DAO;
import org.samplr.server.utility.Normalization;

import com.google.common.base.Objects;
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
public class SampleSourceType implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String key;

  private String title;

  private String normalizedTitle;

  private SampleSourceType() {}

  SampleSourceType(final String title, final String normalizedTitle, final String key) {
    Preconditions.checkNotNull(title, "title may not be null.");
    Preconditions.checkNotNull(normalizedTitle, "normalizedTitle may not be null.");
    Preconditions.checkNotNull(key, "key may not be null.");

    this.title = title;
    this.normalizedTitle = normalizedTitle;
    this.key = key;
  }

  public String getTitle() {
    return title;
  }

  public String getNormalizedTitle() {
    return normalizedTitle;
  }

  public String getKey() {
    return key;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(key, title, normalizedTitle);
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof SampleSourceType)) {
      return false;
    }

    final SampleSourceType casted = (SampleSourceType)other;

    return (Objects.equal(key, casted.getKey()) && Objects.equal(title, casted.getTitle()) && Objects.equal(normalizedTitle, casted.getNormalizedTitle()));
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

    public SampleSourceType getByKey(final String key) throws NotFoundException {
      Preconditions.checkNotNull(key, "key may not be null.");
      final Key<SampleSourceType> entityKey = new Key<SampleSourceType>(SampleSourceType.class, key);

      return dao.ofy().get(entityKey);
    }

    public ImmutableList<SampleSourceType> queryByTitle(final String title) {
      Preconditions.checkNotNull(title, "title may not be null.");

      final String normalizedTitle = normalization.normalize(title);

      final Query<SampleSourceType> query = dao
          .ofy().query(SampleSourceType.class).filter("normalizedTitle = ", normalizedTitle);

      return ImmutableList.copyOf(query);
    }

    public Key<SampleSourceType> commit(final SampleSourceType sampleSourceType) {
      Preconditions.checkNotNull(sampleSourceType, "sampleSourceType may not be null.");

      return dao.ofy().put(sampleSourceType);
    }

    public void delete(final SampleSourceType sampleSourceType) {
      Preconditions.checkNotNull(sampleSourceType, "sampleSourceType may not be null.");

      dao.ofy().delete(sampleSourceType);
    }

    public Mutator from(final SampleSourceType original) {
      return new Mutator(original);
    }

    public Builder create() {
      return new Builder();
    }

    public class Builder {
      private String title;

      Builder() {}

      public Builder withTitle(final String title) {
        this.title = title;

        return this;
      }

      public SampleSourceType build() {
        Preconditions.checkNotNull("title", "title may not be null.");

        final String normalizedTitle = normalization.normalize(title);
        final String key = normalizedTitle;

        return new SampleSourceType(title, normalizedTitle, key);
      }
    }

    public class Mutator {
      private final SampleSourceType original;
      private String newTitle;

      Mutator(final SampleSourceType original) {
        this.original = original;
      }

      public Mutator withTitle(final String newTitle) {
        this.newTitle = newTitle;

        return this;
      }

      public SampleSourceType generate() {
        if (newTitle != null) {
          final String newNormalizedTitle = normalization.normalize(newTitle);
          final String key = original.getKey();

          return new SampleSourceType(newTitle, newNormalizedTitle, key);
        } else {
          return original;
        }
      }
    }
  }
}
