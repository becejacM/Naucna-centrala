package ftn.uns.ac.rs.naucnaCentrala.taskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
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
        AppUser user = this.privateUserRepository.findByEmail(email);
        final String subject = "Verifikacija naloga";
        final String link = String.format("http://localhost:4200/register/verify/%d/%s", user.getId(), processInstanceId);
        final String messageText = String.format("Uspešno ste se registrovali.\n\n%s.\n\nMolimo Vas da klikom na link iznad potvrdite svoju registraciju.", link);
        emailService.sendMessage(email, subject, messageText);

        return link;
    }

    
}
