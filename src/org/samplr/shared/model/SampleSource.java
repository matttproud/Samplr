/**
 * 
 */
package org.samplr.shared.model;

import javax.persistence.Id;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * @author mtp
 *
 */
public class SampleSource {
  @Id
  private Long key;

  private String title;

  private String normalizedTitle;

  private Long sampleSourceTypeKey;

  private SampleSource() {
  }

  public SampleSource(final String title, final String normalizedTitle, final SampleSourceType sampleSourceType) {
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

  public long getSampleSourceTypeKey() {
    return sampleSourceTypeKey;
  }

  public long getKey() {
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