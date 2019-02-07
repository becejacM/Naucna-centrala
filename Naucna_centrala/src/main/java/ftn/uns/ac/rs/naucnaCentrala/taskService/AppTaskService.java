package ftn.uns.ac.rs.naucnaCentrala.taskService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.model.Author;
import ftn.uns.ac.rs.naucnaCentrala.model.Editor;
import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.model.PaymentMethod;
import ftn.uns.ac.rs.naucnaCentrala.model.Reviewer;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.RoleRepository;
import ftn.uns.ac.rs.naucnaCentrala.service.EmailService;

@Component
@Transactional
public class AppTaskService {

	private FormService formService;

    private TaskService taskService;

    private RuntimeService runtimeService;

    @Autowired
    private MagazineRepository magazineRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private EmailService emailService;

    private SimpMessagingTemplate template;
    
    @Autowired
    public AppTaskService(final FormService formService,
                                   final TaskService taskService,
                                   final RuntimeService runtimeService, SimpMessagingTemplate template) {
        this.formService = formService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.template = template;

    }
    
    public Boolean checkMagazine(String name) {
    	System.out.println("checkiram magazine "+name);
    	Magazine m = magazineRepository.findByName(name);
    	if(m.getPaymentMethod().equals(PaymentMethod.OPEN_ACCESS)) {
        	System.out.println("open access autoru se naplacuje");
    		return true;
    	}
    	System.out.println("open access autoru se ne naplacuje");
    	return false;
    }
    
    public Boolean checkFee(String name) {
    	System.out.println("proveravam da li je korisnik pretplacen ako je casopis sa pretplatom"+name);
    	return true;
    }
    
    public void notifyAboutOpenAccess() {
    	System.out.println("radi ovo obavestenje 2 ");
        this.template.convertAndSend("/nc/notifyAboutOpenAccess", "Magazine is open access");
    }
    
    public void notifyAboutActiveFee() {
    	System.out.println("radi ovo obavestenje 3");
        this.template.convertAndSend("/nc/notifyAboutActiveFee", "You have access to choosen magazine");
    }
    
    public void payFee() {
    	System.out.println("radi ovo obavestenje 4");
        this.template.convertAndSend("/nc/errors", "You must pay first");
    }
    
    public void notifyAboutInvalidPaper() {
    	System.out.println("radi ovo obavestenje 5");
        this.template.convertAndSend("/nc/notifyAboutInvalidPaper", "Invalid paper");
    }
    
    public boolean checkPaper(String naslov, String kljucneReci, String apstrakt, String naucnaOblast, String filename) {
    	System.out.println("proveravam validnost rada");
    	if(naslov.equals(null) || naslov.equals("")) {
    		return false;
    	}
    	return true;
    }
    
    public String addMainEditor(String magazineName) {
    	String usernameEditor = "";
    	Magazine m = this.magazineRepository.findByName(magazineName);
    	System.out.println(m.getName() + "naziv magazinaaaa");

    	Collection<Editor> editors = m.getEditorialBoard().getEditors();
    	for (Editor editor : editors) {
        	System.out.println(editor.getUsername() + "naziv editora");
			if(editor.isMain().equals("yes")) {
				usernameEditor = editor.getUsername();
			}
		}
    	System.out.println("dodeljujem urednika "+usernameEditor);
    	return  usernameEditor;
    }
    
    public String checkScientificField() {
    	System.out.println("Proveravam urednika naucne oblasti casopisa");
    	return "";
    }
    
    public void checkEditor() {
    	System.out.println("Dodeljujem urednika naucne oblasti casopisa radu");

    }
    
    public void checkMainEditor() {
    	System.out.println("Dodeljujem glavnog urednika naucne oblasti casopisa radu");

    }
    
    public Collection<String> checkAndReturnReviewers(String magazine, String processInstanceId){
		System.out.println(" ******************************");

    	Collection<String> reviewersUsernames = new ArrayList<String>();
    	Collection<Reviewer> reviewers = magazineRepository.findByName(magazine).getEditorialBoard().getReviewers();
    	for (Reviewer reviewer : reviewers) {
			reviewersUsernames.add(reviewer.getUsername());
		}
    	Map<String, AbstractFormFieldType> formTypes = new HashMap<String, AbstractFormFieldType>();
        AbstractFormFieldType formType = null;

    	Map<String, String> values = new LinkedHashMap<String, String>();
    	int i=0;
    	for (String s : reviewersUsernames) {
			values.put(i+"", s);
			i++;
		}
        formType = new EnumFormType(values);
        formTypes.put(formType.getName(), formType);
		/*Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
		TaskFormData formData = formService.getTaskFormData("Task_0d9vsou");
		for (FormField f : formData.getFormFields()) {
			System.out.println(f.getType().getName()+" ******************************");
			if(f.getType().getName().equals("enum")) {
				EnumFormType enumFormType = (EnumFormType) f.getType();
				Map<String, String> values = enumFormType.getValues();
				enumFormType.getValues().put("key", "miki");
				f = (FormField) enumFormType;
			}
		}*/
		return reviewersUsernames;
    }
}
