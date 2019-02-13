package ftn.uns.ac.rs.naucnaCentrala.camunda.dto;

public class MagazineResponseDTO {

	String name;
	
	public MagazineResponseDTO() {
		
	}

	public MagazineResponseDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
