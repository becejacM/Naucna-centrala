package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import java.util.Collection;

public class ProccessMagazineDTO {

	private Collection<MagazineDTO> magazines;
	private String processInstanceId;

	public ProccessMagazineDTO() {
		
	}

	public ProccessMagazineDTO(Collection<MagazineDTO> magazines, String processInstanceId) {
		super();
		this.magazines = magazines;
		this.processInstanceId = processInstanceId;
	}

	public Collection<MagazineDTO> getMagazines() {
		return magazines;
	}

	public void setMagazines(Collection<MagazineDTO> magazines) {
		this.magazines = magazines;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	
}
