package ftn.uns.ac.rs.naucnaCentrala.taskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.model.Author;
import ftn.uns.ac.rs.naucnaCentrala.model.Editor;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.service.EmailService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class EmailTaskService {

	@Autowired
    private AppUserRepository privateUserRepository;

	@Autowired
    private EmailService emailService;

    

    public String sendConfirmationMail(String email, String processInstanceId) {
    	//korisceno, poziva sse iz registrationStoreDataTask
        AppUser user = this.privateUserRepository.findByEmail(email);
        final String subject = email;
        final String link = String.format("http://localhost:4200/register/verify/%d/%s", user.getId(), processInstanceId);
        final String messageText = String.format("Uspešno ste se registrovali.\n\n%s.\n\nMolimo Vas da klikom na link iznad potvrdite svoju registraciju.", link);
        sendEmail(email, subject, messageText);

        return link;
    }

    public void sendEmailAuthorAndEditorPaperReceived(String starter, String usernameEditor) {
    	//korisceno, poziva se iz procesnog taska
    	Author a = (Author) privateUserRepository.findByUsername(starter);
    	Editor e = (Editor) privateUserRepository.findByUsername(usernameEditor);
    	
        final String subject = "Primljen rad";
        final String messageText = String.format("Rad je uspesno primljen. Molimo Vas da na svom nalogu izvrsite Vase naredne zadatke!");
        
        sendEmail(subject, a.getEmail(), messageText);
        sendEmail(subject, e.getEmail(), messageText);
    }
    public void sendEmailAuthorAndEditorPaperReceived(String usernameEditor) {
    	Editor e = (Editor) privateUserRepository.findByUsername(usernameEditor);
    	
        final String subject = "Primljen rad";
        final String messageText = String.format("Rad je uspesno primljen. Molimo Vas da na svom nalogu izvrsite Vase naredne zadatke!");
        
        sendEmail(subject, e.getEmail(), messageText);
    }
    public void sendEmailAuthorPaperRejectedFormat(String starter) {
    	//korisceno
    	Author a = (Author) privateUserRepository.findByUsername(starter);    	
        final String subject = "Odbijen rad";
        final String messageText = String.format("Vas rad je odbijen jer nije tematski prikladan!");
        
        sendEmail(subject, a.getEmail(), messageText);
    }
    
    public void sendEmailAuthorPaperCorrection(String starter, String poruka) {
    	//korisceno
    	Author a = (Author) privateUserRepository.findByUsername(starter);    	
        final String subject = "Potrebnna korekcija rada";
        final String messageText = String.format("Vas rad nije dobro formatiran! Molimo Vas posetite svoj profil i izvrsite potrebne korekcije. Poruka editora: "+poruka);
        
        sendEmail(subject, a.getEmail(), messageText);
    }
    
    public void sendEmailEditorReviewerFailed(String glavniUrednik, String urednik) {
    	System.out.println("Revizor nije revizirao");
    	Editor e = null;
    	if(urednik.equals("")) {
        	e = (Editor) privateUserRepository.findByUsername(glavniUrednik);
    	}
    	else {
        	e = (Editor) privateUserRepository.findByUsername(urednik);
    	}
        final String subject = "Revizor nije revizirao rad";
        final String messageText = String.format("Odabrani revizor nije revizirao dodeljeni rad. Molimo Vas odaberite drugog revizora!");
        
        sendEmail(subject, e.getEmail(), messageText);
    }
    public void sendPaperToReviewers() {
    	System.out.println("Saljem rad revizorima");
    }
    
    public void sendEmail(String subject, String email, String messageText) {
        emailService.sendMessage(email, subject, messageText);
    }
    
    
}
