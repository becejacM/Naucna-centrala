package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.model.PaymentMethod;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;

@Service
public class PublishCheckOpenAccess implements JavaDelegate{

	@Autowired
    private MagazineRepository magazineRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String magazine = (String) execution.getVariable("magazine");
		System.out.println("checkiram magazine "+magazine);
    	Magazine m = magazineRepository.findByName(magazine);
    	if(m.getPaymentMethod().equals(PaymentMethod.OPEN_ACCESS)) {
        	System.out.println("open access autoru se naplacuje");
        	execution.setVariable("isOK", false);
    	}
    	else {
    		System.out.println("nije open access autoru se ne naplacuje");
        	execution.setVariable("isOK", true);
    	}
    	
	}

	
}
