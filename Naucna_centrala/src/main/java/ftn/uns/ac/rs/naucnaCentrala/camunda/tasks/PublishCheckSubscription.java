package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.Subscription;
import ftn.uns.ac.rs.naucnaCentrala.repository.SubscriptionRepository;

@Service
public class PublishCheckSubscription implements JavaDelegate{
	@Autowired
	SubscriptionRepository subRepository;
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// OK
		String magazine = (String) execution.getVariable("magazine");
		String user = (String) execution.getVariable("author");
    	System.out.println("proveravam da li je korisnik"+user+" pretplacen ako je casopis sa pretplatom, naziv magazina: "+ magazine);
    	Subscription sub = subRepository.getByKupacAndNazivCasopisaAndStatus(user, magazine, "SUCCESS");
    	
    	if(sub==null) {
        	System.out.println("nema suuubskripcije");
            throw new BpmnError("NeuspesnaPretplata", "NeuspesnaPretplata!");
    	}
    	else {
    		
    		if (!sub.getStatus().equals("SUCCESS")) {
    	    	System.out.println("subskripcija koja nije success");
                throw new BpmnError("NeuspesnaPretplata", "NeuspesnaPretplata!");
            }
    	}
	}

}
