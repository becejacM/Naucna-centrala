package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model;

public final class ResultData {
	
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
	private String highlight;
	private String originalText;
	private String text;
	
	public ResultData() {
		super();
	}

	public ResultData(String filename, String nazivCasopisa, String naslovRada, String apstrakt, String keywords,
			String imeAutora, String prezimeAutora, String mime, String naucneOblasti, String putanjaDoFajla,
			String highlight, String originalText, String text) {
		super();
		this.filename = filename;
		this.nazivCasopisa = nazivCasopisa;
		this.naslovRada = naslovRada;
		this.apstrakt = apstrakt;
		this.keywords = keywords;
		this.imeAutora = imeAutora;
		this.prezimeAutora = prezimeAutora;
		this.mime = mime;
		this.naucneOblasti = naucneOblasti;
		this.putanjaDoFajla = putanjaDoFajla;
		this.highlight = highlight;
		this.originalText = originalText;
		this.text = text;
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

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
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

	
}
