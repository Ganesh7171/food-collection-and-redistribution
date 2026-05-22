package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import entities.Claims;
import entities.Donation;
import entities.NgoBooking;
import entities.NgoDetails;
import entities.Users;
import jakarta.servlet.http.HttpSession;
import repositories.DonationRepository;
import repositories.NgoBookingRepository;
import services.DonationServiceImp;
import services.NgoService;

@Controller
@RequestMapping("/fwc")
public class ViewsController {
	 @Autowired
	 private NgoService ngoService;
	 
	 
	 @Autowired
	 private DonationServiceImp donationService;
	 
		/*
		 * @Autowired private ClaimsServiceImp claimsServiceImp;
		 */
	 
	 @Autowired
	 private DonationRepository donationRepository;
	 @Autowired
	 private NgoBookingRepository ngoBookingRepository;
	 
	@GetMapping("/SignUp")
    public String showSignUpPage(Model model) {
		Users newUser = new Users();
		 newUser.setNgoDetails(new NgoDetails());  // 
		  //  newUser.setVolunteerDetails(new VolunteerDetails()); // optional if volunteer fields exist
		    model.addAttribute("newUser", newUser);
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
    public String dashboard(Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("Users");

        // Total donations made and received
        long totalDonationsMade = donationRepository.countByDonor(user);
        long totalDonationsReceived = donationService.getDonationsReceivedCount(user);

        // Recent activities
        List<String> recentActivities = new ArrayList<>();

        // Last 5 donations
        donationRepository.findTop5ByDonorOrderByClaimTimeDesc(user)
        .forEach(d -> recentActivities.add(
            "Donation: " + d.getMealType() + " at " + d.getAddress() 
            + " on " + (d.getClaimTime() != null ? d.getClaimTime().toLocalDate() : "N/A")
        ));


        // Last 5 bookings
        List<NgoBooking> recentBookings = ngoBookingRepository.findTop5ByBookedByUserOrderByBookingDateDesc(user);
        recentBookings.forEach(b -> recentActivities.add(
            "Booked slot: " + b.getSlot().name() + " on " + b.getBookingDate()
        ));

        // Pass to view
        model.addAttribute("user", user);
        model.addAttribute("totalDonationsMade", totalDonationsMade);
        model.addAttribute("totalDonationsReceived", totalDonationsReceived);
        model.addAttribute("recentActivities", recentActivities);

        return "dashboard"; // Thymeleaf template name
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
	
	@GetMapping("/myaccount")
	public String myAccountPage(Model model, HttpSession session) {
		
	    Users user = (Users) session.getAttribute("Users");
	    model.addAttribute("ngoDetails", user.getNgoDetails());
	    
	    if (user == null) return "redirect:/fwc/login";

	    long made = (int) donationService.getDonationsMadeCount(user);
	  long received = donationService.getDonationsReceivedCount(user);

	    model.addAttribute("user", user);
	    model.addAttribute("donationsMade", made);
	    model.addAttribute("donationsReceived", received);
	    return "myaccount";
	}

	/*
	 * @GetMapping("/mycalendar") public String myCalendar(Model model, HttpSession
	 * session) { Users user = (Users) session.getAttribute("Users");
	 * 
	 * model.addAttribute("ngo", user.getNgoDetails()); return "Mycalendar"; }
	 */
	
	
	@GetMapping("/mycalendar")
	public String showCalendar(HttpSession session, Model model) {
	    Users loggedUser = (Users) session.getAttribute("Users");
	    model.addAttribute("loggedUser", loggedUser);

	    if (loggedUser != null && loggedUser.getNgoDetails() != null) {
	        model.addAttribute("ngo", loggedUser.getNgoDetails());
	    } else {
	        model.addAttribute("ngo", null);
	    }

	    return "MyCalendar";
	}

}
