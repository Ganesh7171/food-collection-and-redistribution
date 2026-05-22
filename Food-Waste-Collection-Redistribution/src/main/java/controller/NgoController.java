package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import entities.NgoBooking;
import entities.NgoDetails;
import entities.Users;
import jakarta.servlet.http.HttpSession;
import repositories.NgoBookingRepository;
import repositories.NgoDetailsRepository;
import services.NgoService;
import services.UserService;


@Controller
@RequestMapping("/ngo")
public class NgoController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	NgoService ngoService;
	
	@Autowired
	NgoDetailsRepository ngoDetailsRepository;
	
	@Autowired
	NgoBookingRepository ngoBookingRepository;
	
	@PostMapping("/book")
    public String bookNgoSlot(@ModelAttribute NgoBooking booking, @RequestParam int ngoId,HttpSession session) {
        // Get currently logged-in user
		
        Users user = (Users) session.getAttribute("Users");
        System.out.println(booking.getBookingDate());
        // Fetch the NGO
        NgoDetails ngo = ngoService.getNgoById(ngoId).orElse(null);
        System.out.println(ngo);
        System.out.println(user);
        if (ngo != null && user != null) {
            booking.setNgo(ngo);
            System.out.println(booking);
            booking.setBookedByUser(user);
            ngoBookingRepository.save(booking);
        }

        return "redirect:/ngo/ngos-list";
    }

	@GetMapping("/ngos-list")
	public String showNgos(Model model,HttpSession session) {
		  Users user = (Users) session.getAttribute("Users");
		 if (user == null) return "redirect:/fwc/login";
	    List<NgoDetails> ngos = ngoService.getAllNgos();

	    Map<Integer, Map<NgoBooking.Slot, Boolean>> slotAvailability = new HashMap<>();
	    for (NgoDetails ngo : ngos) {
	        Map<NgoBooking.Slot, Boolean> availability = new HashMap<>();
	        for (NgoBooking.Slot slot : NgoBooking.Slot.values()) {
	            boolean booked = ngo.getBookings().stream().anyMatch(b -> b.getSlot() == slot);
	            availability.put(slot, !booked);
	        }
	        slotAvailability.put(ngo.getNgoId(), availability);
	    }

	    model.addAttribute("ngos", ngos);
	    model.addAttribute("slotAvailability", slotAvailability);
	    model.addAttribute("slots", NgoBooking.Slot.values());

	    return "NGOList";
	}


	 @GetMapping("/ngo-image/{ngoId}")
	 public ResponseEntity<byte[]> getNgoImage(@PathVariable int ngoId) {
	     NgoDetails ngo = ngoService.getNgoById(ngoId)
	             .orElseThrow(() -> new RuntimeException("NGO not found"));

	     if (ngo.getImageData() == null || ngo.getImageData().length == 0) {
	         return ResponseEntity.notFound().build();
	     }

	     HttpHeaders headers = new HttpHeaders();
	     // 👇 Default content type (you can change to image/png if needed)
	     headers.setContentType(MediaType.IMAGE_JPEG);

	     return new ResponseEntity<>(ngo.getImageData(), headers, HttpStatus.OK);
	 }
	 
	 
		/*
		 * @GetMapping("/{ngoId}/calendar")
		 * 
		 * @ResponseBody public List<Map<String, Object>> getNgoBookings(@PathVariable
		 * int ngoId, HttpSession session) { Users loggedUser = (Users)
		 * session.getAttribute("Users");
		 * 
		 * 
		 * NgoDetails ngo = ngoDetailsRepository.findById(ngoId) .orElseThrow(() -> new
		 * RuntimeException("NGO not found"));
		 * 
		 * List<NgoBooking> bookings;
		 * 
		 * // ✅ If the logged-in user is an NGO admin, show all bookings for that NGO if
		 * (loggedUser.getNgoDetails() != null && loggedUser.getNgoDetails().getNgoId()
		 * == ngoId) {
		 * 
		 * 
		 * System.out.
		 * println("*************************************inside if******************88"
		 * ); bookings = ngoBookingRepository.findByNgo(loggedUser.getNgoDetails());
		 * 
		 * 
		 * } else { // ✅ Regular user: show only their bookings for this NGO System.out.
		 * println("*************************************inside else******************88"
		 * ); bookings = ngoBookingRepository.findByBookedByUser(loggedUser); }
		 * 
		 * // ✅ Convert bookings into FullCalendar event format List<Map<String,
		 * Object>> events = new ArrayList<>(); for (NgoBooking b : bookings) {
		 * Map<String, Object> event = new HashMap<>(); event.put("title",
		 * b.getMealType() + " - " + b.getSlot().name()); event.put("start",
		 * b.getBookingDate().toString()); // Must be LocalDate or LocalDateTime
		 * event.put("allDay", true); events.add(event); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * return events; }
		 * 
		 */
	 
	 
	 @GetMapping("/{ngoId}/calendar")
	 @ResponseBody
	 public List<Map<String, Object>> getNgoBookings(@PathVariable int ngoId, HttpSession session) {
		 System.out.println("Inside NGO admin branch");
	     Users loggedUser = (Users) session.getAttribute("Users");
	     if (loggedUser == null) {
	         throw new RuntimeException("User not logged in");
	     }

	     NgoDetails ngo = ngoDetailsRepository.findById(ngoId)
	             .orElseThrow(() -> new RuntimeException("NGO not found"));

	     List<NgoBooking> bookings;

	     // NGO admin: see all bookings for their NGO
	     if (loggedUser.getNgoDetails() != null 
	    	        && Integer.valueOf(loggedUser.getNgoDetails().getNgoId()).equals(Integer.valueOf(ngoId))) {
	    	    System.out.println("Inside NGO admin branch");
	    	    bookings = ngoBookingRepository.findByNgo(loggedUser.getNgoDetails());
	    	} else {
	    	    System.out.println("Inside volunteer / regular user branch");
	    	    bookings = ngoBookingRepository.findByBookedByUser(loggedUser);
	    	}


	     // Convert bookings into FullCalendar event format
	     List<Map<String, Object>> events = new ArrayList<>();
	     for (NgoBooking b : bookings) {
	         Map<String, Object> event = new HashMap<>();
	         String ngoName = b.getNgo() != null ? b.getNgo().getOrganizationName() : "NGO";
	         event.put("title", ngoName + " - " + b.getMealType() + " - " + b.getSlot().name());
	         event.put("start", b.getBookingDate().toString());
	         event.put("allDay", true);
	         events.add(event);
	     }

	     return events;
	 }



}
