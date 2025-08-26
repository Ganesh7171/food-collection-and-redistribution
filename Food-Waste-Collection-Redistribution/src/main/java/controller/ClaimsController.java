package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import entities.Claims;
import entities.Users;
import jakarta.servlet.http.HttpSession;
import services.ClaimsServiceImp;
import services.DonationService;


@Controller
@RequestMapping("/claims")
public class ClaimsController {

	@Autowired
	ClaimsServiceImp claimsServiceImp;
	
	/*
	 * @Autowired DonationService donationService;
	 */
	
	@PostMapping("/submitClaim")
	@ResponseBody
	public String submitClaim(@ModelAttribute("claimForm")Claims claim ) {
		
			claim.getDonation().getDonationId();
			System.out.println(claim.getDonation().getDonationId());
			return claimsServiceImp.submitClaim(claim);
		
		//return "redirect:/fwc/listAllDonations";
	}
	
	@GetMapping("/MyClaimRequests")
	public String getMyClaimsPage(
	        @RequestParam(defaultValue = "claimTime") String sortField,
	        @RequestParam(defaultValue = "asc") String sortDir,
	        Model model, HttpSession session) {

	    Users user = (Users) session.getAttribute("Users");	
	    
	    if (user == null) {
	        return "redirect:/fwc/login";
	    }

	    Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
	   
	    List<Claims> claimsList = claimsServiceImp.getClaim(user.getUserId(), sort);

	    model.addAttribute("myClaimRequests", claimsList);
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

	    return "MyClaims";
	}

	@GetMapping("/MyApprovals")
	public String  getRequestsOnMyDonations(Model model, HttpSession session) {
		
		Users user = (Users)session.getAttribute("Users");
		List<Claims> claims = claimsServiceImp.getRequestsByOthersOnMyDonations(user.getUserId());
    
		model.addAttribute("calimsRequests", claims);
		
		return "MyApprovals";
    
}

	@PostMapping("/approve")
	public String approveClaim(@ModelAttribute("id") int claimId) {
		
		claimsServiceImp.approveClaim(claimId);
		
		
		return "redirect:/claims/MyApprovals";
	}
	
}
