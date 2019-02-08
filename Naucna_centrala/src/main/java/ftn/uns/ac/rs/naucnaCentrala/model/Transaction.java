package ftn.uns.ac.rs.naucnaCentrala.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "naslovRada", nullable = true)
	private String naslovRada;
	
	@Column(name = "kupac", nullable = true)
	private String kupac;
	
	@Column(name = "status", nullable = true)
	private String status;
	
	public Transaction() {
		
	}

	public Transaction(Long id, String naslovRada, String kupac, String status) {
		super();
		this.id = id;
		this.naslovRada = naslovRada;
		this.kupac = kupac;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaslovRada() {
		return naslovRada;
	}

	public void setNaslovRada(String naslovRada) {
		this.naslovRada = naslovRada;
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
