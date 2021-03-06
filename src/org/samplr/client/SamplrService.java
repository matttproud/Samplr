package org.samplr.client;

import java.util.List;

import org.samplr.shared.model.SampleSourceType;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service")
public interface SamplrService extends RemoteService {
  public List<SampleSourceType> getSampleSourceTypes();
  public SampleSourceType createSampleSourceType(final SampleSourceType future);
}
