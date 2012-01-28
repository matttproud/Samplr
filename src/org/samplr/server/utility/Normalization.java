package org.samplr.server.utility;

import com.google.inject.Singleton;

/**
 * 
 * @author mtp
 *
 */
@Singleton
public class Normalization {
  public String normalizeTitle(final String title) {
    return title.toLowerCase();
  }
}
