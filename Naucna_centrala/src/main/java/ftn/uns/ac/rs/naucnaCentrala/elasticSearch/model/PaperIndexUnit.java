package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import ftn.uns.ac.rs.naucnaCentrala.model.ScientificField;


//@Document(indexName = PaperIndexUnit.INDEX_NAME, type = PaperIndexUnit.TYPE_NAME, shards = 1, replicas = 0)
@Document(indexName = "naucnacentrala6", createIndex = false)
public class PaperIndexUnit {


	//public static final String INDEX_NAME = "naucnacentrala2";
	//public static final String TYPE_NAME = "espaper2";
	
	@Id
    @Field(type = FieldType.Keyword)
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
