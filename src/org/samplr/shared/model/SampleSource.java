/**
 * 
 */
package org.samplr.shared.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * @author mtp
 *
 */
@PersistenceCapable
public class SampleSource {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  private String key;

  @Persistent
  private String title;

  @Persistent
  private String normalizedTitle;

  @Persistent
  private String sampleSourceTypeKey;

  public SampleSource(final String title, final String normalizedTitle, final SampleSourceType sampleSourceType) {
    Preconditions.checkNotNull(title, "title may not be null.");
    Preconditions.checkNotNull(normalizedTitle, "normalizedTitle may not be null.");
    Preconditions.checkNotNull(sampleSourceType, "sampleSourceType may not be null.");

    this.title = title;
    this.normalizedTitle = normalizedTitle;
    this.sampleSourceTypeKey = sampleSourceType.getKey();
  }

  public void setTitle(final String title) {
    Preconditions.checkNotNull(title, "title may not be null.");

    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setNormalizedTitle(final String normalizedTitle) {
    Preconditions.checkNotNull(normalizedTitle, "normalizedTitle may not be null.");

    this.normalizedTitle = normalizedTitle;
  }

  public String getNormalizedTitle() {
    return normalizedTitle;
  }

  public void setSampleSourceType(final SampleSourceType sampleSourceType) {
    Preconditions.checkNotNull(sampleSourceType, "sampleSourceType may not be null.");

    this.sampleSourceTypeKey = sampleSourceType.getKey();
  }

  public String getSampleSourceTypeKey() {
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
}