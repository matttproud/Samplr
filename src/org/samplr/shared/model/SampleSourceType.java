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
  private Long key;

  private String title;

  private String normalizedTitle;

  private SampleSourceType() {
  }

  SampleSourceType(final String title, final String normalizedTitle, final String key) {
    Preconditions.checkNotNull(title, "title may not be null.");
    Preconditions.checkNotNull(normalizedTitle, "normalizedTitle may not be null.");
    Preconditions.checkNotNull(key, "key may not be null.");

    this.title = title;
    this.normalizedTitle = normalizedTitle;
    this.key = (long)key.hashCode();  //XXX
  }

  public SampleSourceType(final String title, final String normalizedTitle) {
    Preconditions.checkNotNull(title, "title may not be null.");
    Preconditions.checkNotNull(normalizedTitle, "normalizedTitle may not be null.");

    this.title = title;
    this.normalizedTitle = normalizedTitle;
  }

  public void setTitle(final String title) {
    Preconditions.checkNotNull(title, "title may not be null.");

    this.title = title;
  }

  public void setNormalizedTitle(final String normalizedTitle) {
    Preconditions.checkNotNull(normalizedTitle, "normalizedTitle may not be null.");

    this.normalizedTitle = normalizedTitle;
  }

  public String getTitle() {
    return title;
  }

  public String getNormalizedTitle() {
    return normalizedTitle;
  }

  public long getKey() {
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
  public static class Factory {
    private final DAO dao;
    private final Normalization normalization;

    @Inject
    public Factory(final DAO dao, final Normalization normalization) {
      Preconditions.checkNotNull(dao, "dao may not be null.");
      Preconditions.checkNotNull(normalization, "normalization may not be null.");

      this.dao = dao;
      this.normalization = normalization;
    }

    public SampleSourceType getByKey(final String key) throws NotFoundException {
      final Key<SampleSourceType> entityKey = new Key<SampleSourceType>(SampleSourceType.class, key);

      return dao.ofy().get(entityKey);
    }

    public ImmutableList<SampleSourceType> queryByTitle(final String title) {
      final String normalizedTitle = normalization.normalize(title);

      final Query<SampleSourceType> query = dao
          .ofy().query(SampleSourceType.class).filter("normalizedTitle = ", normalizedTitle);

      return ImmutableList.copyOf(query);
    }

    public Mutator from(final SampleSourceType original) {
      return new Mutator(original);
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

      public SampleSourceType build() {
        if (newTitle != null) {
          final String newNormalizedTitle = normalization.normalize(newTitle);
          final String key = original.key.toString();

          return new SampleSourceType(newTitle, newNormalizedTitle, key);
        } else {
          return original;
        }
      }
    }
  }
}
