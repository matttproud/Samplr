/**
 * 
 */
package org.samplr.shared.model;

import javax.persistence.Id;

import org.samplr.shared.utility.Normalization;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import com.googlecode.objectify.Key;

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

  public static class Builder {
    private final Normalization normalization;

    private Optional<String> title = Optional.absent();
    private Optional<Key<SampleSourceType>> sampleSourceTypeKey = Optional.absent();

    Builder(final Normalization normalization) {
      this.normalization = normalization;
    }

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
      final String futureTitle = title.get();
      final String normalizedTitle = normalization.normalize(futureTitle);
      final String key = normalizedTitle;
      final Key<SampleSourceType> futureSampleSourceTypeKey = sampleSourceTypeKey.get();

      return new SampleSource(futureTitle, normalizedTitle, key, futureSampleSourceTypeKey);
    }
  }

  public static class Mutator {
    private final SampleSource original;
    private final Normalization normalization;

    private Optional<String> newTitle = Optional.absent();
    private Optional<Key<SampleSourceType>> newSampleSourceTypeKey = Optional.absent();

    Mutator(final SampleSource original, final Normalization normalization) {
      Preconditions.checkNotNull(original, "original may not be null.");

      this.original = original;
      this.normalization = normalization;
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

  @Singleton
  public static class MutationManager {
    private final Normalization normalization = new Normalization();

    public Mutator from(final SampleSource original) {
      return new Mutator(original, normalization);
    }

    public Builder create() {
      return new Builder(normalization);
    }
  }
}