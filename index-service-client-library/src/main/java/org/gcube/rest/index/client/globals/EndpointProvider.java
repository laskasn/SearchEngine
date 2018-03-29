package org.gcube.rest.index.client.globals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.gcube.rest.index.common.discover.exceptions.IndexDiscoverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.laskarisn.indexservice.cluster.EndpointManager;


public class EndpointProvider {

	private static final Logger logger = LoggerFactory.getLogger(EndpointProvider.class);

	EndpointManager endpointManager;

	
	public EndpointProvider(String clusterConnectionStr) {
		endpointManager = EndpointManager.getInstance(clusterConnectionStr);
	}
	
	public int endpointsNumber() {
		try {
			return endpointManager.getEndpointNumber();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public String getAnEndpoint() throws Exception {
		Set<String> endpoints = endpointManager.getServiceEndpoints();
		if(endpoints.isEmpty())
			throw new IndexDiscoverException("There are no available index service endpoints");
		return endpointManager.getServiceEndpoints().toArray(new String[endpoints.size()])[new Random().nextInt(endpoints.size())];
	}
	
	
	
	
}

