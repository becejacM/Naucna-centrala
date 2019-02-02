package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class SearchHitDTO {

    private long id;

    private String nazivCasopisa;
    
    private String naslovRada;
    
    private String autor;

    private String keywords;

    private String text;
    
    private String naucnaOblast;

    public SearchHitDTO() {
    }

	public SearchHitDTO(long id, String nazivCasopisa, String naslovRada, String autor, String keywords, String text,
			String naucnaOblast) {
		super();
		this.id = id;
		this.nazivCasopisa = nazivCasopisa;
		this.naslovRada = naslovRada;
		this.autor = autor;
		this.keywords = keywords;
		this.text = text;
		this.naucnaOblast = naucnaOblast;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNazivCasopisa() {
		return nazivCasopisa;
	}

	public void setNazivCasopisa(String nazivCasopisa) {
		this.nazivCasopisa = nazivCasopisa;
	}

	public String getNaslovRada() {
		return naslovRada;
	}

	public void setNaslovRada(String naslovRada) {
		this.naslovRada = naslovRada;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(String naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}
    
    

}
