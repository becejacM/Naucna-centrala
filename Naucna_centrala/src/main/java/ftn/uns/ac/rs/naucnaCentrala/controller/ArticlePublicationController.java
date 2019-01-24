package ftn.uns.ac.rs.naucnaCentrala.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.FormFieldsDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDetailsDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.ProccessMagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.RegistrationDetailsDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.TaskDto;
import ftn.uns.ac.rs.naucnaCentrala.security.TokenUtils;
import ftn.uns.ac.rs.naucnaCentrala.service.MagazineService;

@RestController
@RequestMapping("/publication")
public class ArticlePublicationController {


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
	
	@Autowired
	TokenUtils tokenUtils;
    
	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Autowired
	MagazineService magazineService;
	
    @GetMapping
    public @ResponseBody ProccessMagazineDTO startProcess() {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		System.out.println(username);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("ObjavljivanjeRada");
		runtimeService.setVariable(pi.getProcessInstanceId(), "processInstanceId", true);

		System.out.println(pi.getProcessInstanceId());
		System.out.println(pi.getId());
		System.out.println(taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).list().size());
		Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).list().get(0);
		System.out.println(task.getId());

		Collection<MagazineDTO> magazines = magazineService.getAll(username);
		ProccessMagazineDTO pmd = new ProccessMagazineDTO(magazines, pi.getProcessInstanceId());
        return pmd;
    }

    @PostMapping("chooseMagazine")
    public ResponseEntity chooseMagazine(@RequestBody MagazineDetailsDTO magazineDetails) {
    	System.out.println(magazineDetails.getProcessInstanceId());
		Task task = taskService.createTaskQuery().processInstanceId(magazineDetails.getProcessInstanceId()).list().get(0);

        System.out.println("submitovani podaci");
        //formService.submitTaskFormData(arg0, arg1);
        formService.submitTaskFormData(task.getId(), convertToMap(magazineDetails));
        System.out.println("submitovani podaci zavrseno");

        return new ResponseEntity<>(HttpStatus.OK);
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

        return new FormFieldsDto(id, task.getProcessInstanceId(), props);
    }

    @PutMapping("task/execute/{id}")
    public ResponseEntity executeTask(@PathVariable String id,
            @RequestBody Map<String, String> data) {
        System.out.println("radim task sa id:   "+id);
        formService.submitTaskFormData(id, data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Map<String, String> convertToMap(MagazineDetailsDTO details) {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.convertValue(details, Map.class);
        return map;
    }
}
