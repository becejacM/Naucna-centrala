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
	private String username;
	private String no;
	private Collection<NaucnaOblast> naucnaOblast = new ArrayList<NaucnaOblast>();

	
	public ReviewerDTO() {
		
	}


	public ReviewerDTO(String username, String firstname, String lastname, String city, String state,
			Collection<NaucnaOblast> naucnaOblast) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.state = state;
		this.naucnaOblast = naucnaOblast;
	}

	public ReviewerDTO(String username, String firstname, String lastname, String city, String state,
			String naucnaOblast) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.state = state;
		this.no = naucnaOblast;
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


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}
	
	
	
	
}
