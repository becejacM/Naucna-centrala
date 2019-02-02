package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import java.util.List;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.SearchType;

public class SearchDTO {

    private String value;

    private List<String> fields;

    private SearchType type;

    private String field;

    public SearchDTO() {
    }

	public SearchDTO(String value, List<String> fields, SearchType type, String field) {
		super();
		this.value = value;
		this.fields = fields;
		this.type = type;
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public SearchType getType() {
		return type;
	}

	public void setType(SearchType type) {
		this.type = type;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

    
}
