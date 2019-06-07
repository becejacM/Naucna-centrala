package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import java.util.Collection;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.model.Editor;
import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;

@Service
public class PublishAddMainEditor implements JavaDelegate{

	@Autowired
    private MagazineRepository magazineRepository;
	
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// OK
		String usernameEditor = "";
        String magazine = (String) execution.getVariable("magazine");
    	Magazine m = this.magazineRepository.findByName(magazine);
    	System.out.println(m.getName() + " naziv magazinaaaa");

    	Collection<Editor> editors = m.getEditorialBoard().getEditors();
    	for (Editor editor : editors) {
        	System.out.println(editor.getUsername() + " naziv editora");
			if(editor.isMain().equals("yes")) {
				usernameEditor = editor.getUsername();
			}
		}
        execution.setVariable("editor", usernameEditor);
    	System.out.println("dodeljujem urednika "+usernameEditor);
	}

}
