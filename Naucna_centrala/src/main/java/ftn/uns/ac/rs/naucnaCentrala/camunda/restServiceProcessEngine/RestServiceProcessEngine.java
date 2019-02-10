package ftn.uns.ac.rs.naucnaCentrala.camunda.restServiceProcessEngine;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.CompleteTaskDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ftn.uns.ac.rs.naucnaCentrala.camunda.dto.FormDataDTO;
import ftn.uns.ac.rs.naucnaCentrala.camunda.dto.FormFieldDTO;
import ftn.uns.ac.rs.naucnaCentrala.camunda.utils.CamundaRestContrants;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.FormFieldsDto;

@Service
public class RestServiceProcessEngine {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	RuntimeService runtimeService;

	public String startProcess(String key, Map<String, VariableValueDto> variables) {
        StartProcessInstanceDto startProcessInstanceDto = new StartProcessInstanceDto();
        startProcessInstanceDto.setVariables(variables);
        System.out.println(String.format(CamundaRestContrants.ENGINE_BASE_URL + CamundaRestContrants.START_PROCESS, key));
        ProcessInstanceDto response = restTemplate.postForObject(String.format(CamundaRestContrants.ENGINE_BASE_URL + CamundaRestContrants.START_PROCESS, key), startProcessInstanceDto, ProcessInstanceDto.class);
        return response.getId();
    }
	
	public List<TaskDto> getTasks(String processId, String assignee) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(CamundaRestContrants.ENGINE_BASE_URL + CamundaRestContrants.TASK);

        if (processId != null) {
            builder.queryParam("processInstanceId", processId);
        }

        if (assignee != null) {
            builder.queryParam("assignee", assignee);
        }

        ParameterizedTypeReference<List<TaskDto>> returnType = new ParameterizedTypeReference<List<TaskDto>>() {
        };
        List<TaskDto> res = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, returnType).getBody();
        return res;
    }
	
	public FormDataDTO getTaskFormData(String taskId) {

        String url = String.format(CamundaRestContrants.ENGINE_BASE_URL + CamundaRestContrants.FORM_VARIABLES, taskId);

        ParameterizedTypeReference<Map<String, VariableValueDto>> returnType = new ParameterizedTypeReference<Map<String, VariableValueDto>>() {
        };
        Map<String, VariableValueDto> res = restTemplate.exchange(url, HttpMethod.GET, null, returnType).getBody();

        List<FormFieldDTO> fieldDtos = res.entrySet().stream().map(el -> new FormFieldDTO(el.getKey(), el.getValue())).collect(Collectors.toList());
        return new FormDataDTO(taskId, fieldDtos);
    }
	
	public void submitTaskForm(String taskId, List<FormFieldDTO> formFieldDtos) {
        System.out.println(taskId+"   taskid");

        String url = String.format(CamundaRestContrants.ENGINE_BASE_URL + CamundaRestContrants.SUBMIT_FORM, taskId);
        System.out.println(url+"   prosao");

        CompleteTaskDto completeTaskDto = new CompleteTaskDto();
        System.out.println(url+"   prosao 2");

        completeTaskDto.setVariables(formFieldDtos.stream().collect(Collectors.toMap(FormFieldDTO::getName, FormFieldDTO::getValue)));
        System.out.println(url+"   prosao 3");

        restTemplate.postForLocation(url, completeTaskDto);
        System.out.println(url+"   prosao 4");


    }
	
	public void createUser(UserDto userDto) {

        restTemplate.postForLocation(CamundaRestContrants.ENGINE_BASE_URL + CamundaRestContrants.CREATE_USER, userDto);
    }
	
	public TaskDto findTask(String taskId) {
        try {
            return restTemplate.getForObject(String.format("%s/task/%s", CamundaRestContrants.ENGINE_BASE_URL, taskId), TaskDto.class);
        } catch (Exception e) {
            return null;
        }
    }
	
	public void setVariable(String processInstanceId, String variableName, Object variableValue) {
        runtimeService.setVariable(processInstanceId, variableName, variableValue);
		//String url = String.format(CamundaRestContrants.ENGINE_BASE_URL + CamundaRestContrants.GET_VARIABLE, processInstanceId, key);
		//restTemplate.get
		//restTemplate.getForObject(url, VariableValueDto.class).getValue();
    }
	
	public Object getVariable(String processId, String key) {
        String url = String.format(CamundaRestContrants.ENGINE_BASE_URL + CamundaRestContrants.GET_VARIABLE, processId, key);

        return restTemplate.getForObject(url, VariableValueDto.class).getValue();
    }
}
