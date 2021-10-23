package com.carApp.controller;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;


import com.carApp.entity.User;


import com.carApp.service.userServices;

@Controller
public class UserController {

	@Autowired
	userServices userserve;
	

	@GetMapping("/admin")
	public String returadmin() {
		return "adminHome";
	}
	
	
	@GetMapping("/userHome")
	public String returnuser() {
		return "userHome";
	}
	

	@GetMapping("/getall")
	public String getallUsers(Model model) {
		List<User> list=this.userserve.getAllUser();
		model.addAttribute("users",list);
		return "userList";
	}
	

	

	
	@GetMapping("/delete/{userId}")
	public String  deleteUser(@PathVariable(value ="userId") long id) {
	// long id1=Long.parseLong("userId");
	   this.userserve.deleteUser(id);
	   return "redirect:/getall";
	}
	

}
