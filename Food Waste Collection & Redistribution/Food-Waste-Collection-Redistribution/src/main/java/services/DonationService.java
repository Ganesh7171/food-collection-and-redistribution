package services;

import java.util.List;

import entities.Donation;

public interface DonationService {

	public void addNewDonation(Donation newDonation);
	public Donation getDonationById(int id);
	public Donation deleteDonationById();
	public List<Donation> listAllDonations();
	public void updateDonation();
	public List<Donation> getDonationsByUserId(int id);
	
	
}
