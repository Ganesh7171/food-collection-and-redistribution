package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Donation;
import jakarta.transaction.Transactional;
import repositories.DonationRepository;

@Service
@Transactional
public class DonationServiceImp implements DonationService {

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
	public List<Donation> listAllDonations() {
			
		List<Donation> donations = donationRepository.findAll();		
		return donations;
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
