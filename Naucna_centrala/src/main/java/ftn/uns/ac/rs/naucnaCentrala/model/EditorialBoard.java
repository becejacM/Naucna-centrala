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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "editorial_board")
public class EditorialBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "editorialBoard")
	private Magazine magazine;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "editorialBoard")
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<Editor> editors;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "reviewer_magazine", joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "reviewer_id", referencedColumnName = "id"))
	private Collection<Reviewer> reviewers = new ArrayList<Reviewer>();
	
	public EditorialBoard() {
		
	}

	public EditorialBoard(Long id, Magazine magazine, Collection<Editor> editors,
			Collection<Reviewer> reviewers) {
		super();
		this.id = id;
		this.magazine = magazine;
		this.editors = editors;
		this.reviewers = reviewers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}


	public Collection<Editor> getEditors() {
		return editors;
	}

	public void setEditors(Collection<Editor> editors) {
		this.editors = editors;
	}

	public Collection<Reviewer> getReviewers() {
		return reviewers;
	}

	public void setReviewers(Collection<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}
	
	
}
