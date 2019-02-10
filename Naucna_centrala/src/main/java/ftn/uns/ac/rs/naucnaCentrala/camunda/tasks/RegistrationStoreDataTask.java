package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.rest.dto.identity.UserCredentialsDto;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.camunda.bpm.engine.rest.dto.identity.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.camunda.restServiceProcessEngine.RestServiceProcessEngine;
import ftn.uns.ac.rs.naucnaCentrala.model.AppUser;
import ftn.uns.ac.rs.naucnaCentrala.model.Author;
import ftn.uns.ac.rs.naucnaCentrala.model.Role;
import ftn.uns.ac.rs.naucnaCentrala.model.UserRole;
import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.repository.RoleRepository;
import ftn.uns.ac.rs.naucnaCentrala.taskService.EmailTaskService;

@Service
public class RegistrationStoreDataTask implements JavaDelegate{

	@Autowired
	private RestServiceProcessEngine rspe;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private AppUserRepository userRepository;
	
	@Autowired
	private EmailTaskService emailService;

    public RegistrationStoreDataTask() {
    }
    
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		AppUser user = new AppUser();
        user.setFirstname((String) execution.getVariable("firstname"));
        user.setLastname((String) execution.getVariable("lastname"));
        user.setEmail((String) execution.getVariable("email"));
        String passwordHashed = new BCryptPasswordEncoder().encode((String) execution.getVariable("password"));
        user.setPassword(passwordHashed);
        user.setUsername((String) execution.getVariable("username"));
        user.setState((String) execution.getVariable("state"));
        user.setCity((String) execution.getVariable("city"));

        user.setVerified(false);
        user.setRole(UserRole.AUTHOR);
        Role role = roleRepository.findByName("AUTHOR");
        user.getRoles().add(role);
        System.out.println("kreiram useraaaaa");
        AppUser saved = userRepository.save(user);
        
        this.emailService.sendConfirmationMail((String) execution.getVariable("email"), execution.getProcessInstanceId());
        execution.setVariable("userId", saved.getId());
        //execution.setVariable("emailAuthor", (String) execution.getVariable("email"));
	}
	
	public void creteUser(Author author) {
		final UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(author.getUsername());
        userProfileDto.setEmail(author.getEmail());
        userProfileDto.setFirstName(author.getFirstname());
        userProfileDto.setLastName(author.getLastname());
        final UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setPassword(author.getPassword());
        final UserDto userDto = new UserDto();
        userDto.setProfile(userProfileDto);
        userDto.setCredentials(userCredentialsDto);
        rspe.createUser(userDto);

	}

}
