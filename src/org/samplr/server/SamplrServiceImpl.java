package org.samplr.server;

import org.samplr.client.SamplrService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@Singleton
public class SamplrServiceImpl extends RemoteServiceServlet implements
SamplrService {
}
