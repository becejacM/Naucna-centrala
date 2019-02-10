package ftn.uns.ac.rs.naucnaCentrala.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.naucnaCentrala.repository.AppUserRepository;
import ftn.uns.ac.rs.naucnaCentrala.service.EmailService;
import ftn.uns.ac.rs.naucnaCentrala.taskService.EmailTaskService;

@Service
public class EmailServiceImpl implements EmailService{

	
	@Autowired
    public JavaMailSender emailSender;

	@Override
    public void sendMessage(
            String to, String subject, String text) {
    	System.out.println("saljem");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(to);
        message.setText(text);
        emailSender.send(message);
    }

	

}
