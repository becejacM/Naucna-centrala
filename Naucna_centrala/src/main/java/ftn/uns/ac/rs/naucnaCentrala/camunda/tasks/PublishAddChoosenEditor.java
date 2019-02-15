package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import java.util.Collection;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.model.Editor;
import ftn.uns.ac.rs.naucnaCentrala.model.Magazine;
import ftn.uns.ac.rs.naucnaCentrala.model.ScientificField;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.MagazineRepository;
import ftn.uns.ac.rs.naucnaCentrala.taskService.EmailTaskService;

@Service
public class PublishAddChoosenEditor implements JavaDelegate{

	@Autowired
    private MagazineRepository magazineRepository;
	
	@Autowired
	private EmailTaskService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String choosenEditor = "";

        String naucnaOblast = (String) execution.getVariable("nsucnaOblast");
        String magazine = (String) execution.getVariable("magazine");
        Magazine m = this.magazineRepository.findByName(magazine);
    	System.out.println(m.getName() + " naziv magazinaaaa, a naucna oblast je: "+naucnaOblast);

    	Collection<Editor> editors = m.getEditorialBoard().getEditors();
    	for (Editor editor : editors) {
        	System.out.println(editor.getUsername() + " naziv editora"+ " naucne oblasti editora: ");
        	for(ScientificField sf : editor.getEditorFields()) {
        		System.out.println(sf.getScientificFieldName());
        		String sfn = sf.getScientificFieldName().name();
        		if(sfn.equals(naucnaOblast)) {
        			choosenEditor = editor.getUsername(); //znaci ima editora tog casopisa za tu naucnu oblast
            		System.out.println("Postoji urednik za tu naucnu oblast i to je: "+choosenEditor);
        		}
        	}
		}
    	if(choosenEditor.equals("")) {
    		choosenEditor = (String) execution.getVariable("editor");
    		System.out.println("Nema urednika za tu naucnu oblas dodeljuje se glavni urednik: "+choosenEditor);
    	}

    	execution.setVariable("choosenEditor", choosenEditor);
    	emailService.sendEmailEditorChooseReviewers(choosenEditor);
    	System.out.println("dodeljujem urednika za odabir recenzenata "+choosenEditor);
	}

}
