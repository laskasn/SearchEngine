package org.gcube.rest.index.service.resources;

import java.io.IOException;
import java.util.Properties;

import org.gcube.rest.index.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceProperties {
	
	
	private Properties indexProps = null;
	
	private int maxFragmentCnt;
	private int maxFragmentSize;
//	private int noReplicas;
	private int noShards;
	private int elasticSearchPort;
	private boolean defaultSameCluster;
	private String dataDir;
	private int maxResults;

	private boolean clientMode;
	private String hostname;
	private int port;
	
	private String restApiEndpoint;
	
	private String zkConnStr;
	
	static final Logger logger = LoggerFactory.getLogger(ServiceProperties.class);
	
	
//	static{
	public ServiceProperties(){
		
		indexProps = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			indexProps.load(classLoader.getResourceAsStream(Constants.PROPERTIES_FILE));
		} catch (IOException e) {
			logger.error("Could not find the deploy.properties file to initiate the Index Service!");
		}
		
		maxFragmentCnt = Integer.parseInt(indexProps.getProperty("maxFragmentCnt", "5").trim());
		maxFragmentSize = Integer.parseInt(indexProps.getProperty("maxFragmentSize", "80").trim());
//		noReplicas = Integer.parseInt(indexProps.getProperty("noReplicas", "1").trim());
		noShards = Integer.parseInt(indexProps.getProperty("noShards", "2").trim());
		elasticSearchPort = Integer.parseInt(indexProps.getProperty("elasticSearchPort", "9200").trim());
		defaultSameCluster = Boolean.parseBoolean(indexProps.getProperty("defaultSameCluster", "true").trim());
		dataDir = indexProps.getProperty("dataDir", "./index-data").trim();
		clientMode = Boolean.parseBoolean(indexProps.getProperty("clientMode", "false").trim());
		maxResults = Integer.parseInt(indexProps.getProperty("maxResults", "15").trim());
		
		hostname = indexProps.getProperty("hostname");
		if(hostname==null) logger.error("Cannot find any hostname within deploy.properties. Are you sure you have defined it properly?");
		port = Integer.parseInt(indexProps.getProperty("port", "8080"));
		
		restApiEndpoint = indexProps.getProperty("restApiEndpoint").trim();
		zkConnStr = indexProps.getProperty("zkConnStr").trim();
		
	}

	public int getMaxFragmentCnt() {
		return maxFragmentCnt;
	}

	public int getMaxFragmentSize() {
		return maxFragmentSize;
	}

//	public int getNoReplicas() {
//		return noReplicas;
//	}

	public int getNoShards() {
		return noShards;
	}

	public int getElasticSearchPort() {
		return elasticSearchPort;
	}

	public boolean isDefaultSameCluster() {
		return defaultSameCluster;
	}


	public String getDataDir() {
		return dataDir;
	}

	public boolean isClientMode() {
		return clientMode;
	}

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}
	
	public int getMaxResults() {
		return maxResults;
	}

	public String getRestApiEndpoint() {
		return restApiEndpoint;
	}

	public String getZkConnStr() {
		return zkConnStr;
	}

	
	
	
	
}
