package controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entities.Donation;
import entities.Users;
import jakarta.servlet.http.HttpSession;
import services.DonationServiceImp;

@Controller
@RequestMapping("/fwc")
public class DonationController {

	@Autowired
	DonationServiceImp donationService;
	
	@GetMapping("/Db")
    public String showDashboard() {
        return "Dashboard";
    }
	
	@PostMapping("/submitDonation")
	public String addNewDonation(@ModelAttribute("donation") Donation newDonation,HttpSession session) {
		
			Users user =(Users)session.getAttribute("Users");
			newDonation.setUser(user);
		    donationService.addNewDonation(newDonation);
		    
			return "redirect:/fwc/donationSuccess/" + newDonation.getDonationId();	
	}
	
	@GetMapping("/donationSuccess/{donationId}")
	public String showSuccess(@PathVariable int donationId, Model model) {
		
	    model.addAttribute("donationId", donationId);
	    
	    return "DonationSuccess";
	}
	
	/*
	 * @GetMapping("/listAllDonations") public String getAllDonations(Model model){
	 * 
	 * List<Donation> donationObject = donationServiceImp.listAllDonations();
	 * model.addAttribute("donations", donationObject);
	 * model.addAttribute("claimForm", new Claims()); return "ShowAllDonations"; }
	 */

	@GetMapping("/listAllDonations")
	public String listAllDonations(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "6") int size,
	        @RequestParam(defaultValue = "donationId") String sortBy,
	        @RequestParam(defaultValue = "desc") String order,
	        @RequestParam(required = false) String search,
	        @RequestParam(required = false) String status,
	        @RequestParam(required = false) String meal,
	        Model model
	) {
	    Page<Donation> donationPage = donationService.listAllDonations(
	            search, status, meal, sortBy, order, page, size
	    );

	    model.addAttribute("donations", donationPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", donationPage.getTotalPages());

	    // pass filters back to view
	    model.addAttribute("search", search);
	    model.addAttribute("status", status);
	    model.addAttribute("meal", meal);
	    model.addAttribute("sortBy", sortBy);
	    model.addAttribute("order", order);

	    return "ShowAllDonations";
	}

	
	@GetMapping("/listMyDonations")
	public String getMyDonations(HttpSession session,Model model) {
		
		Users user = (Users) session.getAttribute("Users");
		System.out.println(user.getUserId());
		List<Donation> donations = donationService.getDonationsByUserId(user.getUserId());
		model.addAttribute("donations",donations);
	
		return "MyDonations";
	}

	
}
