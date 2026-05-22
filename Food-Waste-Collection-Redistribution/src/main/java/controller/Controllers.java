package controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import entities.NgoDetails;
import entities.Users;
import entities.VolunteerDetails;
import repositories.NgoDetailsRepository;
import repositories.PrintMessage;
import repositories.UserRepository;

@Controller
@RequestMapping("/fwc")
public class Controllers {

	@Autowired
	PrintMessage pm;
	
	@Autowired
	UserRepository logon;
	
	@Autowired
	NgoDetailsRepository ngoRepository;
	
	
	@GetMapping("/gm")
	 public ResponseEntity<String>  printMessage(){
		
		return ResponseEntity.status(HttpStatus.OK)
                .body(pm.printMe());
		
	}
	
	
	/*
	 * @PostMapping("/saveUser") public String
	 * saveNewUser(@ModelAttribute("newUser") Users
	 * user,@ModelAttribute("details")Model model) { model.addAttribute("users",
	 * user.getUsername()); logon.save(user); return "login";
	 * 
	 * }
	 */
			
	
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("newUser") Users user) {
		
		if ("Restaurant".equals(user.getUserType())) {
		    user.setVolunteerDetails(null);
		    user.setNgoDetails(null);

		}

		if ("NGO".equals(user.getUserType())) {
		    user.setVolunteerDetails(null); // explicitly null
		    if (user.getNgoDetails() == null) user.setNgoDetails(new NgoDetails());
		    user.getNgoDetails().setUser(user);
		}

		if ("Volunteer".equals(user.getUserType())) {
		    user.setNgoDetails(null); // explicitly null
		    if (user.getVolunteerDetails() == null) user.setVolunteerDetails(new VolunteerDetails());
		    user.getVolunteerDetails().setUser(user);
		}

	    logon.save(user);
	    return "redirect:/fwc/login";
	}

	
	
	@GetMapping("/ngo/{id}/image")
	public ResponseEntity<byte[]> getNgoImage(@PathVariable int id) {
	    NgoDetails ngo = ngoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("NGO not found"));

	    if (ngo.getImageData() == null) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(ngo.getImageType()))
	            .body(ngo.getImageData());
	}

	
	
	
	
	
	
	
	
	  
		@GetMapping("/login")
	    public String showLoginPage() {
	        return "login"; 
	    }
		
		
	 
}
