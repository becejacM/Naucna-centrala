package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;
import ftn.uns.ac.rs.naucnaCentrala.taskService.EmailTaskService;
@Service
public class PublishAddNewReviewer implements JavaDelegate{

	@Autowired
    private MagazineRepository magazineRepository;
	
	@Autowired
	private EmailTaskService emailService;
	
	@Autowired
	private AppUserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
        
        final String username = (String) execution.getVariable("choosenEditor");
        System.out.println("username choosenEditora/////////////////////: "+username);
        //final AppUser editor = userRepository.findByUsername(username);
        
        emailService.sendEmailEditorChooseNewReviewer(username);
	}

}
