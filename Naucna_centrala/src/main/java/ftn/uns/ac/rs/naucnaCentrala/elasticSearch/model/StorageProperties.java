package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private String location;
    private String temporarylocation;
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

	public String getTemporarylocation() {
		return temporarylocation;
	}

	public void setTemporarylocation(String temporarylocation) {
		this.temporarylocation = temporarylocation;
	}
    
}
