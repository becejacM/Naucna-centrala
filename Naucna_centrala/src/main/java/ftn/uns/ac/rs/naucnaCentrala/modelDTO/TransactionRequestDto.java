package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class TransactionRequestDto {

	private String sellerUuid;

	private Long amount;

	private String description;

	private String successUrl;

	private String failUrl;

	public TransactionRequestDto() {
		// TODO Auto-generated constructor stub
	}

	public TransactionRequestDto(String sellerUuid, Long amount, String description, String successUrl,
			String failUrl) {
		super();
		this.sellerUuid = sellerUuid;
		this.amount = amount;
		this.description = description;
		this.successUrl = successUrl;
		this.failUrl = failUrl;
	}

	public String getSellerUuid() {
		return sellerUuid;
	}

	public void setSellerUuid(String sellerUuid) {
		this.sellerUuid = sellerUuid;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getFailUrl() {
		return failUrl;
	}

	public void setFailUrl(String failUrl) {
		this.failUrl = failUrl;
	}

	@Override
	public String toString() {
		return "TransactionRequestDto [sellerUuid=" + sellerUuid + ", amount=" + amount + ", description=" + description
				+ ", successUrl=" + successUrl + ", failUrl=" + failUrl + "]";
	}

}
