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
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;

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
import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;
import ftn.uns.ac.rs.naucnaCentrala.model.Review;
import ftn.uns.ac.rs.naucnaCentrala.model.Subscription;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.AnswerDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.MagazineDetailsDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.PaperDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.ProccessMagazineDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.ReviewAuthorResponse;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.ReviewDTO;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.ReviewEditorResponse;
import ftn.uns.ac.rs.naucnaCentrala.modelDTO.ReviewerDTO;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.SubscriptionRepository;
import ftn.uns.ac.rs.naucnaCentrala.security.TokenUtils;
import ftn.uns.ac.rs.naucnaCentrala.service.MagazineService;
import ftn.uns.ac.rs.naucnaCentrala.service.ReviewService;
import ftn.uns.ac.rs.naucnaCentrala.taskService.AppTaskService;

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
	ReviewService reviewService;

	@Autowired
	SubscriptionRepository subRep;

	@Autowired
	ESPaperService esPaperService;

	@Autowired
	PdfDocumentHandler pdfDH;

	Path dirTemporaryLocation;

	@Autowired
	AppTaskService appTaskService;

	@Autowired
	AppUserRepository userRepository;

	@Autowired
	public PublishPaperController(StorageProperties properties) {
		this.dirTemporaryLocation = Paths.get(properties.getTemporarylocation());
	}

	@GetMapping("startPublishProccess")
	public ResponseEntity startPaperSubmissionProcess() {
		//OK novo, pokretanje procesa 
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
		//OK novo, getovanje taskova
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		System.out.println("Trazim taskove za: " + username);
		final List<TaskDto> tasks = rspe.getTasks(null, username);
		System.out.println("User ima : " + tasks.size() + " taskova");

		return ResponseEntity.ok(tasks);

	}

	@GetMapping("task/{taskId}")
	public ResponseEntity getByTaskId(@PathVariable String taskId) {
		//OK novo
		TaskDto task = rspe.findTask(taskId);
		String magazine = (String) rspe.getVariable(task.getProcessInstanceId(), "magazine");
		System.out.println("ispisujem task id: " + task.getId());
		return ResponseEntity.ok(new MagazineResponseDTO(magazine));
	}

	@GetMapping("task/{taskId}/paperInfo")
	public ResponseEntity getPaperInfoByTaskId(@PathVariable String taskId) {
		// novo
		TaskDto task = rspe.findTask(taskId);
		String naslovRada = (String) rspe.getVariable(task.getProcessInstanceId(), "naslov");
		String apstrakt = (String) rspe.getVariable(task.getProcessInstanceId(), "apstrakt");
		String keywords = (String) rspe.getVariable(task.getProcessInstanceId(), "kljucneReci");

		System.out.println("ispisujem task id: " + task.getId());
		return ResponseEntity.ok(new PaperDTO(naslovRada, apstrakt, keywords));
	}

	@GetMapping("task/{taskId}/allReviewers")
	public ResponseEntity<Collection<ReviewerDTO>> getAllReviewers(@PathVariable String taskId) {
		// OK, novo, getuje sve revizore
		TaskDto task = rspe.findTask(taskId);
		String magazine = (String) rspe.getVariable(task.getProcessInstanceId(), "magazine");
		String paper = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");

		Collection<ReviewerDTO> reviewers = appTaskService.getAllReviewers(magazine);
		Collection<ReviewerDTO> reviewersData = new ArrayList<ReviewerDTO>();

		Collection<Review> reviews = reviewService.getByPaper(paper);
		for(ReviewerDTO rew : reviewers) {
			boolean flag = false;
			for(Review review : reviews) {
				System.out.println("evo me odje6666666666666666666666666666666 "+rew.getUsername()+"   "+review.getReviewer());
				if(rew.getUsername().equals(review.getReviewer())) {
					System.out.println("evo me odje6666666666666666666666666666666");
					flag = true;
				}
			}
			
			if(!flag && !reviewersData.contains(rew)) {
				
				reviewersData.add(rew);
			}
		}
		System.out.println("ispisujem task id: " + task.getId());
		return ResponseEntity.ok(reviewersData);
	}

	@GetMapping("task/{taskId}/reviewersBySF")
	public ResponseEntity<Collection<ReviewerDTO>> getReviewersBySF(@PathVariable String taskId) {
		// OK, novo, getuje samo one revizore sa prosledjenom naucnom oblascu
		TaskDto task = rspe.findTask(taskId);
		String magazine = (String) rspe.getVariable(task.getProcessInstanceId(), "magazine");
		String naucnaOblast = (String) rspe.getVariable(task.getProcessInstanceId(), "nsucnaOblast");
		String paper = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");

		Collection<ReviewerDTO> reviewers = appTaskService.getAllReviewersBySF(magazine, naucnaOblast);
		Collection<ReviewerDTO> reviewersData = new ArrayList<ReviewerDTO>();

		Collection<Review> reviews = reviewService.getByPaper(paper);
		for(ReviewerDTO rew : reviewers) {
			boolean flag = false;
			for(Review review : reviews) {
				System.out.println("evo me odje6666666666666666666666666666666 "+rew.getUsername()+"   "+review.getReviewer());
				if(rew.getUsername().equals(review.getReviewer())) {
					System.out.println("evo me odje6666666666666666666666666666666");
					flag = true;
				}
			}
			
			if(!flag && !reviewersData.contains(rew)) {
				
				reviewersData.add(rew);
			}
		}
		System.out.println("ispisujem task id: " + task.getId());
		return ResponseEntity.ok(reviewersData);
	}

	@PostMapping("task/{taskId}/addReviewers")
	public ResponseEntity addReviewers(@PathVariable String taskId, @RequestBody List<ReviewerDTO> reviewerDtos,
			@RequestParam String date) {
		final TaskDto task = rspe.findTask(taskId);
		String magazine = (String) rspe.getVariable(task.getProcessInstanceId(), "magazine");

		/*
		 * final Paper paperEntity = paperService.findById(Long.parseLong(paperId));
		 * 
		 * if (!paperEntity.getReviewers().isEmpty()) { if (reviewerDtos.size() != 1) {
		 * throw new
		 * BadRequestException("You can choose only one reviewer for extra review"); }
		 * final ReviewerDTO reviewerSearchDto = reviewerDtos.get(0); if
		 * (paperEntity.getReviewers().stream().anyMatch(r -> r.getId() ==
		 * reviewerSearchDto.getId())) { throw new
		 * BadRequestException("This reviewer already reviewed this paper"); } } else if
		 * (reviewerDtos.size() < 2) { throw new
		 * BadRequestException("You must chose two reviewers at minimum"); }
		 * 
		 * paperService.setReviewers(Long.parseLong(paperId),
		 * reviewerDtos.stream().map(dto -> { final Reviewer reviewer = new Reviewer();
		 * reviewer.setId(dto.getId()); return reviewer;
		 * }).collect(Collectors.toList()));
		 */

		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		taskFormFieldDtos.add(new FormFieldDTO("reviewers",
				new ArrayList<>(reviewerDtos.stream().map(r -> userRepository.findByUsername(r.getUsername()))
						.map(AppUser::getUsername).collect(Collectors.toList()))));
		taskFormFieldDtos.add(new FormFieldDTO("reviewDuration", "P"+date+"D"));
		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("task/{taskId}/addNewReviewer")
	public ResponseEntity chooseNewReviewer(@PathVariable String taskId, @RequestBody ReviewerDTO reviewerDto,
			@RequestParam String date) {
		final TaskDto task = rspe.findTask(taskId);
		String magazine = (String) rspe.getVariable(task.getProcessInstanceId(), "magazine");

		/*
		 * final Paper paperEntity = paperService.findById(Long.parseLong(paperId));
		 * 
		 * if (paperEntity.getReviewers().stream().anyMatch(r -> r.getId() ==
		 * reviewerDto.getId())) { throw new
		 * BadRequestException("This reviewer already reviewed this paper"); }
		 * 
		 * final Reviewer reviewer = new Reviewer();
		 * reviewer.setId(reviewerDto.getId());
		 * paperService.setReviewer(Long.parseLong(paperId), reviewer);
		 */

		final AppUser user = userRepository.findByUsername(reviewerDto.getUsername());
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		taskFormFieldDtos.add(new FormFieldDTO("reviewer", user.getUsername()));
		taskFormFieldDtos.add(new FormFieldDTO("reviewDuration", "P"+date+"D"));
		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("task/{taskId}/addReview")
	public ResponseEntity reviewPaper(@PathVariable String taskId, @RequestBody ReviewDTO review) {
		// OK, novo
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		TaskDto task = rspe.findTask(taskId);
		String filename = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");
		Review r = new Review(review.getCommentAuthor(), review.getSuggestion(), review.getCommentEditor(), filename, username);
		reviewService.save(r);
		
		final ArrayList<FormFieldDTO> fields = new ArrayList<>();
		fields.add(new FormFieldDTO("commentAuthor", review.getCommentAuthor()));
		fields.add(new FormFieldDTO("suggestion", review.getSuggestion()));
		fields.add(new FormFieldDTO("commentEditor", review.getCommentAuthor()));
		rspe.submitTaskForm(taskId, fields);

		
		return ResponseEntity.noContent().build();
	}

	@PostMapping("task/{taskId}/addEditorReview")
	public ResponseEntity reviewPaperByEditor(@PathVariable String taskId, @RequestBody ReviewDTO review) {
		// OK, novo
		TaskDto task = rspe.findTask(taskId);
		String filename = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");
		//Review r = new Review(review.getCommentAuthor(), review.getSuggestion(), review.getCommentEditor(), filename);
		//reviewService.save(r);
		
		final ArrayList<FormFieldDTO> fields = new ArrayList<>();
		if(review.getNewReviewers().equals("yes")) {
			//ako je potrebno izabrati nove revizore jer se ocene revizora drasticno razlikuju
			rspe.setVariable(task.getProcessInstanceId(), "newReviewer", true);
		}
		else {
			System.out.println("***********************************"+review.getSuggestion());
			rspe.setVariable(task.getProcessInstanceId(), "newReviewer", false);
			if(review.getSuggestion().equals("PRIHVATITI UZ MANJE ISPRAVKE")) {
				fields.add(new FormFieldDTO("odluka", "prihvatiUzManjeIspravke"));
			}
			else if(review.getSuggestion().equals("USLOVNO PRIHVATITI UZ VECE ISPRAVKE")) {
				fields.add(new FormFieldDTO("odluka", "uslovnoPrihvatitiUzVeceIspravke"));

			}
			else {
				fields.add(new FormFieldDTO("odluka", review.getSuggestion()));

			}
		}
		rspe.submitTaskForm(taskId, fields);

		
		return ResponseEntity.noContent().build();
	}
	@GetMapping("task/{taskId}/paperPDF")
	public ResponseEntity getPaperPDFByTaskId(@PathVariable String taskId) {
		// novo
		TaskDto task = rspe.findTask(taskId);
		String filename = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");
		System.out.println("**********************" + filename);
		Path filepath = dirTemporaryLocation.resolve(filename);
		File bookFile = filepath.toFile();
		String rad = pdfDH.getText(bookFile);
		System.out.println("ispisujem task id: " + task.getId());
		return ResponseEntity.ok(new PaperDTO(rad, filename));
	}

	@GetMapping("task/{taskId}/getReviewsEditor")
	public ResponseEntity<Collection<ReviewEditorResponse>> getReviewsForEditor(@PathVariable String taskId) {
		// novo
		TaskDto task = rspe.findTask(taskId);
		String filename = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");

		Collection<Review> reviews= reviewService.getByPaper(filename);
		Collection<ReviewEditorResponse> responseReviews = new ArrayList<ReviewEditorResponse>();
		for(Review r : reviews) {
			ReviewEditorResponse response = new ReviewEditorResponse(r);
			responseReviews.add(response);
		}
		return ResponseEntity.ok(responseReviews);
	}
	
	@GetMapping("task/{taskId}/getReviewsAuthor")
	public ResponseEntity<Collection<ReviewAuthorResponse>> getReviewsForAuthor(@PathVariable String taskId) {
		// novo
		TaskDto task = rspe.findTask(taskId);
		String filename = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");

		Collection<Review> reviews= reviewService.getByPaper(filename);
		Collection<ReviewAuthorResponse> responseReviews = new ArrayList<ReviewAuthorResponse>();
		for(Review r : reviews) {
			ReviewAuthorResponse response = new ReviewAuthorResponse(r);
			responseReviews.add(response);
		}
		return ResponseEntity.ok(responseReviews);
	}
	
	@GetMapping("subscribe/{taskId}")
	public ResponseEntity subscribe(@PathVariable String taskId) {
		// OK, novo
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		TaskDto task = rspe.findTask(taskId);
		System.out.println("ispisujem task id iz subscribe: " + task.getId());

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
		// OK, novo, dobavljanje casopisa
		String username = this.tokenUtils.getUsernameFromToken(this.httpServletRequest.getHeader("X-Auth-Token"));
		System.out.println("dobavljam casopise za korisnika: " + username);
		Collection<MagazineDTO> magazines = magazineService.getAll(username);
		ProccessMagazineDTO pmd = new ProccessMagazineDTO(magazines);
		return pmd;

	}

	@PostMapping("chooseMagazine/{taskId}")
	public ResponseEntity chooseMagazine(@PathVariable String taskId, @RequestBody MagazineDetailsDTO magazineDetails) {
		// OK, novo
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
		// OK, novo, submitovani potrebni podaci o radu
		System.out.println("Dodajem rad: " + paperDTO.toString() + " za task sa idom: " + taskId);
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

	@PostMapping("adapted/{taskId}")
	public ResponseEntity adapted(@PathVariable String taskId, @RequestBody PaperDTO paperDTO) {
		// novo, submitovani potrebni podaci o radu za doradu uz komentar
		System.out.println("Dodajem rad: " + paperDTO.toString() + " za task sa idom: " + taskId);
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		taskFormFieldDtos.add(new FormFieldDTO("komentarDorada", paperDTO.getKomentarDorada()));

		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		System.out.println("Submitovani podaci za rad dosao dovde");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("resubmit/{taskId}")
	public ResponseEntity resubmit(@PathVariable String taskId, @RequestBody PaperDTO paperDTO) {
		// OK, novo, resubmitovani potrebni podaci o radu
		System.out.println("Dodajem rad: " + paperDTO.toString() + " za task sa idom: " + taskId);
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();

		taskFormFieldDtos.add(new FormFieldDTO("filename", paperDTO.getRad()));

		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		System.out.println("Submitovani podaci za rad dosao dovde resubmit");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("temporaryupload")
	public ResponseEntity uploadTemporary(@RequestParam("file") MultipartFile multipartFile) {
		//OK, jedino kad je isti fajl kaze da je vec zauzet
		String originalFileName = multipartFile.getOriginalFilename();

		Paper paper = null;
		File storedFile = esPaperService.storeFileTemporary(multipartFile);
		paper = esPaperService.getMetadata(storedFile);
		paper.setFilename(originalFileName);
		return new ResponseEntity<>(paper, HttpStatus.OK);
	}

	@PostMapping("tematicAnswer/{taskId}")
	public ResponseEntity tematicAnswer(@PathVariable String taskId, @RequestBody AnswerDTO answer) throws IOException {
		// OK, novo, submitovani potrebni podaci o radu
		System.out.println(" rad tematski prikladan: " + answer.getAnswer() + " za task sa idom: " + taskId);
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		if (answer.getAnswer().equals("yes")) {
			taskFormFieldDtos.add(new FormFieldDTO("radPrikladan", true));
		} else {
			taskFormFieldDtos.add(new FormFieldDTO("radPrikladan", false));
		}

		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		System.out.println("Submitovani podaci za rad dosao dovde tematic");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("formatAnswer/{taskId}")
	public ResponseEntity formatAnswer(@PathVariable String taskId, @RequestBody AnswerDTO answer, @RequestParam String date) throws IOException {
		// OK, novo, submitovani potrebni podaci o radu
		System.out.println(" rad tematski prikladan: " + answer.getAnswer() + " za task sa idom: " + taskId);
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		TaskDto task = rspe.findTask(taskId);
		System.out.println("ispisujem task id iz subscribe: " + task.getId());

		String filename = (String) rspe.getVariable(task.getProcessInstanceId(), "filename");
		if (answer.getAnswer().equals("yes")) {
			taskFormFieldDtos.add(new FormFieldDTO("radFormatiran", true));
		} else {
			taskFormFieldDtos.add(new FormFieldDTO("radFormatiran", false));
			taskFormFieldDtos.add(new FormFieldDTO("poruka", answer.getAnswer()));
			taskFormFieldDtos.add(new FormFieldDTO("timeCorrection", "P"+date+"D"));

			// Files.deleteIfExists(dirTemporaryLocation.resolve(filename));

		}

		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		System.out.println("Submitovani podaci za rad dosao dovde format");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("editorDecision/{taskId}")
	public ResponseEntity editorDecisionAnswer(@PathVariable String taskId, @RequestBody AnswerDTO answer) throws IOException {
		// Ok, novo, submitovani potrebni podaci o radu
		System.out.println(" rad editor odluka: " + answer.getAnswer() + " za task sa idom: " + taskId);
		final ArrayList<FormFieldDTO> taskFormFieldDtos = new ArrayList<>();
		if (answer.getAnswer().equals("yes")) {
			taskFormFieldDtos.add(new FormFieldDTO("doradaOK", true));
		} else {
			taskFormFieldDtos.add(new FormFieldDTO("doradaOK", false));
		}

		rspe.submitTaskForm(taskId, taskFormFieldDtos);

		System.out.println("Submitovani podaci za rad dosao dovde editor decision");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("task/{taskId}/authorComment")
	public ResponseEntity<AnswerDTO> getAuthorComment(@PathVariable String taskId) {
		// OK, novo, getuje samo one revizore sa prosledjenom naucnom oblascu
		TaskDto task = rspe.findTask(taskId);
		String comment = (String) rspe.getVariable(task.getProcessInstanceId(), "komentarDorada");
		System.out.println("ispisujem task id: " + task.getId()+" komentar dorada: "+comment);
		AnswerDTO answer = new AnswerDTO(comment);
		return ResponseEntity.ok(answer);
	}
	
	@GetMapping("task/{taskId}/editorCommentCorrection")
	public ResponseEntity<AnswerDTO> getEditorCommentCorrection(@PathVariable String taskId) {
		// OK, novo, getuje samo one revizore sa prosledjenom naucnom oblascu
		TaskDto task = rspe.findTask(taskId);
		String comment = (String) rspe.getVariable(task.getProcessInstanceId(), "poruka");
		System.out.println("ispisujem task id: " + task.getId()+" poruka za autora za korekciju rada: "+comment);
		AnswerDTO answer = new AnswerDTO(comment);
		return ResponseEntity.ok(answer);
	}
}
