package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import ftn.uns.ac.rs.naucnaCentrala.model.Review;

public class ReviewAuthorResponse {

	private String commentAuthor;
	private String suggestion;
	
	public ReviewAuthorResponse() {
		
	}

	public ReviewAuthorResponse(String commentAuthor, String suggestion) {
		super();
		this.commentAuthor = commentAuthor;
		this.suggestion = suggestion;
	}

	public ReviewAuthorResponse(Review r) {
		this.suggestion = r.getSuggestion();
		this.commentAuthor = r.getCommentAuthor();
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
	
	
}
