package services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import entities.Donation;
import entities.Users;

public interface DonationService {

	public void addNewDonation(Donation newDonation);
	public Donation getDonationById(int id);
	public Donation deleteDonationById();
	public Page<Donation> listAllDonations(String search, String status, String meal, java.awt.print.Pageable pageable);
	public void updateDonation();
	
	Page<Donation> listAllDonations(String search, String status, String meal, String sortBy, String order, int page,
			int size);
	List<Donation> getMyDonations(Integer donorId);
	/**
	 * Get count of donations received by a user (as receiver)
	 */
	long getDonationsReceivedCount(Users receiver);
	
	
}
