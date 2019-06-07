package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.Subscription;
import ftn.uns.ac.rs.naucnaCentrala.repository.SubscriptionRepository;

@Service
public class PublishCheckFee implements JavaDelegate{

	@Autowired
	SubscriptionRepository subRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		// ovde treba odraditi proveru da li je bas taj autor pretplacen na bas taj magazin, ima vec negde odradjena ta provera
		// OK odradjeno
		String magazine = (String) execution.getVariable("magazine");
		String user = (String) execution.getVariable("author");
    	System.out.println("proveravam da li je korisnik"+user+" pretplacen ako je casopis sa pretplatom, naziv magazina: "+ magazine);
    	Subscription sub = subRepository.getByKupacAndNazivCasopisa(user, magazine);
    	if(sub==null) {
        	System.out.println("nema suuub provera 1");
        	execution.setVariable("isOKFee", false);
    	}
    	else {
        	execution.setVariable("isOKFee", true);
    	}

	}

}
