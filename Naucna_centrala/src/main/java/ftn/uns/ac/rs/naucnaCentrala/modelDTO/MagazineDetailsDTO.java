package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class MagazineDetailsDTO {

	private String name;
	private String processInstanceId;
	
	public MagazineDetailsDTO() {
		
	}

	public MagazineDetailsDTO(String name, String processInstanceId) {
		super();
		this.name = name;
		this.processInstanceId = processInstanceId;
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
	
	
}
