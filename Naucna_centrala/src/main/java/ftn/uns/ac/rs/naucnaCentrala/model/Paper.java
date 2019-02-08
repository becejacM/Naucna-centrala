package ftn.uns.ac.rs.naucnaCentrala.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
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
	
	@Column(name = "naslovRada", nullable = true)
	private String naslovRada;
	
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
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private Magazine magazine;
	
	@Column(name="dostupnost")
	private String dostupnost;

	@Column(name="cena")
	private Long cena;
	public Paper() {
		
	}

	public Paper(PaperDTO paperdto) {
		this.id = paperdto.getId();
		this.naslovRada = paperdto.getName();
		this.keywords = paperdto.getKeywords();
	}
	
	public String getDostupnost() {
		return dostupnost;
	}

	public void setDostupnost(String dostupnost) {
		this.dostupnost = dostupnost;
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

	public Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
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

	public Long getCena() {
		return cena;
	}

	public void setCena(Long cena) {
		this.cena = cena;
	}

	
}
