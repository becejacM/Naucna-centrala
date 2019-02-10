package ftn.uns.ac.rs.naucnaCentrala.camunda.dto;

public class RegistrationResponseDTO {

	private String author;
	
	public RegistrationResponseDTO() {
		
	}

	public RegistrationResponseDTO(String author) {
		super();
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
