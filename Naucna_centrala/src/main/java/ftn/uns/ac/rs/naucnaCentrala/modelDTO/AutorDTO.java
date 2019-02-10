package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class AutorDTO {

	private String imeAutora;
	private String prezimeAutora;
	
	public AutorDTO() {
		
	}

	public AutorDTO(String imeAutora, String prezimeAutora) {
		super();
		this.imeAutora = imeAutora;
		this.prezimeAutora = prezimeAutora;
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
	
	
}
