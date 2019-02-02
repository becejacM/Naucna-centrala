package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


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
	
	private String imeAutora;
	
	private String prezimeAutora;
	
	private String mime;
	
	private String naucneOblasti;
	
	private String putanjaDoFajla;

	private String originalText;
	
	private String text;
	
	private String hightlight;

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

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(String naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public String getPutanjaDoFajla() {
		return putanjaDoFajla;
	}

	public void setPutanjaDoFajla(String putanjaDoFajla) {
		this.putanjaDoFajla = putanjaDoFajla;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
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

	
	@Override
	public String toString() {
		return "PaperIndexUnit [filename=" + filename + ", nazivCasopisa=" + nazivCasopisa + ", naslovRada="
				+ naslovRada + ", apstrakt=" + apstrakt + ", keywords=" + keywords + ", imeAutora=" + imeAutora
				+ ", prezimeAutora=" + prezimeAutora + ", mime=" + mime + ", naucneOblasti=" + naucneOblasti
				+ ", putanjaDoFajla=" + putanjaDoFajla + ", originalText=" + originalText + ", text=" + text
				+ ", hightlight=" + hightlight + "]";
	}

	
}
