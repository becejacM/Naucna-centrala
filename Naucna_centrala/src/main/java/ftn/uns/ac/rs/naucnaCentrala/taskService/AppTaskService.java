package ftn.uns.ac.rs.naucnaCentrala.taskService;

import javax.transaction.Transactional;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.model.PaymentMethod;
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
    	System.out.println("radi ovo obavestenje 2");
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
}
