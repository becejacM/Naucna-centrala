package ftn.uns.ac.rs.naucnaCentrala.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Editor extends AppUser{
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private EditorialBoard editorialBoard;
	
	@Column(name = "title", nullable = true)
	private String title;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "editor_field", joinColumns = @JoinColumn(name = "editor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"))
	private Collection<ScientificField> editorFields = new ArrayList<ScientificField>();
	
	@Column(name = "isMain", nullable = true)
	private String isMain;
	
	public Editor() {
		super();
	}

	public Editor(EditorialBoard editorialBoard, String title, Collection<ScientificField> editorFields,
			String isMain) {
		super();
		this.editorialBoard = editorialBoard;
		this.title = title;
		this.editorFields = editorFields;
		this.isMain = isMain;
	}

	public EditorialBoard getEditorialBoard() {
		return editorialBoard;
	}

	public void setEditorialBoard(EditorialBoard editorialBoard) {
		this.editorialBoard = editorialBoard;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<ScientificField> getEditorFields() {
		return editorFields;
	}

	public void setEditorFields(Collection<ScientificField> editorFields) {
		this.editorFields = editorFields;
	}

	public String isMain() {
		return isMain;
	}

	public void setMain(String isMain) {
		this.isMain = isMain;
	}

	
	
}
