package com.carApp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carApp.entity.User;
import com.carApp.service.userServices;
import com.carApp.service.vehicleService;

@Controller
public class Buyercontroller {

	@Autowired
	vehicleService vehicleservice;

	@Autowired
	userServices userservice;

	@GetMapping("/bLogin")
	public String login(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "buyerLogin";
	}

	@PostMapping("/validateBuyer")
	public String confirmUser(@RequestParam("email") String email, @RequestParam("passWord") String password,
			HttpSession session) {
		
		String userRole = this.userservice.validate(email, password);
		
		
		long thisId = this.userservice.getUser(email).getUserId();
		System.out.println(session.getAttribute("vuserId"));
		long sellUserId = (long) session.getAttribute("vuserId");
		
		
		if (thisId == sellUserId) {

			try {

				User user = this.userservice.getUser(email);

				session.setAttribute("userName", user.getUserName());
				session.setAttribute("userId", user.getUserId());

		} catch (NullPointerException e) {
				return userRole;
			}

		}else {
		User user1 = this.userservice.getUser(email);
			session.setAttribute("userName", user1.getUserName());
			session.setAttribute("userId", user1.getUserId());

			return "redirect:/contact" ;
		}
		return userRole;

	}
	
	
	@GetMapping("/contact")
	public String getContact(HttpSession session, Model model) {
		long userId=(long)session.getAttribute("vuserId");
		User user=this.userservice.getcontact(userId);
		model.addAttribute("User",user);
		
		return "contactDetail" ;
	}

}
