/**
 * 
 */
package org.samplr.shared.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * @author mtp
 * 
 */
public class SampleSourceType {
  @Id
  private Long key;

  String title;

  String normalizedTitle;

  Set<Long> sampleSourceKeys = new HashSet<Long>();

  private SampleSourceType() {
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

  public Set<Long> getSampleSourceKeys() {
    return new HashSet<Long>(sampleSourceKeys);
  }

  public void addSampleSource(final SampleSource sampleSource) {
    Preconditions.checkNotNull(sampleSource, "sampleSource may not be null.");

    sampleSourceKeys.add(sampleSource.getKey());
  }

  public void removeSampleSource(final SampleSource sampleSource) {
    Preconditions.checkNotNull(sampleSource, "sampleSource may not be null.");

    sampleSourceKeys.add(sampleSource.getKey());
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
}
