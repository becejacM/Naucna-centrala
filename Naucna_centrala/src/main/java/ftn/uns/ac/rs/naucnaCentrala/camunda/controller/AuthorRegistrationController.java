package ftn.uns.ac.rs.naucnaCentrala.camunda.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.naucnaCentrala.camunda.dto.FormDataDTO;
import ftn.uns.ac.rs.naucnaCentrala.camunda.dto.FormFieldDTO;
import ftn.uns.ac.rs.naucnaCentrala.camunda.dto.RegistrationResponseDTO;
import ftn.uns.ac.rs.naucnaCentrala.camunda.restServiceProcessEngine.RestServiceProcessEngine;
import ftn.uns.ac.rs.naucnaCentrala.exceptions.MyNotFoundExeption;


@RestController
@RequestMapping("/camunda/register")
public class AuthorRegistrationController {

	@Autowired
	FormService formService;;
	
	@Autowired
	RestServiceProcessEngine rspe;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@GetMapping("/task/{username}")
    ResponseEntity getUserTasks(@PathVariable String username) {
		System.out.println("Trazim taskove za: "+username);
        final List<TaskDto> tasks = rspe.getTasks(null, username);
        if (tasks.size() > 0) {
            TaskDto nextTask = tasks.get(0);
            System.out.println("user ima taska sa id:   "+nextTask.getId());

            return new ResponseEntity<>(nextTask, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.OK);

    }
	@GetMapping()
    public ResponseEntity getRegisterForm() {
        String processId = rspe.startProcess("procesRegistracijeAutora", null);
        
        List<TaskDto> processTasks = rspe.getTasks(processId, null);
        TaskDto task = processTasks.stream().findFirst().get();
        FormDataDTO taskFormData = rspe.getTaskFormData(task.getId());

        return ResponseEntity.ok(taskFormData);

    }
	@GetMapping("/task/form/{taskId}")
    public ResponseEntity getTaskFormData(@PathVariable String taskId) {
        
        FormDataDTO taskFormData = rspe.getTaskFormData(taskId);

        return ResponseEntity.ok(taskFormData);

    }
	@PostMapping("{taskId}")
    public ResponseEntity<RegistrationResponseDTO> register(@PathVariable String taskId, @RequestBody List<FormFieldDTO> formFieldDtos) {
		System.out.println(taskId+"      id");
		TaskDto task = null;
        try {
            task = rspe.findTask(taskId);
        } catch (Exception e) {

        }
        if (task == null) {
        	System.out.println("Task ne postoji!");
            throw new MyNotFoundExeption("Task ne postoji!");
        }

        final String processInstanceId = task.getProcessInstanceId();
        /*final Map<String, Object> mapValues = formFieldDtos.stream().collect(Collectors.toMap(FormFieldDTO::getName, FormFieldDTO::getValue));
        *///rspe.setVariable(processInstanceId, "registracija", mapValues);

        //formService.submitTaskForm(taskId, mapValues);
		//try {
		rspe.submitTaskForm(taskId, formFieldDtos);
		String ret="";
		for (FormFieldDTO formFieldDTO : formFieldDtos) {
			if(formFieldDTO.getName().equals("username")) {
				ret=(String) formFieldDTO.getValue().getValue();
				//rspe.setVariable(processInstanceId, "author", formFieldDTO.getValue().getValue());
			}
		}
		//Object l = rspe.getVariable(processInstanceId, "username");
		//System.out.println(l.toString()+"         evo meeee");
		/*}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
		//rspe.submitTaskForm(taskId, formFieldDtos);

        return new ResponseEntity<RegistrationResponseDTO>(new RegistrationResponseDTO(ret), HttpStatus.OK);
    }

	@PutMapping("verify/{id}/{processInstanceId}")
    public ResponseEntity verifyAccount(@PathVariable long id, @PathVariable String processInstanceId) {
    	System.out.println("eve meeeeeeeeeeeeeeeeeee "+processInstanceId);
        //ProcessInstance instance = runtimeService.createProcessInstanceQuery()
          //      .processDefinitionKey(processInstanceId).list().get(0);
                //.singleResult();
        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(processInstanceId)
                .activityId("ReceiveTask_0hyxu4p")
                .singleResult();
        System.out.println("saljeeeeeeeeeeem "+execution.getId());

        Map<String, Object> vars = new HashMap<>();
        vars.put("userId", id);
        System.out.println("saljeeeeeeeeeeem "+processInstanceId);

        runtimeService.signal(execution.getId(), vars);
        System.out.println("zavrsio verify ");

        return new ResponseEntity(HttpStatus.OK);
    }
}
