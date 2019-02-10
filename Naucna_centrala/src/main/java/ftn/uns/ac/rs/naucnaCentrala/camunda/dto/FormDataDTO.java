package ftn.uns.ac.rs.naucnaCentrala.camunda.dto;

import java.util.List;

import org.camunda.bpm.engine.form.FormField;


public class FormDataDTO {

	private String taskId;

    private List<FormFieldDTO> formFields;
    
    public FormDataDTO() {
		// TODO Auto-generated constructor stub
	}

	public FormDataDTO(String taskId, List<FormFieldDTO> formFields) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<FormFieldDTO> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<FormFieldDTO> formFields) {
		this.formFields = formFields;
	}
    
    
    
    
}
