package ftn.uns.ac.rs.naucnaCentrala.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.indexing.Indexer;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.StorageProperties;
import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.FormFieldsDto;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDetailsDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MessageDTO;
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
	
	@Autowired
	private Indexer indexer;
	
	@Autowired
	StorageProperties properties;
	
    @GetMapping
    public @ResponseBody ProccessMagazineDTO startProcess() {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		System.out.println("evo usernameeeee: "  + username);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("ObjavljivanjeRada");
		runtimeService.setVariable(pi.getProcessInstanceId(), "processInstanceId", true);
        identityService.setAuthenticatedUserId(username);
        System.out.println(identityService.getCurrentAuthentication().getUserId() + "        evo******************");
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
    /*@GetMapping("task")
    public ResponseEntity getUserTasks(@RequestParam("username") String username) {
        System.out.println("getujem user task ");

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(username)
                .list();
        List<TaskDto> tasksDTOs = new ArrayList<TaskDto>();
        if (tasks.size() > 0) {
            Task nextTask = tasks.get(0);
            System.out.println("user ima taska sa id:   "+nextTask.getId());

            TaskDto t = new TaskDto(nextTask.getId(), nextTask.getName(), username);
            tasksDTOs.add(t);
            //return new ResponseEntity<>(t, HttpStatus.OK);
        }

        return new ResponseEntity(tasksDTOs, HttpStatus.OK);
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
    }*/

    @GetMapping("tasks")
    public ResponseEntity getTasks() {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		System.out.println("Getujem taskove za usera: " + username);
        List<Task> userTasks = taskService.createTaskQuery()
                .taskAssignee(username)
                .list();
        System.out.println(userTasks.size());
        //System.out.println(identityService.getCurrentAuthentication().getUserId() + " 2.put evo******************");
        List<TaskDto> finalTasks = new ArrayList<TaskDto>();
        for (Task t : userTasks) {
        	System.out.println(t.toString());
			finalTasks.add(new TaskDto(t.getId(), t.getName(), t.getAssignee()));
		}
        /*List<TaskDto> finalTasks = userTasks
                .stream()
                .map(t -> new TaskDto(t.getId(), t.getName(), t.getAssignee()))
                .collect(Collectors.toList());*/
        System.out.println(finalTasks.size());
        return new ResponseEntity<>(finalTasks, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("tasks/{id}")
    public ResponseEntity getTask(@PathVariable String id) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));

        if (!canExecute(id, username)) {
            return new ResponseEntity<>(new MessageDTO("Ne možete izvršiti ovaj zadatak"), HttpStatus.FORBIDDEN);
        }

        TaskFormData formData = formService.getTaskFormData(id);
        List<FormField> props = formData.getFormFields();
		Task task = taskService.createTaskQuery().taskId(id).singleResult();

		System.out.println(task.getTaskDefinitionKey()+"  "+task.getExecutionId());
		FormFieldsDto customTask =  new FormFieldsDto(id, task.getProcessInstanceId(), props, task.getTaskDefinitionKey());
        return new ResponseEntity<>(customTask, HttpStatus.OK);
    }

    @PostMapping("tasks/{id}")
    public ResponseEntity executeTask(@PathVariable String id,
                                      @RequestBody Map<String, String> params) {
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		System.out.println(params);

        if (canExecute(id, username)) {
            //if (formService.getTaskFormData(id).getFormProperties().size() == 0) {
              //  taskService.complete(id);
            //} else {
        	for (String s : params.values()) {
				if(s.equals("") || s.equals(null)) {
	        		System.out.println("parameteriiii " + s);
			        return new ResponseEntity<>(new MessageDTO("Ne možete izvršiti ovaj zadatak"), HttpStatus.FORBIDDEN);
				}
			}
            	System.out.println("submitujem podatke");
                formService.submitTaskFormData(id, params);
        	
            //}
            return new ResponseEntity<>(new MessageDTO("Uspešno završen zadatak"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Ne možete izvršiti ovaj zadatak"), HttpStatus.FORBIDDEN);
    }

    @GetMapping("tasks/{id}/claim")
    public ResponseEntity claimTask(@PathVariable String id) {
        return new ResponseEntity(HttpStatus.OK);
    }

    public boolean canExecute(String taskId, String username) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(username)
                .list();

        return tasks.stream().anyMatch(t -> t.getId().equals(taskId));
    }

    private boolean canClaim(String taskId, String username) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateUser(username)
                .list();

        return tasks.stream().anyMatch(t -> t.getId().equals(taskId));
    }
    private Map<String, String> convertToMap(MagazineDetailsDTO details) {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.convertValue(details, Map.class);
        return map;
    }
    
	/*@RequestMapping(
			value = "/upload",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<PaperIndexUnit> upload(@RequestParam(value = "file") MultipartFile file) {

		if(!file.getContentType().equals("application/pdf")){
			return new ResponseEntity<PaperIndexUnit>(HttpStatus.FORBIDDEN);
		}
		
		PaperIndexUnit indexUnit = indexUploadedFile(file);
		
		return new ResponseEntity<PaperIndexUnit>(indexUnit, HttpStatus.OK);

	}
	
	private PaperIndexUnit indexUploadedFile(MultipartFile file) {

		String fileName = saveUploadedFile(file);
		if(fileName != null){
			
			PaperIndexUnit indexUnit = indexer.getHandler(fileName).getIndexUnit(new File(fileName));
			indexer.add(indexUnit);
			
			return indexUnit;
		}
		return null;
	}
	
	private String saveUploadedFile(MultipartFile file) {
		String retVal = null;
		if (! file.isEmpty()) {
			byte[] bytes;
			try {
				bytes = file.getBytes();
				Path path = Paths.get(getResourceFilePath(dataDirPath).getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.write(path, bytes);
				retVal = path.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return retVal;
	}*/



}
