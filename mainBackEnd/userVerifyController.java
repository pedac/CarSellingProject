package com.carApp.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carApp.entity.User;
import com.carApp.service.UserNotFoundException;

import com.carApp.service.userServices;

import com.carApp.Utility;


import net.bytebuddy.utility.RandomString;

@Controller
public class userVerifyController {


	@Autowired
	 private JavaMailSender mailSender;
	
	@Autowired
	userServices userserve;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	// registration
		@GetMapping("/registration")
		public String registrationForm(Model model) {
			User user=new User();
			model.addAttribute("user",user);
			return "register";
		}
		
		
		@PostMapping("/saveUser")
		public String  addUser(@Valid @ModelAttribute User user,BindingResult result, HttpServletRequest reqeust) {
			
			List<String> mailList=this.userserve.getAllEmails();
			List<String> mobileList=this.userserve.getAllMobile();
			
			if(mailList.contains(user.getEmail())) {
				return "redirect:/registration?emailMessage" ;
			}
			
			if(mobileList.contains(user.getMobile())) {
				return "redirect:/registration?mobileMessage" ;
			}

			
			
			
			
			if(result.hasErrors())
			{
				return "register" ;
			}
			
			System.out.println(user.getPassWord());
			String encodedPassword=bCryptPasswordEncoder.encode(user.getPassWord());
			System.out.println(encodedPassword);
			user.setPassWord(encodedPassword);
			
			this.userserve.register(user,getStringURL(reqeust));
			
		    // String verify= Verified(null)
			return "redirect:/login" ;
		}

		private String getStringURL(HttpServletRequest request) {
		String siteURL=request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(),"");
		}
		
		@GetMapping("/verify")
		public String Verified(@RequestParam("code") String code) {
			 if(userserve.verifyUser(code)) {
				 return "redirect:/login?registeredsuccessfully" ;
			 }
			 
			 return "failRegister";
			
			
			
		}
	
	
	// login  
	@GetMapping("/login")
	public String login(Model model) {
		User user=new User();
		model.addAttribute("user",user);
		return "login";
	}
	
	@PostMapping("/validateUser")
	public String confirmUser(@RequestParam("email") String email, @RequestParam("passWord") String password, HttpSession session) {
		
		
		  String userRole=this.userserve.validate(email, password);
		  
		 try{
			 User user=this.userserve.getUser(email);
			
			 session.setAttribute("userName",user.getUserName());
			 session.setAttribute("userId", user.getUserId());
			  
		 }catch(NullPointerException e) {
			 return userRole;
		 }
      return userRole; 
	
	}




	 @GetMapping("/forgot_password")
	    public String showForgotPasswordForm() {
	        return "forgot_password_form";
	    }
	 

	 
	    @PostMapping("/forgot_password")
	    public String processForgotPassword(HttpServletRequest request, Model model) {
	    	System.out.println("comes upto this");
	        String email = request.getParameter("email");
	        System.out.println(email);
	        String token = RandomString.make(30);
	        System.out.println(token);
	         
	        try {
	            userserve.updateResetPasswordToken(token, email);
	            
	            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
	            System.out.println(resetPasswordLink);
	            sendEmail(email, resetPasswordLink);
	           
	            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
	             
	        } catch (UserNotFoundException ex) {
	            model.addAttribute("error", ex.getMessage());
	        } catch (UnsupportedEncodingException | MessagingException e) {
	            model.addAttribute("error", "Error while sending email");
	        }
	             
	        return "redirect:/";
	    }
	    
	    
	    
	    public void sendEmail(String recipientEmail, String link)
	            throws MessagingException, UnsupportedEncodingException {
	    	
	    	System.out.println("mailsender Started");
	        MimeMessage message = mailSender.createMimeMessage();              
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	         
	        helper.setFrom("careasy34@gmail.com", "CarEasy: change your password");
	        helper.setTo(recipientEmail);
	         
	        String subject = "Here's the link to reset your password";
	         
	        String content = "<p>Hello,</p>"
	                + "<p>You have requested to reset your password.</p>"
	                + "<p>Click the link below to change your password:</p>"
	                + "<p><a href=\"" + link + "\">Change my password</a></p>"
	                + "<br>"
	                + "<p>Ignore this email if you do remember your password, "
	                + "or you have not made the request.</p>";
	         
	        helper.setSubject(subject);
	         
	        helper.setText(content, true);
	         
	        mailSender.send(message);
	    }
	    
	    
	    
	    @GetMapping("/reset_password")
	    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
	        User user = userserve.getByResetPasswordToken(token);
	        model.addAttribute("token", token);
	         
	        if (user == null) {
	            model.addAttribute("message", "Invalid Token");
	            return "message";
	        }
	         
	        return "reset_password";
	    }
	    
	    
	    
	    @PostMapping("/reset_password")
	    public String processResetPassword(HttpServletRequest request, Model model) {
	        String token = request.getParameter("token");
	        String password = request.getParameter("password");
	        String encodedPassword=bCryptPasswordEncoder.encode(password);
	        User user = userserve.getByResetPasswordToken(token);
	        model.addAttribute("title", "Reset your password");
	         
	        if (user == null) {
	            model.addAttribute("message", "Invalid Token");
	            return "redirect:/login";
	        } else {           
	            userserve.updatePassword(user, encodedPassword);
	             
	          model.addAttribute("message", "You have successfully changed your password.");
	           // return "redirect:/login?message";
	        }
	         
	        return "redirect:/login";
	    }
	    
	
		
		@GetMapping("/logout")
		public String invalidate(HttpSession session) {
			
			System.out.println(session.getAttribute("userName"));
			session.removeAttribute("userName");
			session.removeAttribute("userId");
			session.invalidate();
			return "redirect:/";
		}
		
	
	
}
