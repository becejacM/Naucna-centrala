package ftn.uns.ac.rs.naucnaCentrala.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "scientific_field")
public class ScientificField {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "scientific_field_name", nullable = false)
	private ScientificFieldName scientificFieldName;
	
	@ManyToMany(mappedBy = "scientificFields")
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<Magazine> magazine;
	
	@ManyToMany(mappedBy = "reviewerFields")
	@Fetch(value = FetchMode.SUBSELECT)
    private Collection<Reviewer> reviewerFields;
	
	@ManyToMany(mappedBy = "editorFields")
	@Fetch(value = FetchMode.SUBSELECT)
    private Collection<Editor> editorFields;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "scientificField")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Paper> articles;
	
	public ScientificField() {
		
	}

	public ScientificField(Long id, ScientificFieldName scientificFieldName, Collection<Magazine> magazine,
			Collection<Reviewer> reviewerFields, Collection<Editor> editorFields, List<Paper> articles) {
		super();
		this.id = id;
		this.scientificFieldName = scientificFieldName;
		this.magazine = magazine;
		this.reviewerFields = reviewerFields;
		this.editorFields = editorFields;
		this.articles = articles;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ScientificFieldName getScientificFieldName() {
		return scientificFieldName;
	}

	public void setScientificFieldName(ScientificFieldName scientificFieldName) {
		this.scientificFieldName = scientificFieldName;
	}

	public Collection<Magazine> getMagazine() {
		return magazine;
	}

	public void setMagazine(Collection<Magazine> magazine) {
		this.magazine = magazine;
	}

	public Collection<Reviewer> getReviewerFields() {
		return reviewerFields;
	}

	public void setReviewerFields(Collection<Reviewer> reviewerFields) {
		this.reviewerFields = reviewerFields;
	}

	public Collection<Editor> getEditorFields() {
		return editorFields;
	}

	public void setEditorFields(Collection<Editor> editorFields) {
		this.editorFields = editorFields;
	}

	public List<Paper> getArticles() {
		return articles;
	}

	public void setArticles(List<Paper> articles) {
		this.articles = articles;
	}

}
