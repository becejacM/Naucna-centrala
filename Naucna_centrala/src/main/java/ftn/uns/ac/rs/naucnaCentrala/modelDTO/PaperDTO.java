package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import ftn.uns.ac.rs.naucnaCentrala.model.Paper;

public class PaperDTO {

	private Long id;
	private String name;
	private String author;
	private String highlight;
	private String keywords;
	
	public PaperDTO() {
		
	}

	public PaperDTO(Long id, String name, String author, String highlight, String keywords) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.highlight = highlight;
		this.keywords = keywords;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public PaperDTO(Paper a) {
		//articleDTO.setAuthor(a.get);
		//articleDTO.setKeywords(a.getKeywords());
		this.name = a.getFilename();
		this.id = a.getId();
		//articleDTO.setMimeType(eBook.getMimeType());
		//articleDTO.setFilename(eBook.getFilename());

    }
	
}
