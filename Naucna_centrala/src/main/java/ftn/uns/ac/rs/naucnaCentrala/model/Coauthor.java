package ftn.uns.ac.rs.naucnaCentrala.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "article")
public class Coauthor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "firstname", nullable = true)
	private String firstname;
	
	@Column(name = "email", nullable = true)
	private String email;
	
	@Column(name = "city", nullable = true)
	private String city;
	
	@Column(name = "state", nullable = true)
	private String state;
	
	@ManyToMany(mappedBy = "coauthors")
	@Fetch(value = FetchMode.SUBSELECT)
    private Collection<Paper> articles;
	
	public Coauthor() {
		
	}

	public Coauthor(Long id, String firstname, String email, String city, String state, Collection<Paper> articles) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.email = email;
		this.city = city;
		this.state = state;
		this.articles = articles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Collection<Paper> getArticles() {
		return articles;
	}

	public void setArticles(Collection<Paper> articles) {
		this.articles = articles;
	}

	
	
	
}
