package ftn.uns.ac.rs.naucnaCentrala.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Reviewer extends AppUser{
	
	@ManyToMany(mappedBy = "reviewers")
    private Collection<EditorialBoard> magazines;

	@Column(name = "title", nullable = true)
	private String title;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reviewer_field", joinColumns = @JoinColumn(name = "reviewer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"))
	private Collection<ScientificField> reviewerFields = new ArrayList<ScientificField>();
	
	public Reviewer() {
		
	}
	
	public Reviewer(Collection<EditorialBoard> magazines, String title, Collection<ScientificField> reviewerFields) {
		super();
		this.magazines = magazines;
		this.title = title;
		this.reviewerFields = reviewerFields;
	}

	public Collection<EditorialBoard> getMagazines() {
		return magazines;
	}

	public void setMagazines(Collection<EditorialBoard> magazines) {
		this.magazines = magazines;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<ScientificField> getReviewerFields() {
		return reviewerFields;
	}

	public void setReviewerFields(Collection<ScientificField> reviewerFields) {
		this.reviewerFields = reviewerFields;
	}
	
	
}
