/**
 * 
 */
package org.samplr.shared.model;

import java.util.ArrayList;
import java.util.List;

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
public class SampleSourceType {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value="true")
  private String key;

  @Persistent
  private String title;

  @Persistent
  private String normalizedTitle;

  @Persistent
  private List<String> sampleSourceKeys = new ArrayList<String>();

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

  public String getKey() {
    return key;
  }

  public List<String> getSampleSourceKeys() {
    return new ArrayList<String>(sampleSourceKeys);
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
