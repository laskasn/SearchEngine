package org.gcube.rest.index.client;

import java.util.HashMap;
import java.util.Map;

import org.gcube.rest.index.client.cache.CacheConfig;
import org.gcube.rest.index.client.cache.IndexClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ClientFactory {

	private static final Logger logger = LoggerFactory.getLogger(ClientFactory.class);
	
	
	public static IndexClient getMeAnIndexClient(String scope){
		IndexClient indexClient ;
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(CacheConfig.class);
		ctx.refresh();
		indexClient = ctx.getBean(IndexClient.class);
		indexClient.initiateClient(scope);
		ctx.close();
		return indexClient;
	}
	
	

	
	
}