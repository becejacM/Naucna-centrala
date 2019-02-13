package ftn.uns.ac.rs.naucnaCentrala.camunda.controller;

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

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ftn.uns.ac.rs.naucnaCentrala.camunda.dto.FormFieldDTO;
import ftn.uns.ac.rs.naucnaCentrala.camunda.dto.MagazineResponseDTO;
import ftn.uns.ac.rs.naucnaCentrala.camunda.restServiceProcessEngine.RestServiceProcessEngine;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler.PdfDocumentHandler;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.StorageProperties;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service.ESPaperService;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.model.Subscription;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.AnswerDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDetailsDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.PaperDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.ProccessMagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.repository.SubscriptionRepository;
import ftn.uns.ac.rs.naucnaCentrala.security.TokenUtils;
import ftn.uns.ac.rs.naucnaCentrala.service.MagazineService;

@RestController
@RequestMapping("/camunda/publish")
public class PublishPaperController {

	@Autowired
	RestServiceProcessEngine rspe;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	MagazineService magazineService;
	
	@Autowired
	SubscriptionRepository subRep;
	
	@Autowired
	ESPaperService esPaperService;
	
	@Autowired
	PdfDocumentHandler pdfDH;
	
    Path dirTemporaryLocation;

    @Autowired
    public PublishPaperController(StorageProperties properties) {
        this.dirTemporaryLocation = Paths.get(properties.getTemporarylocation());
	}
    
	@GetMapping("startPublishProccess")
	public ResponseEntity startPaperSubmissionProcess() {
		//novo, pokretanje procesa
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		System.out.println("zapocinjem proces objave rada za korisnika: " + username);
		Map<String, VariableValueDto> variables = new HashMap<>();
		VariableValueDto variableValueDto = new VariableValueDto();
		variableValueDto.setValue(username);
		variables.put("author", variableValueDto);
		String processId = rspe.startProcess("procesObjaveRada", variables);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/userTasks")
    ResponseEntity getUserTasks() {
		//novo
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		System.out.println("Trazim taskove za: "+username);
        final List<TaskDto> tasks = rspe.getTasks(null, username);
		System.out.println("User ima : "+tasks.size()+ " taskova");


        return ResponseEntity.ok(tasks);

    }
	
	@GetMapping("task/{taskId}")
    public ResponseEntity getByTaskId(@PathVariable String taskId) {
		//novo
        TaskDto task = rspe.findTask(taskId);
        String magazine = (String) rspe.getVariable(task.getProcessInstanceId(), "magazine");
        System.out.println("ispisujem task id: "+task.getId());
        return ResponseEntity.ok(new MagazineResponseDTO(magazine));
    }
	
	@GetMapping("task/{taskId}/paperInfo")
    public ResponseEntity getPaperInfoByTaskId(@PathVariable String taskId) {
		//novo
        TaskDto task = rspe.findTask(taskId);
        String naslovRada = (String) rspe.getVariable(task.getProcessInstanceId(), "naslov");
        String apstrakt = (String) rspe.getVariable(task.getProcessInstanceId(), "apstrakt");
        String keywords = (String) rspe.getVariable(task.getProcessInstanceId(), "kljucneReci");

        System.out.println("ispisujem task id: "+task.getId());
        return ResponseEntity.ok(new PaperDTO(naslovRada, apstrakt, keywords));
    }
	
	@GetMapping("task/{taskId}/paperPDF")
    public ResponseEntity getPaperPDFByTaskId(@PathVariable String taskId) {
		//novo
        TaskDto task = rspe.findTask(taskId);
        String filename = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");
        Path filepath = dirTemporaryLocation.resolve(filename);
        File bookFile = filepath.toFile();
        String rad = pdfDH.getText(bookFile);
        System.out.println("ispisujem task id: "+task.getId());
        return ResponseEntity.ok(new PaperDTO(rad, filename));
    }
	
	@GetMapping("subscribe/{taskId}")
    public ResponseEntity subscribe(@PathVariable String taskId) {
		//novo
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
        TaskDto task = rspe.findTask(taskId);
        System.out.println("ispisujem task id iz subscribe: "+task.getId());

        String magazine = (String) rspe.getVariable(task.getProcessInstanceId(), "magazine");

		ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
        Subscription sub = new Subscription();
        sub.setKupac(username);
        sub.setNazivCasopisa(magazine);
        sub.setStatus("SUCCESS");
        subRep.save(sub);
        rspe.submitTaskForm(task.getId(), taskFormFieldDtos);

        return ResponseEntity.noContent().build();
    }
	
	@GetMapping()
	public @ResponseBody ProccessMagazineDTO getMagazines() {
		// novo, dobavljanje casopisa
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		System.out.println("dobavljam casopise za korisnika: " + username);
		Collection<MagazineDTO> magazines = magazineService.getAll(username);
		ProccessMagazineDTO pmd = new ProccessMagazineDTO(magazines);
		return pmd;

	}

	@PostMapping("chooseMagazine/{taskId}")
	public ResponseEntity chooseMagazine(@PathVariable String taskId, @RequestBody MagazineDetailsDTO magazineDetails) {
		// novo
		System.out.println(magazineDetails.getProcessInstanceId());
		System.out.println("submitovani podaci za odabir casopisa");
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		taskFormFieldDtos.add(new FormFieldDTO("magazine", magazineDetails.getName()));
		rspe.submitTaskForm(taskId, taskFormFieldDtos);
		System.out.println("submitovani podaci zavrseno evo meee");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("submit/{taskId}")
    public ResponseEntity submit(@PathVariable String taskId, @RequestBody PaperDTO paperDTO) {
		//novo, submitovani potrebni podaci o radu
		System.out.println("Dodajem rad: " + paperDTO.toString()+ " za task sa idom: "+taskId);
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		taskFormFieldDtos.add(new FormFieldDTO("naslov", paperDTO.getNaslovRada()));
		taskFormFieldDtos.add(new FormFieldDTO("apstrakt", paperDTO.getApstrakt()));
		taskFormFieldDtos.add(new FormFieldDTO("kljucneReci", paperDTO.getKeywords()));
		taskFormFieldDtos.add(new FormFieldDTO("nsucnaOblast", paperDTO.getNaucnaOblast()));
		taskFormFieldDtos.add(new FormFieldDTO("autori", paperDTO.getAutori()));
		taskFormFieldDtos.add(new FormFieldDTO("filename", paperDTO.getRad()));
        
		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		System.out.println("Submitovani podaci za rad dosao dovde");
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("resubmit/{taskId}")
    public ResponseEntity resubmit(@PathVariable String taskId, @RequestBody PaperDTO paperDTO) {
		//novo, submitovani potrebni podaci o radu
		System.out.println("Dodajem rad: " + paperDTO.toString()+ " za task sa idom: "+taskId);
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		
		taskFormFieldDtos.add(new FormFieldDTO("filename", paperDTO.getRad()));
        
		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		System.out.println("Submitovani podaci za rad dosao dovde resubmit");
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("temporaryupload")
    public ResponseEntity uploadTemporary(@RequestParam("file")MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();

        Paper paper = null;
        File storedFile = esPaperService.storeFileTemporary(multipartFile);
        paper = esPaperService.getMetadata(storedFile);
        paper.setFilename(originalFileName);
        return new ResponseEntity<>(paper, HttpStatus.OK);
    }
	
	@PostMapping("tematicAnswer/{taskId}")
    public ResponseEntity tematicAnswer(@PathVariable String taskId, @RequestBody AnswerDTO answer) throws IOException {
		//novo, submitovani potrebni podaci o radu
		System.out.println(" rad tematski prikladan: " + answer.getAnswer()+ " za task sa idom: "+taskId);
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		if(answer.getAnswer().equals("yes")) {
			taskFormFieldDtos.add(new FormFieldDTO("radPrikladan", true));
		}
		else {
			taskFormFieldDtos.add(new FormFieldDTO("radPrikladan", false));
		}
		

		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		System.out.println("Submitovani podaci za rad dosao dovde tematic");
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("formatAnswer/{taskId}")
    public ResponseEntity formatAnswer(@PathVariable String taskId, @RequestBody AnswerDTO answer) throws IOException {
		//novo, submitovani potrebni podaci o radu
		System.out.println(" rad tematski prikladan: " + answer.getAnswer()+ " za task sa idom: "+taskId);
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		TaskDto task = rspe.findTask(taskId);
        System.out.println("ispisujem task id iz subscribe: "+task.getId());

        String filename = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");
		if(answer.getAnswer().equals("yes")) {
			taskFormFieldDtos.add(new FormFieldDTO("radFormatiran", true));
		}
		else {
			taskFormFieldDtos.add(new FormFieldDTO("radFormatiran", false));
			taskFormFieldDtos.add(new FormFieldDTO("poruka", answer.getAnswer()));
	        Files.deleteIfExists(dirTemporaryLocation.resolve(filename));

		}
		

		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		System.out.println("Submitovani podaci za rad dosao dovde format");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
