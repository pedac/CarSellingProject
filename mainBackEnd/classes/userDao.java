package com.carApp.Dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.carApp.entity.User;


@Repository
public interface userDao extends JpaRepository<User, Long> {
	
	
	@Query("select  u from User u where u.email=:email And u.passWord=:password")
	public User findByusernameAndPassword(@Param("email") String email, @Param("password") String password);
	
	@Query("select  u from User u where u.email=:email")
	public User forGotPassword(@Param("email") String email);
	
	@Query("select  u from User u where u.email=:email")
	public User getSingleuserByEmail(@Param("email") String email);
	
	@Query("select  u from User u where u.verificationCode=:verificationCode")
	public User getuserByverificationCode(@Param("verificationCode") String verificationCode);


	  @Query("SELECT c FROM User c WHERE c.email = ?1")
	    public User findByEmail(String email);

	public User findByResetPasswordToken(String token); 
	
	@Query("select email From User")
	public List<String> getAllEmails();

	@Query("select Mobile From User")
	public List<String> getAllMobile();

	
	
}
