package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import entities.Claims;
import entities.Donation;
import entities.Users;
import jakarta.transaction.Transactional;
import repositories.ClaimsRepository;
import repositories.DonationRepository;

@Service
@Transactional
public  class DonationServiceImp implements DonationService {

	@Autowired
	DonationRepository donationRepository;
	
	@Autowired
	ClaimsRepository claimsRepository;
	
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

	

	
	 @Override
	    public List<Donation> getMyDonations(Integer donorId) {
	        return donationRepository.findByDonorUserId(donorId);
	    }

	    public List<Claims> getClaimForDonation(Integer donationId) {
	        return claimsRepository.findByDonationDonationId(donationId);
	    }

	@Override
	public Page<Donation> listAllDonations(String search, String status, String meal,
			java.awt.print.Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}


	public long getDonationsMadeCount(Users donor) {
        if (donor == null) return 0;
        return donationRepository.countByDonor(donor);
    }

    /**
     * Get count of donations received by a user (as receiver)
     */
	@Override
    public long getDonationsReceivedCount(Users receiver) {
        if (receiver == null) return 0;
        return claimsRepository.countByUser(receiver);
    }

	public int countByUserAsReceiver(Users user) {
		// TODO Auto-generated method stub
		return 0;
	}


 


}
