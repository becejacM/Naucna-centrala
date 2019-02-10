package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import java.util.ArrayList;
import java.util.Collection;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.NaucnaOblast;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.ReviewerIndexUnit;

public class ReviewerDTO {

	private String id;
	private String firstname;
	private String lastname;
	private String city;
	private String state;
	private Collection<NaucnaOblast> naucnaOblast = new ArrayList<NaucnaOblast>();

	
	public ReviewerDTO() {
		
	}


	public ReviewerDTO(String id, String firstname, String lastname, String city, String state,
			Collection<NaucnaOblast> naucnaOblast) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.state = state;
		this.naucnaOblast = naucnaOblast;
	}

	public ReviewerDTO(ReviewerIndexUnit r) {
		this.id = r.getId();
		this.firstname = r.getFirstname();
		this.lastname = r.getLastname();
		this.city = r.getCity();
		this.state = r.getState();
		this.naucnaOblast = r.getNaucnaOblast();
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Collection<NaucnaOblast> getNaucnaOblast() {
		return naucnaOblast;
	}


	public void setNaucnaOblast(Collection<NaucnaOblast> naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}
	
	
	
}
