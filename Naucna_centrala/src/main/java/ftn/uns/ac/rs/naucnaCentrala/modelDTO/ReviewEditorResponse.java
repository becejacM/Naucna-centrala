package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import java.util.Collection;

import ftn.uns.ac.rs.naucnaCentrala.model.Review;

public class ReviewEditorResponse {

	private String commentEditor;
	private String suggestion;
	private String reviewer;
	
	public ReviewEditorResponse() {
		
	}

	public ReviewEditorResponse(String commentEditor, String suggestion, String reviewer) {
		super();
		this.commentEditor = commentEditor;
		this.suggestion = suggestion;
		this.reviewer = reviewer;
	}

	public ReviewEditorResponse(Review r) {
		this.commentEditor = r.getCommentEditor();
		this.suggestion = r.getSuggestion();
		this.reviewer = r.getReviewer();
	}
	public String getCommentEditor() {
		return commentEditor;
	}

	public void setCommentEditor(String commentEditor) {
		this.commentEditor = commentEditor;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	
}
