package org.samplr.server.storage;

import java.util.List;
import java.util.logging.Logger;

import org.samplr.shared.model.SampleSource;
import org.samplr.shared.model.SampleSourceType;

import com.google.common.collect.ImmutableList;
import com.google.inject.Singleton;
import com.googlecode.objectify.ObjectifyService;

@Singleton
public class ModelRegistry {
  private static final Logger log = Logger.getLogger(ModelRegistry.class.getName());

  public void register() {
    final List<Class<?>> toRegister = ImmutableList.<Class<?>>builder()
        .add(SampleSource.class)
        .add(SampleSourceType.class)
        .build();

    for (final Class<?> c : toRegister) {
      final String className = c.getName();

      log.info("Registering " + className + "...");
      try {
        ObjectifyService.register(c);
      } catch (final IllegalArgumentException e) {
        log.severe("There was an error " + e + " while registering " + c + ".");
      }
    }
  }
}
