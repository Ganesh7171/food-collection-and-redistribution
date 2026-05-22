package controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import entities.Donation;
import entities.Users;
import jakarta.servlet.http.HttpSession;
import services.ClaimsServiceImp;


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


	@GetMapping("/MyApprovals1")
	public String viewClaimsForApproval(Model model,HttpSession session) {
		Users user = (Users)session.getAttribute("Users");
	    List<Claims> claims = claimsServiceImp.getRequestsByOthersOnMyDonations(user.getUserId());
	    
	    Map<Donation, List<Claims>> grouped = claims.stream()
	        .collect(Collectors.groupingBy(Claims::getDonation));
	    
	    model.addAttribute("claimsGroupedByDonation", grouped);
	    return "/MyApprovals"; // your Thymeleaf template
	}
	
	
	    @GetMapping("/MyApprovals")
	    public String viewApprovals(Model model,HttpSession session) {
	        // ✅ Fetch all claims from DB
	    	Users user = (Users)session.getAttribute("Users");
	        List<Claims> allClaims = claimsServiceImp.getRequestsByOthersOnMyDonations(user.getUserId());

	        // ✅ Separate pending and actioned (approved/rejected/refer-back)
	        List<Claims> pendingClaims = allClaims.stream()
	                .filter(c -> c.getClaimStatus().equalsIgnoreCase("Awaiting Approval") ||
	                             c.getClaimStatus().equalsIgnoreCase("Pending"))
	                .collect(Collectors.toList());

	        List<Claims> actionedClaims = allClaims.stream()
	                .filter(c -> !c.getClaimStatus().equalsIgnoreCase("Awaiting Approval") &&
	                             !c.getClaimStatus().equalsIgnoreCase("Pending"))
	                .collect(Collectors.toList());

	        // ✅ Group by Donation ID
	        Map<Integer, List<Claims>> groupedPending = pendingClaims.stream()
	                .collect(Collectors.groupingBy(c -> c.getDonation().getDonationId(),
	                        LinkedHashMap::new, Collectors.toList()));

	        Map<Integer, List<Claims>> groupedActioned = actionedClaims.stream()
	                .collect(Collectors.groupingBy(c -> c.getDonation().getDonationId(),
	                        LinkedHashMap::new, Collectors.toList()));

	        // ✅ Send data to Thymeleaf
	        model.addAttribute("groupedPending", groupedPending);
	        model.addAttribute("groupedActioned", groupedActioned);
	        model.addAttribute("allClaims", allClaims);

	        return "MyApprovals"; // → Thymeleaf page name
	    }
	
	
	


	@PostMapping("/action")
	public String handleClaimAction(
	        @RequestParam("id") int claimId,
	        @RequestParam("comment") String comment,
	        @RequestParam("action") String action) {

	    String status;

	    switch (action.toLowerCase()) {
	        case "approve":
	            status = "Approved";
	            break;
	        case "reject":
	            status = "Rejected";
	            break;
	        case "refer-back":
	            status = "Refer_Back";
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid action: " + action);
	    }

	    claimsServiceImp.updateClaimStatus(claimId, status, comment);
	    return "redirect:/claims/MyApprovals";
	}


}
