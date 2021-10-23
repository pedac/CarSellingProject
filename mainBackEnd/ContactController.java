package com.carApp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;



@Controller
public class ContactController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@GetMapping("/contactu")
	public String contactus() {
	
		return "contactus";
	}

	
	@PostMapping("/contactu")
	public String submitContact(HttpServletRequest request)
	{
		String name=request.getParameter("name");
		String email=request.getParameter("email"); 
		String phone=request.getParameter("phone");
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		
		SimpleMailMessage message= new SimpleMailMessage();
		message.setFrom("careasy34@gmail.com");
		message.setTo("careasy34@gmail.com");
		
		String mailSubject= name + " has sent a message";
		String mailContent= "Sender name: " + name+ "\n";
		mailContent += "Sender email: "+ email +"\n";
		mailContent += "Sender phone: "+ phone +"\n";
		mailContent += "Subject: "+ subject +"\n";
		mailContent += "Content: "+ content +"\n";
		
		message.setSubject(mailSubject);
		message.setText(mailContent);
		
		mailSender.send(message);
		
		return "message1";
		
		
	}
}
