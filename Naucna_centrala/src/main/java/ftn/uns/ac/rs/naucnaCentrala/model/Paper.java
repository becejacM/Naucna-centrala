package ftn.uns.ac.rs.naucnaCentrala.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import ftn.uns.ac.rs.naucnaCentrala.modelDTO.PaperDTO;

@Entity
@Table(name = "paper")
public class Paper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "name", nullable = true)
	private String name;
	
	@Column(name = "keywords", nullable = true)
	private String keywords;
	
	@Column(name = "apstract", nullable = true)
	private String apstract;
	
	@Column(name = "filename", nullable = true)
	private String filename;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "article_coauthor", joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
	private Collection<Coauthor> coauthors = new ArrayList<Coauthor>();
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private ScientificField scientificField;
	
	@Column(name = "firstVersionPath", nullable = true)
	private String firstVersionPath;	
	
	@Column(name = "finaltVersionPath", nullable = true)
	private String finalVersionPath;
	
	public Paper() {
		
	}

	public Paper(Long id, String name, String keywords, String apstract, String filename,
			Collection<Coauthor> coauthors, ScientificField scientificField, String firstVersionPath,
			String finalVersionPath) {
		super();
		this.id = id;
		this.name = name;
		this.keywords = keywords;
		this.apstract = apstract;
		this.filename = filename;
		this.coauthors = coauthors;
		this.scientificField = scientificField;
		this.firstVersionPath = firstVersionPath;
		this.finalVersionPath = finalVersionPath;
	}

	public Paper(PaperDTO paperdto) {
		this.id = paperdto.getId();
		this.name = paperdto.getName();
		this.keywords = paperdto.getKeywords();
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getApstract() {
		return apstract;
	}

	public void setApstract(String apstract) {
		this.apstract = apstract;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Collection<Coauthor> getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(Collection<Coauthor> coauthors) {
		this.coauthors = coauthors;
	}

	public ScientificField getScientificField() {
		return scientificField;
	}

	public void setScientificField(ScientificField scientificField) {
		this.scientificField = scientificField;
	}

	public String getFirstVersionPath() {
		return firstVersionPath;
	}

	public void setFirstVersionPath(String firstVersionPath) {
		this.firstVersionPath = firstVersionPath;
	}

	public String getFinalVersionPath() {
		return finalVersionPath;
	}

	public void setFinalVersionPath(String finalVersionPath) {
		this.finalVersionPath = finalVersionPath;
	}

	
}
