package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import ftn.uns.ac.rs.naucnaCentrala.model.TransactionStatus;

public class TransactionResponseDto {

	private String redirectUrl;

	public TransactionResponseDto() {
		
	}

	public TransactionResponseDto(String redirectUrl) {
		super();
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public String toString() {
		return "TransactionResponseDto [redirectUrl=" + redirectUrl + "]";
	}
	
	
	
}
