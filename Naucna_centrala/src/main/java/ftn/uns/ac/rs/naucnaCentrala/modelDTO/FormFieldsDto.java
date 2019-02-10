package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

import java.util.List;

import org.camunda.bpm.engine.form.FormField;

public class FormFieldsDto {
	String taskId;
	List<FormField> formFields;
	String processInstanceId;
	String taskDefinitionId;

	public FormFieldsDto() {
		
	}

	
	public FormFieldsDto(String taskId, String processInstanceId,List<FormField> formFields, String taskDefinitionId) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
		this.taskDefinitionId = taskDefinitionId;
	}


	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<FormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskDefinitionId() {
		return taskDefinitionId;
	}

	public void setTaskDefinitionId(String taskDefinitionId) {
		this.taskDefinitionId = taskDefinitionId;
	}
	
	
	
}
