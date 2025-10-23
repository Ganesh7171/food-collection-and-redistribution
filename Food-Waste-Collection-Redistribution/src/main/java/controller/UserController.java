package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import entities.NgoBooking;
import entities.NgoDetails;
import entities.Users;
import jakarta.servlet.http.HttpSession;
import repositories.NgoBookingRepository;
import services.NgoService;
import services.UserService;

@Controller
@RequestMapping("/auth")
public class UserController {
	
	@Autowired()
	UserService userService;
	
	@Autowired 
	NgoService ngoService;
	
	@Autowired
	NgoBookingRepository ngoBookingRepository;
	
	@PostMapping("/logon")
	public String authenticateUser(@ModelAttribute("loginRequest") Users user,HttpSession session) {
		
		if(userService.authenticateUser(user)!=null) {
			
			session.setAttribute("Users", userService.authenticateUser(user));
			return "redirect:/fwc/Dashboard";
		}
		else
			
			return "login";
		
	}
	
	 @PostMapping("/updateUser")
	    public String updateUser(@ModelAttribute("user") Users user,
	                             @RequestParam(value = "ngoImage", required = false) MultipartFile file) {

	        try {
	            userService.updateUserProfile(user, file);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return "redirect:/fwc/myaccount";
	    }
	 
	 
	
	 @PostMapping("/updateNgo")
	 public String updateNgoProfile(@ModelAttribute("ngoDetails") NgoDetails ngoDetails,
	                                @RequestParam("file") MultipartFile file,
	                                HttpSession session,
	                                Model model) {
	     Users currentUser = (Users) session.getAttribute("Users");
	     if (currentUser == null) {
	         return "redirect:/auth/login";
	     }

	     try {
	         userService.updateNgoProfile(currentUser.getUserId(), ngoDetails, file);
	         System.out.print("after upload");
	         System.out.println(file);
	         session.setAttribute("success", "NGO profile updated successfully!");

	         // Refresh the user object after update
	         Users updatedUser = userService.authenticateUser(currentUser);
	                                          
	         session.setAttribute("Users", updatedUser);
	         model.addAttribute("user", updatedUser);

	         
	     } catch (Exception e) {
	    	 
	    	 System.out.println(e);
	         session.setAttribute("error", "Error updating profile: " + e.getMessage());
	         model.addAttribute("user", currentUser); // fallback
	     }

	     return "Dashboard";
	 }
	 

	 @ResponseBody
	 @GetMapping("/user/calendar")
	    public List<Map<String, Object>> getUserBookings(HttpSession session) {
	        Users loggedUser = (Users) session.getAttribute("Users");

	        if (loggedUser == null) {
	            return Collections.emptyList();
	        }

	        // ✅ Fetch all bookings made by this user
	        List<NgoBooking> bookings = ngoBookingRepository.findByBookedByUser(loggedUser);

	        List<Map<String, Object>> events = new ArrayList<>();
	        for (NgoBooking b : bookings) {
	            Map<String, Object> event = new HashMap<>();
	            String ngoName = b.getNgo() != null ? b.getNgo().getOrganizationName() : "NGO";
	            event.put("title", ngoName + " - " + b.getMealType() + " - " + b.getSlot().name());
	            event.put("start", b.getBookingDate().toLocalDate().toString());
	            event.put("allDay", true);
	            events.add(event);
	        }
	        return events;
	    }

}
