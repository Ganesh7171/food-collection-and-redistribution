package services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import entities.Donation;

public interface DonationService {

	public void addNewDonation(Donation newDonation);
	public Donation getDonationById(int id);
	public Donation deleteDonationById();
	public Page<Donation> listAllDonations(String search, String status, String meal, java.awt.print.Pageable pageable);
	public void updateDonation();
	public List<Donation> getDonationsByUserId(int id);
	Page<Donation> listAllDonations(String search, String status, String meal, String sortBy, String order, int page,
			int size);
	
	
}
