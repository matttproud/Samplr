/**
 * 
 */
package org.samplr.shared.model;

import java.io.Serializable;

import javax.persistence.Id;

import org.samplr.shared.utility.Normalization;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.inject.Singleton;

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

  public static class Builder {
    private final Normalization normalization;

    private Optional<String> title = Optional.absent();

    Builder(final Normalization normalization) {
      this.normalization = normalization;
    }

    public Builder withTitle(final String title) {
      this.title = Optional.of(title);

      return this;
    }

    public SampleSourceType build() {
      final String normalizedTitle = normalization.normalize(title.get());
      final String key = normalizedTitle;

      return new SampleSourceType(title.get(), normalizedTitle, key);
    }
  }

  public static class Mutator {
    private final Normalization normalization;
    private final SampleSourceType original;

    private Optional<String> newTitle = Optional.absent();

    Mutator(final SampleSourceType original, final Normalization normalization) {
      this.original = original;
      this.normalization = normalization;
    }

    public Mutator withTitle(final String newTitle) {
      this.newTitle = Optional.of(newTitle);

      return this;
    }

    public SampleSourceType generate() {
      final String futureTitle = newTitle.or(original.getTitle());
      final String futureNormalizedTitle = normalization.normalize(futureTitle);

      return new SampleSourceType(futureTitle, futureNormalizedTitle, original.getKey());
    }
  }

  @Singleton
  public static class MutationManager {
    private final Normalization normalization = new Normalization();

    public Mutator from(final SampleSourceType original) {
      return new Mutator(original, normalization);
    }

    public Builder create() {
      return new Builder(normalization);
    }
  }
}
