package controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entities.Users;
import repositories.AuthRepository;
import repositories.PrintMessage;

@Controller
@RequestMapping("/fwc")
public class Controllers {

	@Autowired
	PrintMessage pm;
	
	@Autowired
	AuthRepository logon;
	
	
	@GetMapping("/gm")
	 public ResponseEntity<String>  printMessage(){
		
		return ResponseEntity.status(HttpStatus.OK)
                .body(pm.printMe());
		
	}
	
	
	@PostMapping("/saveUser")
	public String saveNewUser(@ModelAttribute("newUser") Users user,Model model) {
		model.addAttribute("users", user.getUsername());
		logon.save(user);
		return "DashBoard";
		
	}
			
	  
		@GetMapping("/login")
	    public String showLoginPage() {
	        return "login"; 
	    }
		
		
	 
}
