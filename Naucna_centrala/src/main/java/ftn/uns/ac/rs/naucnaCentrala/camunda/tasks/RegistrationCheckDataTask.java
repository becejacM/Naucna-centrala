package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;

@Service
public class RegistrationCheckDataTask implements JavaDelegate{

	@Autowired
    private AppUserRepository userRepository;
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		// dodaj sve provere
		System.out.println("proveravam podatke" + (String)execution.getVariable("username"));
        
        if(this.userRepository.findByUsername((String)execution.getVariable("username"))!=null 
        		|| this.userRepository.findByEmail((String)execution.getVariable("email"))!=null) {
        	execution.setVariable("isOK", false);
        	execution.setVariable("author", (String)execution.getVariable("username"));
        }
        else {
        	execution.setVariable("isOK", true);
        }
	}

}
