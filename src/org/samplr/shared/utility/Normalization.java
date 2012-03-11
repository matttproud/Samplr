package org.samplr.shared.utility;

import com.google.inject.Singleton;

/**
 * 
 * @author mtp
 *
 */
@Singleton
public class Normalization {
  public String normalize(final String content) {
    return content.toLowerCase();
  }
}
