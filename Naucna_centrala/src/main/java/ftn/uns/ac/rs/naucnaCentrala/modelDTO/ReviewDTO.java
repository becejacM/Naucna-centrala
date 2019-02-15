package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class ReviewDTO {
	
	private String commentAuthor;
	private String suggestion;
	private String commentEditor;
	private String odluka;
	private String newReviewers;
	
	public ReviewDTO() {
		
	}

	public ReviewDTO(String commentAuthor, String suggestion, String commentEditor) {
		super();
		this.commentAuthor = commentAuthor;
		this.suggestion = suggestion;
		this.commentEditor = commentEditor;
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

	public String getOdluka() {
		return odluka;
	}

	public void setOdluka(String odluka) {
		this.odluka = odluka;
	}

	public String getNewReviewers() {
		return newReviewers;
	}

	public void setNewReviewers(String newReviewers) {
		this.newReviewers = newReviewers;
	}
	
	
	
}
