package ftn.uns.ac.rs.naucnaCentrala.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class SocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    
    private static final Logger log = LoggerFactory.getLogger(SocketController.class);

    
    
    @MessageMapping("/nc")
    @SendTo("/nc/errors")
	public String processMessageFromClientAlarms(@Payload String message) throws Exception {
    	System.out.println("evo meeeeeeeeeeee porukaaa");
    	String name = new Gson().fromJson(message, Map.class).get("name").toString();
		return name;
    }
    
    @SendTo("/nc/notifyAboutOpenAccess")
	public String processMessageNotifyAboutOpenAccess(@Payload String message) throws Exception {
    	String name = new Gson().fromJson(message, Map.class).get("name").toString();
		return name;
    }
    
    @SendTo("/nc/notifyAboutActiveFee")
	public String processMessageNotifyAboutActiveFee(@Payload String message) throws Exception {
    	String name = new Gson().fromJson(message, Map.class).get("name").toString();
		return name;
    }
    
    @SendTo("/nc/notifyAboutInvalidPaper")
	public String processMessagenotifyAboutInvalidPaper(@Payload String message) throws Exception {
    	String name = new Gson().fromJson(message, Map.class).get("name").toString();
		return name;
    }
}
