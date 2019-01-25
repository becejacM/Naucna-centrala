package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class MagazineDetailsDTO {

	private String name;
	private String processInstanceId;
	private String starter;
	
	public MagazineDetailsDTO() {
		
	}

	public MagazineDetailsDTO(String name, String processInstanceId, String starter) {
		super();
		this.name = name;
		this.processInstanceId = processInstanceId;
		this.starter = starter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getStarter() {
		return starter;
	}

	public void setStarter(String starter) {
		this.starter = starter;
	}

	
}
