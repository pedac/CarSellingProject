package com.carApp.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carApp.Dao.userDao;
import com.carApp.entity.User;


import net.bytebuddy.utility.RandomString;
@Service
public class userServiceImpl implements userServices {
	
	@Autowired
	userDao userRepo;
	
	@Autowired
    private JavaMailSender mailSender;
	

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

 
	
	List<User> list=null;

	@Override
	public User registerUser( User user) {
		this.userRepo.save(user);
		return user;
	}
	
	public User getUserForBuyer(long userId) {
		User user=userRepo.getById(userId);
		return user;
		
	}
	

	@Override
	public List<User> getAllUser() {
	list=new ArrayList<User>();
	list=this.userRepo.findAll();
	
		return list;
	}

	
	
	@Override
	public void deleteUser(long userId) {
	this.userRepo.deleteById(userId);
		
	}

	@Override
	public User updateUserDet() {
		
		return null;
	}

	@Override
	public User getSingleUser(String email, String password) {
	   User user=this.userRepo.findByusernameAndPassword(email, password);
		return user;
	}
	
	public User getUserForChangePassword(String email) {
		return this.userRepo.forGotPassword(email);
	}

	@Override
	public String validate(String email, String password) {
		System.out.println(password);
		   try { 
			   
			   User user1=this.userRepo.getSingleuserByEmail(email);
			   String passwordFD= user1.getPassWord();
		  // String pass= bCryptPasswordEncoder.encode(password);
		   System.out.println(passwordFD);
		  System.out.println(bCryptPasswordEncoder.matches(password,passwordFD));
		 if(bCryptPasswordEncoder.matches(password,passwordFD)) {
		   if(user1 !=null && user1.isEnabled()) {
			   int role=user1.getUserType();
               			   
		    	if(role == 1 ) {
		    		
		    	return "adminHome";
		    	}
		    	else {
		    		return "userHome";
		    	}
		    	
		    }
		 }
	 }catch(NullPointerException e) {
		   return "redirect:/login?invaliduser" ; 
	 }
		return "redirect:/login?invaliduser" ; 
	}

	
	@Override
	public User getcontact(long userId) {
		User user=this.userRepo.getById(userId);
		return user;
	}


	@Override
	public User getUser(String email) {
		User user=this.userRepo.getSingleuserByEmail(email);
		//String username=user.getUserName();
		//long userId =user.getUserId();
		return user;
	}
	
	
	// registration verification
	@Override
	public void register(User user, String siteURL)  {
		
		
		
		String randomCode=RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setEnabled(false);
		this.userRepo.save(user);
		System.out.println(user);
		sendVerificationEmail(user, siteURL);
   }
	
	
	private void sendVerificationEmail(User user,String siteURL) {
		
		System.out.println("*************");
		 String toAddress=user.getEmail();
		 String fromAddress="prashya2017sirsat@gmail.com";
		 String senderName="CarEasy";
		 
		   String subject = "Please verify your registration";
		    String content = "Dear [[name]],<br>"
		            + "Please click the link below to verify your registration:<br>"
		            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
		            + "Thank you,<br>"
		            + "CarEasy";
		     
		    MimeMessage message=mailSender.createMimeMessage();
		    MimeMessageHelper helper=new MimeMessageHelper(message); 
			System.out.println("###################");

		   try {
			helper.setFrom(fromAddress,senderName);

		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		   try {
			helper.setTo(toAddress);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		   
		   try {
			helper.setSubject(subject);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
			System.out.println("*************");

		   
		   content =content.replace("[[name]]", user.getUserName());
		   String verifyURL=siteURL+"/verify?code="+user.getVerificationCode();
		   content=content.replace("[[URL]]", verifyURL);
		   try {
			helper.setText(content,true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
			System.out.println("*************");

		   
		   mailSender.send(message);
	
	 }


	
	public boolean verifyUser(String verifivationCode) {
		User user=userRepo.getuserByverificationCode(verifivationCode);
		if(user==null||user.isEnabled()) {
			return false;
		}
		else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			this.userRepo.save(user);
			return true;
			
		}
		
	}
	

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user =userRepo.findByEmail(email);
        System.out.println(user);
        if (user != null) {
        	user.setResetPasswordToken(token);
        	userRepo.save(user);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }

	@Override
	public User getByResetPasswordToken(String token) {
		 return userRepo.findByResetPasswordToken(token);
		
	}

	@Override
	public void updatePassword(User user, String newPassword) {
		  user.setConfirmpassword(newPassword);
		  user.setPassWord(newPassword);
	     // user.setPassWord(newPassword);   	
	        user.setResetPasswordToken(null);
	        userRepo.save(user);
	    }
	
	@Override
	public List<String> getAllEmails() {
		List<String> MList=this.userRepo.getAllEmails();
		return MList;
	}

	@Override
	public List<String> getAllMobile() {
		List<String> mobileList= this.userRepo.getAllMobile();
		return mobileList;
	}
		
	}
	
	
	
	
	
	
	
	
	


