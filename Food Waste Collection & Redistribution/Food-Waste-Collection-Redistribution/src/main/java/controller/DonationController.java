package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import entities.Donation;
import entities.Users;
import jakarta.servlet.http.HttpSession;
import services.DonationServiceImp;

@Controller
@RequestMapping("/fwc")
public class DonationController {

	@Autowired
	DonationServiceImp donationServiceImp;
	
	@GetMapping("/Db")
    public String showDashboard() {
        return "Dashboard";
    }
	
	@PostMapping("/submitDonation")
	public String addNewDonation(@ModelAttribute("donation") Donation newDonation,HttpSession session) {
		
			Users user =(Users)session.getAttribute("Users");
			newDonation.setUser(user);
		    donationServiceImp.addNewDonation(newDonation);
		    
		
			return "redirect:/fwc/donationSuccess/" + newDonation.getDonationId();	
	}
	
	@GetMapping("/donationSuccess/{donationId}")
	public String showSuccess(@PathVariable int donationId, Model model) {
		
	    model.addAttribute("donationId", donationId);
	    
	    return "DonationSuccess";
	}
	
	@GetMapping("/listAllDonations")
	public String getAllDonations(Model model){
		
		List<Donation> donationObject = donationServiceImp.listAllDonations();
		model.addAttribute("donations", donationObject);
		return "ShowAllDonations";
	}

	
	@GetMapping("/listMyDonations")
	public String getMyDonations(HttpSession session,Model model) {
		
		Users user = (Users) session.getAttribute("Users");
		System.out.println(user.getUserId());
		List<Donation> donations = donationServiceImp.getDonationsByUserId(user.getUserId());
		model.addAttribute("donations",donations);
	
		return "MyDonations";
	}

}
