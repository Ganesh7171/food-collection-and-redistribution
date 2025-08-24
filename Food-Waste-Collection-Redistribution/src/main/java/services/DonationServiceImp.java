package services;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import entities.Donation;
import jakarta.transaction.Transactional;
import repositories.DonationRepository;

@Service
@Transactional
public abstract class DonationServiceImp implements DonationService {

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

	 public Page<Donation> listAllDonations(String search, String status, String meal, org.springframework.data.domain.Pageable pageable) {
	        if (search != null && !search.isEmpty()) {
	            return donationRepository.findByDonationIdContaining(search, (org.springframework.data.domain.Pageable) pageable);
	        } 
	        else if (status != null && !status.isEmpty()) {
	            return donationRepository.findByStatus(status, (org.springframework.data.domain.Pageable) pageable);
	        } 
	        else if (meal != null && !meal.isEmpty()) {
	            return donationRepository.findByMealType(meal, pageable);
	        } 
	        else {
	            return donationRepository.findAll(pageable);
	        }
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

 


}
