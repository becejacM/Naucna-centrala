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

@Entity
@Table(name = "article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "keywords", nullable = false)
	private String keywords;
	
	@Column(name = "apstract", nullable = false)
	private String apstract;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "article_coauthor", joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
	private Collection<Coauthor> coauthors = new ArrayList<Coauthor>();
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private ScientificField scientificField;
	
	@Column(name = "firstVersionPath", nullable = false)
	private String firstVersionPath;	
	
	@Column(name = "finaltVersionPath", nullable = false)
	private String finalVersionPath;
	
	public Article() {
		
	}

	public Article(Long id, String name, String keywords, String apstract, Collection<Coauthor> coauthors,
			ScientificField scientificField, String firstVersionPath, String finalVersionPath) {
		super();
		this.id = id;
		this.name = name;
		this.keywords = keywords;
		this.apstract = apstract;
		this.coauthors = coauthors;
		this.scientificField = scientificField;
		this.firstVersionPath = firstVersionPath;
		this.finalVersionPath = finalVersionPath;
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
