package gr.laskarisn.indexservice.cluster;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import com.fasterxml.jackson.databind.ObjectMapper;

import gr.laskarisn.indexservice.cluster.model.Constants;
import gr.laskarisn.indexservice.cluster.model.EndpointData;


public class EndpointManager implements Serializable {

	private static final long serialVersionUID = 4923050683810151839L;

	final static Logger log = Logger.getLogger(EndpointManager.class);

	private static EndpointManager instance = null;
    private static String zkConnStr;
    
    private static CuratorFramework client = null;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    

    private EndpointManager() {}
    
    	
    public void publishEndpoint(EndpointData endpointData) throws Exception{   	
    	String znodePath = ZKPaths.makePath(Constants.ZK_FS_PATH, endpointData.getId());
    	byte[] endpointDataByte = objectMapper.writeValueAsString(endpointData).getBytes();
    	try {
    		client.create().forPath(znodePath, endpointDataByte);
            client.setData().forPath(znodePath, endpointDataByte);
        }catch ( KeeperException.NoNodeException e ) {
        	e.printStackTrace();
            client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(znodePath, endpointDataByte);
        }
    }
    
    public void unpublishEndpoint(EndpointData endpointData) throws Exception{
    	String znodePath = ZKPaths.makePath(Constants.ZK_FS_PATH, endpointData.getId());
    	client.delete().forPath(znodePath);
    }
    
    public int getEndpointNumber() throws Exception {
    	return client.getChildren().forPath(Constants.ZK_FS_PATH).size();
    }
    
    public void printEndpoints() throws Exception {
    	System.out.println(client.getChildren().forPath(Constants.ZK_FS_PATH));
    }
    
    
    public Set<EndpointData> getEndpointData() throws Exception {
    	return client.getChildren().forPath(Constants.ZK_FS_PATH).parallelStream().map(path -> {
    		try {
				return objectMapper.readValue(client.getData().forPath(ZKPaths.makePath(Constants.ZK_FS_PATH, path)), EndpointData.class);
			} catch (Exception e) {
				return null;
			}
    	})
    	.filter(obj -> obj!=null)
    	.collect(Collectors.toSet());
    }
    
    
    public Set<String> getServiceEndpoints() throws Exception {
    	return client.getChildren().forPath(Constants.ZK_FS_PATH).parallelStream().map(path -> {
    		try {
    			EndpointData ed = objectMapper.readValue(client.getData().forPath(ZKPaths.makePath(Constants.ZK_FS_PATH, path)), EndpointData.class);
				return ed.getServiceEndpoint();
			} catch (Exception e) {
				return null;
			}
    	})
    	.filter(obj -> obj!=null)
    	.collect(Collectors.toSet());
    }

    
    /**
     * this initializer should be used by all new service endpoints
     */
    public static synchronized EndpointManager getInstance(String zkConnStr){
    	if (instance == null){
			instance = new EndpointManager();
			
            try {
            	
            	if (!zkConnStr.isEmpty()){
            		EndpointManager.setZkConnStr(zkConnStr);
            	}else {
            		throw new Exception("Could not initialize connection to Zookeeper. Expect serious problems.");
            	}
            	
            	client = CuratorFrameworkFactory.newClient(zkConnStr, new ExponentialBackoffRetry(1000, Integer.MAX_VALUE));
                client.start();
                
                log.debug("The zookeeper endpoint publisher instance has been created");
                
			} catch (Exception e) {
				log.error("Could not start children client.");
				e.printStackTrace();
			}
		}
		
		return instance;
    }
    

	public static String getZkConnStr() {
		return zkConnStr;
	}

	private static void setZkConnStr(String zkConnStr) {
		EndpointManager.zkConnStr = zkConnStr;
	}

	
}
