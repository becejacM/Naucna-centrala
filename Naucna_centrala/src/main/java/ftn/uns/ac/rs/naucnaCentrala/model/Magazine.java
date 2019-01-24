package ftn.uns.ac.rs.naucnaCentrala.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "magazine")
public class Magazine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "issn", nullable = false)
	private String issn;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "magazine_field", joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"))
	private Collection<ScientificField> scientificFields = new ArrayList<ScientificField>();
	
	@Column(name = "payment_method", nullable = false)
	private PaymentMethod paymentMethod;
	
	@OneToOne(cascade = CascadeType.ALL)
	private EditorialBoard editorialBoard;
	
	public Magazine() {
		
	}

	public Magazine(Long id, String name, String issn, Collection<ScientificField> scientificFields,
			PaymentMethod paymentMethod, EditorialBoard editorialBoard) {
		super();
		this.id = id;
		this.name = name;
		this.issn = issn;
		this.scientificFields = scientificFields;
		this.paymentMethod = paymentMethod;
		this.editorialBoard = editorialBoard;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	
	public Collection<ScientificField> getScientficFields() {
		return scientificFields;
	}

	public void setScientficFields(Collection<ScientificField> scientficFields) {
		this.scientificFields = scientficFields;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public EditorialBoard getEditorialBoard() {
		return editorialBoard;
	}

	public void setEditorialBoard(EditorialBoard editorialBoard) {
		this.editorialBoard = editorialBoard;
	}
	
	
	
}
