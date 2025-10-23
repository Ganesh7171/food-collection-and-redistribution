package repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.NgoBooking;
import entities.NgoDetails;
import entities.Users;


public interface NgoBookingRepository extends JpaRepository<NgoBooking, Long> {
	
	
	 List<NgoBooking> findTop5ByBookedByUserOrderByBookingDateDesc(Users bookedByUser);

	    // ✅ All bookings for a specific NGO
	    List<NgoBooking> findByNgo(NgoDetails ngo);

	    // ✅ Bookings for a particular NGO made by a specific user
	    List<NgoBooking> findByNgoAndBookedByUser(NgoDetails ngo, Users bookedByUser);
	    
	    List<NgoBooking> findByBookedByUser(Users loggedUser);
	    
	    
	    List<NgoBooking> findByBookedByUser_UserId(int userId);


		
	

	

}