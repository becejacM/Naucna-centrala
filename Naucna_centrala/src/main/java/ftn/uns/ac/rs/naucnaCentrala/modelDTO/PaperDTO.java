package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import java.util.ArrayList;

import ftn.uns.ac.rs.naucnaCentrala.model.Coauthor;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;

public class PaperDTO {

	private Long id;
	private String naslovRada;
	private String apstrakt;
	private String keywords;
	private ArrayList<AutorDTO> autori = new ArrayList<AutorDTO>();
	private String naucnaOblast;
	private String nazivCasopisa;
	private String rad;
	private String filename;
	private String komentarDorada;
	
	public PaperDTO() {
		
	}

	public PaperDTO(String naslovRada, String apstrakt, String keywords) {
		this.naslovRada = naslovRada;
		this.apstrakt = apstrakt;
		this.keywords = keywords;
	}
	
	public PaperDTO(String rad, String filename) {
		this.rad = rad;
		this.filename = filename;
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public ArrayList<AutorDTO> getAutori() {
		return autori;
	}


	public void setAutori(ArrayList<AutorDTO> autori) {
		this.autori = autori;
	}


	public String getNaucnaOblast() {
		return naucnaOblast;
	}


	public void setNaucnaOblast(String naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}


	public String getNazivCasopisa() {
		return nazivCasopisa;
	}


	public void setNazivCasopisa(String nazivCasopisa) {
		this.nazivCasopisa = nazivCasopisa;
	}


	public String getRad() {
		return rad;
	}


	public void setRad(String rad) {
		this.rad = rad;
	}


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public PaperDTO(Long id, String naslovRada, String apstrakt, String keywords, ArrayList<AutorDTO> autori,
			String naucnaOblast, String nazivCasopisa, String rad) {
		super();
		this.id = id;
		this.naslovRada = naslovRada;
		this.apstrakt = apstrakt;
		this.keywords = keywords;
		this.autori = autori;
		this.naucnaOblast = naucnaOblast;
		this.nazivCasopisa = nazivCasopisa;
		this.rad = rad;
	}


	public PaperDTO(Paper a) {
		this.rad = a.getFilename();
		this.id = a.getId();
		this.naslovRada = a.getNaslovRada();
		this.nazivCasopisa = a.getMagazine().getName();
		this.apstrakt = a.getApstract();
		this.keywords = a.getKeywords();
		ArrayList<AutorDTO> autori = new ArrayList<AutorDTO>();
		for (Coauthor autorDTO : a.getCoauthors()) {
			AutorDTO aaa = new AutorDTO(autorDTO.getFirstname(), autorDTO.getLastname(), autorDTO.getEmail(), autorDTO.getCity(), autorDTO.getState());
			autori.add(aaa);
		}
		this.autori = autori;
		this.naucnaOblast = a.getScientificField().getScientificFieldName().name();

    }


	@Override
	public String toString() {
		return "PaperDTO [id=" + id + ", naslovRada=" + naslovRada + ", apstrakt=" + apstrakt + ", keywords=" + keywords
				+ ", autori=" + autori + ", naucnaOblast=" + naucnaOblast + ", nazivCasopisa=" + nazivCasopisa
				+ ", rad=" + rad + "]";
	}

	public String getKomentarDorada() {
		return komentarDorada;
	}

	public void setKomentarDorada(String komentarDorada) {
		this.komentarDorada = komentarDorada;
	}
	
	
	
}
