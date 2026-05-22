package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import entities.Users;
import jakarta.servlet.http.HttpSession;
import services.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired()
	AuthService authService;
	
	@PostMapping("/logon")
	public String authenticateUser(@ModelAttribute("loginRequest") Users user,HttpSession session) {
		
		if(authService.authenticateUser(user)!=null) {
			
			session.setAttribute("Users", authService.authenticateUser(user));
			return "Dashboard";
		}
		else
			
			return "login";
		
	}
	

}
