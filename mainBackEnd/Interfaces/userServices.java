package com.carApp.service;

import java.util.List;

import com.carApp.entity.User;

public interface userServices {
	
	public User registerUser( User user);
	
	public List<User> getAllUser();
	
	public void deleteUser(long userId);
	
	public User updateUserDet();
	
	public User getSingleUser(String email,String password);
	
    public User getUserForChangePassword(String email);
    
    public String validate(String email, String password);

    public User getUser(String email);
    
    public void register(User user, String siteURL);
     
    boolean verifyUser(String verifivationCode);
    
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

	public User getByResetPasswordToken(String token);

	public void updatePassword(User user, String newPassword);

	public User getcontact(long userId);
	
	public User getUserForBuyer(long userId);
	
	public List<String> getAllEmails();

	public List<String> getAllMobile();
   

	
	

}
