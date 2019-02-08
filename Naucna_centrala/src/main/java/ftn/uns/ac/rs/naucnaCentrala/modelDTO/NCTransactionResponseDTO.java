package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class NCTransactionResponseDTO {

	private String answer;
	private Boolean jeKupljen;
	
	public NCTransactionResponseDTO() {
		
	}

	public NCTransactionResponseDTO(String answer, Boolean jeKupljen) {
		super();
		this.answer = answer;
		this.jeKupljen = jeKupljen;
	}

	public NCTransactionResponseDTO(String answer) {
		super();
		this.answer = answer;
	}
	public NCTransactionResponseDTO(Boolean jeKupljen) {
		super();
		this.jeKupljen = jeKupljen;
	}
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Boolean getJeKupljen() {
		return jeKupljen;
	}

	public void setJeKupljen(Boolean jeKupljen) {
		this.jeKupljen = jeKupljen;
	}
	
	
}
