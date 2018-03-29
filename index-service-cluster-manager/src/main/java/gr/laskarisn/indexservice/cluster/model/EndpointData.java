package gr.laskarisn.indexservice.cluster.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;


public class EndpointData implements Serializable{
	

	private static final long serialVersionUID = 1263137321814828289L;

	
	String id;
	String serviceEndpoint;
	String elasticEndpoint;
	Properties remainingParams;
	
	
	public EndpointData() {
		
	}
	
	public EndpointData(String serviceEndpoint) {
		this.id = UUID.randomUUID().toString();
		this.serviceEndpoint = serviceEndpoint;
	}
	
	public EndpointData(String serviceEndpoint, String elasticEndpoint) {
		this.id = UUID.randomUUID().toString();
		this.serviceEndpoint = serviceEndpoint;
		this.elasticEndpoint = elasticEndpoint;
	}
	
	public EndpointData(String serviceEndpoint, String elasticEndpoint, Properties remainingParams) {
		this.id = UUID.randomUUID().toString();
		this.serviceEndpoint = serviceEndpoint;
		this.elasticEndpoint = elasticEndpoint;
		this.remainingParams = remainingParams;
	}
	
	public String getServiceEndpoint() {
		return serviceEndpoint;
	}
	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}
	public String getElasticEndpoint() {
		return elasticEndpoint;
	}
	public void setElasticEndpoint(String elasticEndpoint) {
		this.elasticEndpoint = elasticEndpoint;
	}
	public Properties getRemainingParams() {
		return remainingParams;
	}
	public void setRemainingParams(Properties remainingParams) {
		this.remainingParams = remainingParams;
	}

	
	public String getId() {
		return id;
	}

	
	
	
	@Override
	public boolean equals(Object o) {
		
        if (o == this)
            return true;
 
        if (!(o instanceof EndpointData))
            return false;
         
        EndpointData ep = (EndpointData) o;
         
        return id.equals(ep.id);
		
	}
	
	
	@Override
    public int hashCode() {
        return Objects.hash(id);
    }
	
	
	
	
}
