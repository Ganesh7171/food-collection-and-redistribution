package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import entities.Claims;
import entities.Donation;
import repositories.ClaimsRepository;
import repositories.DonationRepository;

@Service
public class ClaimsServiceImp implements ClaimsService {

	@Autowired
	ClaimsRepository claimsRepository;

	@Autowired
	DonationRepository donationRepository;

	public void approveClaim(int claimId) {
				
		claimsRepository.updateClaimStatus("Approved", claimId);
		
	}

	@Override
	public void rejectClaim(int claimId) {
		claimsRepository.updateClaimStatus("Rejected", claimId);

	}

	@Override
	public void editClaim() {
		// TODO Auto-generated method stub

	}

	public List<Claims> getClaim(int userId, Sort sort) {
		return claimsRepository.findByUserUserId(userId, sort);
	}

	@Override
	public String submitClaim(Claims claim) {
		
			String subimmision="";

		/*
		 * claim.setClaimStatus("Waiting For Approval"); claimsRepository.save(claim);
		 */
			Optional<Claims> optionalClaim=  claimsRepository.claimChecker(claim.getDonation().getDonationId(),claim.getUser().getUserId());
		if (optionalClaim.isPresent()==false) {

			Optional<Donation> optionalDonation = donationRepository.findById(claim.getDonation().getDonationId());
			if (optionalDonation.isPresent()) {

				Donation donation = optionalDonation.get();
				claim.setDonation(donation);
				claim.setClaimStatus("Awaiting Approval");
				claim.setClaimTime(LocalDateTime.now());

				if (donation.getClaims() == null) {

					donation.setClaims(new ArrayList<>());
				}

				donation.getClaims().add(claim);
				donationRepository.save(donation);
				
				subimmision="Request Placed successfully";
				
			}

		}
		
		else {
			
			subimmision="You have already placed a request for this Donation";
		}
		return subimmision;
		

	}
	
	public List<Claims> getRequestsByOthersOnMyDonations(int myUserId) {
	    return claimsRepository.findRequestsByOthersOnMyDonations(myUserId);
	}

	


}
