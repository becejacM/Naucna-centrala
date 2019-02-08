package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class NCTransactionRequestDTO {

	private String naslovRada;
	private String kupac;
	private String prodavac;
	
	public NCTransactionRequestDTO() {
		
	}

	public NCTransactionRequestDTO(String naslovRada, String kupac, String prodavac) {
		super();
		this.naslovRada = naslovRada;
		this.kupac = kupac;
		this.prodavac = prodavac;
	}

	public String getNaslovRada() {
		return naslovRada;
	}

	public void setNaslovRada(String naslovRada) {
		this.naslovRada = naslovRada;
	}

	public String getKupac() {
		return kupac;
	}

	public void setKupac(String kupac) {
		this.kupac = kupac;
	}

	public String getProdavac() {
		return prodavac;
	}

	public void setProdavac(String prodavac) {
		this.prodavac = prodavac;
	}

	@Override
	public String toString() {
		return "NCTransactionRequestDTO [naslovRada=" + naslovRada + ", kupac=" + kupac + ", prodavac=" + prodavac
				+ "]";
	}
	
	
}
