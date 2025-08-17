package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import entities.Claims;
import entities.Donation;
import entities.Users;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/fwc")
public class ViewsController {
	
	@GetMapping("/SignUp")
    public String showSignUpPage(Model model) {
		model.addAttribute("newUser", new Users());
        return "SignUp"; 
    }
	
	@GetMapping("/donationPage")
	public String showDonationListingPage(HttpSession session,Model model) {
		
		/* Users userObject= (Users) session.getAttribute("Users"); */
		/* session.setAttribute("Users", userObject); */
		model.addAttribute("donation", new Donation());
		return "ListDonation";
		
	}
	
	@GetMapping("/Dashboard")
	public String showDashBoard() {
		
		
		return "Dashboard";
	}
	
	@GetMapping("/index1")
	public String getIndexPage() {
		return "index1";
	}
	
	@GetMapping("/DonationClaimForm")
	public String getDonationClaimForm(Model model) {
		model.addAttribute("claimForm", new Claims());	
		return "DonationClaimForm";
	}
	
	
}
