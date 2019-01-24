package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;

public class MagazineDTO {

	private String name;
	
	private String issn;
	
	private String paymentMethod;

	public MagazineDTO(String name, String issn, String paymentMethod) {
		super();
		this.name = name;
		this.issn = issn;
		this.paymentMethod = paymentMethod;
	}
	
	public MagazineDTO(Magazine m) {
		this.name = m.getName();
		this.issn = m.getIssn();
		this.paymentMethod = m.getPaymentMethod().toString();
	}
	
	public MagazineDTO() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}
