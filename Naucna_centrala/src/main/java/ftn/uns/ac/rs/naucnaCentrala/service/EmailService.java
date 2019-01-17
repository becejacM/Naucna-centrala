package ftn.uns.ac.rs.naucnaCentrala.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	public void sendMessage(String to, String subject, String text);
	
}