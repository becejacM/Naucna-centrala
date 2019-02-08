package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import java.util.ArrayList;
import java.util.Collection;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.AutorIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;

public class SearchHitDTO {

    private long id;

    private String filename;
	
   	private String nazivCasopisa;

   	private String naslovRada;
   	
   	private String apstrakt;
   	
   	private String keywords;
   	
   	private Collection<AutorIndexUnit> autori = new ArrayList<AutorIndexUnit>();
   		
   	private String naucnaOblast;
   		
   	private String text;
   	
   	private String hightlight;
   	
   	private String dostupnost;
    
    public SearchHitDTO() {
    }

	public SearchHitDTO(PaperIndexUnit index) {
		this.filename = index.getFilename();
		this.nazivCasopisa = index.getNazivCasopisa();
		this.naslovRada = index.getNaslovRada();
		this.apstrakt = index.getApstrakt();
		this.keywords = index.getKeywords();
		this.autori = index.getAutori();
		this.keywords = index.getKeywords();
		this.naucnaOblast = index.getNaucnaOblast();
		this.hightlight = index.getHightlight();
		this.text = index.getText();
		this.dostupnost = index.getDostupnost();
	}

	public SearchHitDTO(Paper index) {
		this.filename = index.getFilename();
		this.nazivCasopisa = index.getMagazine().getName();
		this.naslovRada = index.getNaslovRada();
		this.keywords = index.getKeywords();
		this.naucnaOblast = index.getScientificField().getScientificFieldName().name();
		this.dostupnost = index.getDostupnost();
	}
	public SearchHitDTO(String filename, String nazivCasopisa, String naslovRada, String apstrakt,
			String keywords, Collection<AutorIndexUnit> autori, String naucnaOblast, String text, String hightlight,
			String dostupnost) {
		super();
		this.filename = filename;
		this.nazivCasopisa = nazivCasopisa;
		this.naslovRada = naslovRada;
		this.apstrakt = apstrakt;
		this.keywords = keywords;
		this.autori = autori;
		this.naucnaOblast = naucnaOblast;
		this.text = text;
		this.hightlight = hightlight;
		this.dostupnost = dostupnost;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	public String getApstrakt() {
		return apstrakt;
	}

	public void setApstrakt(String apstrakt) {
		this.apstrakt = apstrakt;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	public String getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(String naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHightlight() {
		return hightlight;
	}

	public void setHightlight(String hightlight) {
		this.hightlight = hightlight;
	}

	public String getDostupnost() {
		return dostupnost;
	}

	public void setDostupnost(String dostupnost) {
		this.dostupnost = dostupnost;
	}



	public Collection<AutorIndexUnit> getAutori() {
		return autori;
	}



	public void setAutori(Collection<AutorIndexUnit> autori) {
		this.autori = autori;
	}

	
}
