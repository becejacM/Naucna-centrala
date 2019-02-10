package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(indexName = "naucnacentrala6reviewer", createIndex = false)
public class ReviewerIndexUnit {

	@Id
    @Field(type = FieldType.Keyword)
	private String id;
	
	private String username;

	private String password;

	private String email;

	private String firstname;

	private String lastname;

	private String city;

	private String state;
	
	private String title;
	
	private Collection<NaucnaOblast> naucnaOblast = new ArrayList<NaucnaOblast>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<NaucnaOblast> getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(Collection<NaucnaOblast> naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	
}
