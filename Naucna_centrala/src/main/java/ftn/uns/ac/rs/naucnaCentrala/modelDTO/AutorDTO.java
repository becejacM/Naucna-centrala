package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class AutorDTO {

	private String imeAutora;
	private String prezimeAutora;
	private String email;
	private String city;
	private String state;
	
	public AutorDTO() {
		
	}

	public AutorDTO(String imeAutora, String prezimeAutora, String email, String city, String state) {
		super();
		this.imeAutora = imeAutora;
		this.prezimeAutora = prezimeAutora;
		this.email = email;
		this.city = city;
		this.state = state;
	}

	public String getImeAutora() {
		return imeAutora;
	}

	public void setImeAutora(String imeAutora) {
		this.imeAutora = imeAutora;
	}

	public String getPrezimeAutora() {
		return prezimeAutora;
	}

	public void setPrezimeAutora(String prezimeAutora) {
		this.prezimeAutora = prezimeAutora;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	
}
