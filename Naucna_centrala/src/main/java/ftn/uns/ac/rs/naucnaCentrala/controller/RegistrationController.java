package ftn.uns.ac.rs.naucnaCentrala.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.FormFieldsDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.RegistrationDetailsDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TaskDto;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormProperty;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.cmd.GetDeploymentResourceNamesCmd;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {


	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
    
    

    @GetMapping
    public @ResponseBody FormFieldsDto startRegistrationProcess() {
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Registracija");
		runtimeService.setVariable(pi.getProcessInstanceId(), "processInstanceId", true);

		//runtimeService.setVariable("processInstanceId", pi.getProcessInstanceId(), pi);
        //kreiran je proces prvi task se uzima i njegova polja
		System.out.println(pi.getProcessInstanceId());
		System.out.println(pi.getId());
		System.out.println(taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).list().size());
		Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).list().get(0);
		System.out.println(task.getId());

		TaskFormData formData = formService.getTaskFormData(task.getId());

        TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDto(task.getId(), pi.getProcessInstanceId(), properties, task.getTaskDefinitionKey());
    }

    @PostMapping
    public ResponseEntity register(@RequestBody RegistrationDetailsDTO registrationDetails) {
    	System.out.println(registrationDetails.getProcessInstanceId());
		//ProcessInstance pi = runtimeService.createProcessInstanceQuery()
          //      .processDefinitionKey(registrationDetails.getProcessInstanceId()).list().get(0);
                //.singleResult();
		Task task = taskService.createTaskQuery().processInstanceId(registrationDetails.getProcessInstanceId()).list().get(0);

        System.out.println("submitovani podaci");
        //formService.submitTaskFormData(arg0, arg1);
        formService.submitTaskFormData(task.getId(), convertToMap(registrationDetails));
        System.out.println("submitovani podaci zavrseno");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("verify/{id}/{processInstanceId}")
    public ResponseEntity verifyAccount(@PathVariable long id, @PathVariable String processInstanceId) {
    	System.out.println("eve meeeeeeeeeeeeeeeeeee "+processInstanceId);
        //ProcessInstance instance = runtimeService.createProcessInstanceQuery()
          //      .processDefinitionKey(processInstanceId).list().get(0);
                //.singleResult();
        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(processInstanceId)
                .activityId("Task_0ldaz4h")
                .singleResult();
        System.out.println("saljeeeeeeeeeeem "+execution.getId());

        Map<String, Object> vars = new HashMap<>();
        vars.put("userId", id);
        System.out.println("saljeeeeeeeeeeem "+processInstanceId);

        runtimeService.signal(execution.getId(), vars);
        System.out.println("zavrsio verify ");

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("task")
    public ResponseEntity getUserTasks(@RequestParam("username") String username) {
        System.out.println("getujem user task ");

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(username)
                .list();

        if (tasks.size() > 0) {
            Task nextTask = tasks.get(0);
            System.out.println("user ima taska sa id:   "+nextTask.getId());

            TaskDto t = new TaskDto(nextTask.getId(), nextTask.getName(), username);
            return new ResponseEntity<>(t, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("task/{id}")
    public @ResponseBody FormFieldsDto getTaskData(@PathVariable String id) {
        System.out.println("getujem taska sa id:   "+id);

        TaskFormData formData = formService.getTaskFormData(id);
        List<FormField> props = formData.getFormFields();
		Task task = taskService.createTaskQuery().taskId(id).singleResult();

        return new FormFieldsDto(id, task.getProcessInstanceId(), props, task.getTaskDefinitionKey());
    }

    @PutMapping("task/execute/{id}")
    public ResponseEntity executeTask(@PathVariable String id,
            @RequestBody Map<String, String> data) {
        System.out.println("radim task sa id:   "+id);
        formService.submitTaskFormData(id, data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Map<String, String> convertToMap(RegistrationDetailsDTO details) {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.convertValue(details, Map.class);
        return map;
    }
}

