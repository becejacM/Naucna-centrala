package ftn.uns.ac.rs.naucnaCentrala.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	private String commentAuthor;
	private String suggestion;
	private String commentEditor;
	private String paperId;
	private String reviewer;
	
	public Review() {
		
	}

	public Review(Long id, String commentAuthor, String suggestion, String commentEditor, String paperId, String reviewer) {
		super();
		this.id = id;
		this.commentAuthor = commentAuthor;
		this.suggestion = suggestion;
		this.commentEditor = commentEditor;
		this.paperId = paperId;
		this.reviewer = reviewer;
	}
	public Review(String commentAuthor, String suggestion, String commentEditor, String paperId, String reviewer) {
		super();
		this.commentAuthor = commentAuthor;
		this.suggestion = suggestion;
		this.commentEditor = commentEditor;
		this.paperId = paperId;
		this.reviewer = reviewer;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommentAuthor() {
		return commentAuthor;
	}

	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getCommentEditor() {
		return commentEditor;
	}

	public void setCommentEditor(String commentEditor) {
		this.commentEditor = commentEditor;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
}
