package ftn.uns.ac.rs.naucnaCentrala.taskService;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.model.UserRole;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.service.EmailService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Transactional
public class RegistrationTaskService {

    private FormService formService;

    private TaskService taskService;

    private RuntimeService runtimeService;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private SimpMessagingTemplate template;
    
    @Autowired
    public RegistrationTaskService(final FormService formService,
                                   final TaskService taskService,
                                   final RuntimeService runtimeService, SimpMessagingTemplate template) {
        this.formService = formService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.template = template;

    }

    /*public TempUser temporaryPersistData(String username, String name, String email, String password, String address,
                                         String place, String zipCode, String lat, String lng) {
        TempUser tempUser = new TempUser();
        tempUser.setName(name);
        tempUser.setUsername(username);
        tempUser.setEmail(email);
        tempUser.setPassword(password);
        tempUser.setAddress(address);
        tempUser.setPlace(place);
        tempUser.setZipCode(zipCode);
        tempUser.setLat(lat);
        tempUser.setLng(lng);

        return tempUser;
    }*/

    //public void persistData(AppUser tempUser) {
    public void persistData(String firstname, String lastname, String username, String password, String email, String city, String state) {
        AppUser user = new AppUser();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        user.setVerified(false);
        user.setRole(UserRole.ADMIN);
        
        userRepository.save(user);
    }


    public boolean checkData(String firstname, String lastname, String username, String password, String email, String city, String state) {
    	System.out.println("proveravam podatke" + username);
        AppUser userByUsername = this.userRepository.findByUsername(username);
        AppUser userByEmail = this.userRepository.findByEmail(email);

        return userByUsername == null && userByEmail == null;
    }

    public ModelAndView notifyAboutInvalidData() {
        //FIX ME: try with web sockets
    	System.out.println("radi ovo obavestenje");
        this.template.convertAndSend("/nc/errors", "Registration failed");
        //return "Korisničko ime/email već postoji u sistemu";
        String projectUrl = "http://localhost:4200/registration/error";
		return new ModelAndView("redirect:" + projectUrl);
    }
    
    public void deleteUser(String username) {
        AppUser user = this.userRepository.findByUsername(username);
        this.userRepository.delete(user);
    }

    public void activateUser(long userId) {
    	System.out.println("verifikujem");
    	AppUser user = this.userRepository.getOne(userId);
        user.setVerified(true);

        this.userRepository.save(user);
    }

}

