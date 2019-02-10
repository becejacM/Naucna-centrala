package ftn.uns.ac.rs.naucnaCentrala.camunda.dto;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;

public class FormFieldDTO {

	private String name;

    private VariableValueDto value;
    
    private FormFieldDTO() {
    	
    }
    
    public FormFieldDTO(String name, Object value) {
        this.name = name;
        final VariableValueDto variableValueDto = new VariableValueDto();
        variableValueDto.setValue(value);
        this.value = variableValueDto;
    }

	public FormFieldDTO(String name, VariableValueDto value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VariableValueDto getValue() {
		return value;
	}

	public void setValue(VariableValueDto value) {
		this.value = value;
	}
    
    
}
