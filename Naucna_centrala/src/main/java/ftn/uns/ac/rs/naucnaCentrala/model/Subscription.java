package ftn.uns.ac.rs.naucnaCentrala.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subscription")
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "nazivCasopisa", nullable = true)
	private String nazivCasopisa;
	
	@Column(name = "kupac", nullable = true)
	private String kupac;
	
	@Column(name = "status", nullable = true)
	private String status;
	
	public Subscription() {
		
	}

	public Subscription(Long id, String nazivCasopisa, String kupac, String status) {
		super();
		this.id = id;
		this.nazivCasopisa = nazivCasopisa;
		this.kupac = kupac;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazivCasopisa() {
		return nazivCasopisa;
	}

	public void setNazivCasopisa(String nazivCasopisa) {
		this.nazivCasopisa = nazivCasopisa;
	}

	public String getKupac() {
		return kupac;
	}

	public void setKupac(String kupac) {
		this.kupac = kupac;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
