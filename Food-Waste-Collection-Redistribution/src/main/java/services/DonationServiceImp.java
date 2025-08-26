package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import entities.Donation;
import jakarta.transaction.Transactional;
import repositories.DonationRepository;

@Service
@Transactional
public  class DonationServiceImp implements DonationService {

	@Autowired
	DonationRepository donationRepository;
	
	@Override
	public Donation getDonationById(int i) {
		
		
		return null;
	}

	@Override
	public Donation deleteDonationById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Donation> listAllDonations(
	        String search,
	        String status,
	        String meal,
	        String sortBy,
	        String order,
	        int page,
	        int size
	) {
	    Sort sort = order.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    // Example: You can expand this with custom filtering
	    if (search != null && !search.isEmpty()) {
	        try {
	            int id = Integer.parseInt(search);
	            return donationRepository.findByDonationIdContaining(id, pageable);
	        } catch (NumberFormatException e) {
	            // fallback when search is not a number
	            return donationRepository.findAll(pageable);
	        }
	    }

	    return donationRepository.findAll(pageable);
	}

	

	@Override
	public void updateDonation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewDonation(Donation addNewDonation) {
		
		donationRepository.save(addNewDonation);
		
	}

	
	public List<Donation> getDonationsByUserId(int id) {
		
		
		return donationRepository.findByUserUserId(id);
	}

	@Override
	public Page<Donation> listAllDonations(String search, String status, String meal,
			java.awt.print.Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}




 


}
