package org.samplr.client;

import java.util.List;

import org.samplr.shared.model.SampleSourceType;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The asynchronous counterpart of {@link SamplrService}.
 */
public interface SamplrServiceAsync {
  void getSampleSourceTypes(AsyncCallback<List<SampleSourceType>> callback);

  void createSampleSourceType(AsyncCallback<Boolean> callback);
}
