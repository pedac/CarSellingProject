package com.carApp.controller;

import java.io.IOException;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.stereotype.Controller;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.carApp.entity.User;
import com.carApp.entity.Vehicle;
import com.carApp.service.userServices;
import com.carApp.service.vehicleService;


@Controller
public class vehicleController {
	
	@Autowired
	vehicleService vehicleservice;
	@Autowired
	userServices userserv;
	
	//done*****
	@GetMapping("/vehicleForm")
	public String addVehicleform(Model model) {
		Vehicle vehicle = new Vehicle();
		model.addAttribute("vehicle",vehicle);
		return "addPost";
	}
	
	
	
	@PostMapping("/add")  //<a href="/add">butt</a>
	public String saveUser(@Valid Vehicle vehicle,BindingResult result,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if(result.hasErrors()){
			return "addPost";
		}
         
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        vehicle.setPhotos(fileName);
         
        Vehicle savedUser = vehicleservice.addVehicle(vehicle);
 
        String uploadDir = "user-photos/" + savedUser.getAdvertiseId();
 
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
         
        return "redirect:/userHome";
	}
	
	
	@PostMapping("/updatedAdd")
	public String UpdatedVehicle(@ModelAttribute @Valid Vehicle vehicle,Model model) {
		Vehicle savedUser = vehicleservice.addVehicle(vehicle);
		return "redirect:/myadd";
	}
	
	
	
	//done*****
	@GetMapping("/")
	public String getAllvehile(Model model){
			List<Vehicle> Vlist= this.vehicleservice.getAllVehicle();
			System.out.println(Vlist);
			model.addAttribute("Vehicles",Vlist);
		   return "HomePage";
	}
	
	
	
	@GetMapping("/advertises")
	public String getvehile(Model model){
			List<Vehicle> Vlist= this.vehicleservice.getAllVehicle();
			System.out.println(Vlist);
			model.addAttribute("Vehicles",Vlist);
		   return "HomePage2";
	}
	
	
	
	//done*****
	@GetMapping("/myadd")
	public String getMyAdds(Model model, HttpSession session) {
		long userId=(long)session.getAttribute("userId");
		List<Vehicle> Vlist=this.vehicleservice.getVehicleByUserId(userId);
		model.addAttribute("Vehiclelist",Vlist);
		return "myaddsUser";
	}
	
	
	//done*****
		@GetMapping("/soldVehicles")
		public String getSoldVehicles(Model model, HttpSession session) {
		
			List<Vehicle> Vlist=this.vehicleservice.getVehicleByStatus(1);
			model.addAttribute("Vehiclelist",Vlist);
			return "soldVehicles";
		}
		
	
	

	//done 
	@GetMapping("/update/{advertiseId}")
	public String update( @PathVariable("advertiseId") long advertiseId, Model model,HttpSession session  ) {
		session.setAttribute("advertiseId", advertiseId);
		Vehicle vehicle=this.vehicleservice.updateVehicle(advertiseId);
		model.addAttribute("updateVehicle",vehicle);
		 return "updateAdd";
	}
	
	

	// done***
	@GetMapping("/viewDetails/{advertiseId}")
	public String getViewOfVehicle(@PathVariable("advertiseId") long advertiseId,Model model) {
		
		Vehicle vehicle = this.vehicleservice.getVehicle(advertiseId);
		model.addAttribute("vehicle",vehicle);
		
		return "vehicleDetails";
	}
	
	
	@GetMapping("/contact/{userId}")
	public String getSingle(@PathVariable(value="userId") long userId,Model model, HttpSession session ) {
		if(session.getAttribute("userName")!= null) {
		User user = userserv.getcontact(userId);
		session.setAttribute("vuserId", userId);
		model.addAttribute("User",user);
		return "contactDetail" ;
		}else {
			session.setAttribute("vuserId",userId);
			return "redirect:/bLogin";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/deleteVehicle/{advertiseId}")
	 public void  deletevehicle(@PathVariable(value="advertiseId") long advertiseId)
	 {
		 
		  this.vehicleservice.deleteVehicle(advertiseId) ;
	 }
	
	
	
	
	
	
	@GetMapping("/vehicle/{brand}")
	public String getVehicleByBrand(@PathVariable("brand") String brand, Model model) {
		List<Vehicle> Vlist=this.vehicleservice.getVehicleByBrand(brand);
		if(!Vlist.isEmpty()) {
		model.addAttribute("Vehicles", Vlist);
		
		return "HomePage";
		}
		return "redirect:/?message";
	}
	
	@GetMapping("/fuel/{fuelType}")
	public String getVehicleByFuelType(@PathVariable("fuelType") String fuelType, Model model) {
		List<Vehicle> Vlist=this.vehicleservice.getVehicleByFuelType(fuelType);
		if(!Vlist.isEmpty()) {
			model.addAttribute("Vehicles", Vlist);
			
			return "HomePage";
			}
			return "redirect:/?message1";
	}
	
	

}
