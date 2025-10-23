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
import jakarta.transaction.Transactional;
import repositories.ClaimsRepository;
import repositories.DonationRepository;

@Service
public class ClaimsServiceImp implements ClaimsService {

	@Autowired
	ClaimsRepository claimsRepository;

	@Autowired
	DonationRepository donationRepository;
	
	
	public void updateClaimStatus(int claimId, String status, String comment) {
	    Claims claim = claimsRepository.findById(claimId).orElseThrow(() -> new RuntimeException("Claim not found"));
	    claim.setClaimStatus(status);
	    claim.setComment(comment); // add comment field in your entity
	    //claim.setUpdatedAt(LocalDateTime.now());
	    claimsRepository.save(claim);
	    int donationId = claim.getDonation().getDonationId();

        // 1. Reject all other claims for this donation
        List<Claims> allClaims = claimsRepository.findByDonationDonationId(donationId);
        for (Claims c : allClaims) {
            if (c.getClaimId() == claimId) {
                c.setClaimStatus("Approved");
            } else {
                c.setClaimStatus("Rejected");
            }
            claimsRepository.save(c);
        }

	    
	}


	/*
	 * @Transactional public Claims updateClaimStatus(int claimId) { Claims claim =
	 * claimsRepository.findById(claimId).orElseThrow(); int donationId =
	 * claim.getDonation().getDonationId();
	 * 
	 * // 1. Reject all other claims for this donation List<Claims> allClaims =
	 * claimsRepository.findByDonationDonationId(donationId); for (Claims c :
	 * allClaims) { if (c.getClaimId() == claimId) { c.setClaimStatus("Approved"); }
	 * else { c.setClaimStatus("Rejected"); } claimsRepository.save(c); }
	 * 
	 * return claim; }
	 */


	@Override
	public Claims rejectClaim(int claimId) {
        Claims claim = claimsRepository.findById(claimId).orElseThrow();
        claim.setClaimStatus("Rejected");
        return claimsRepository.save(claim);
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
	    return claimsRepository.findClaimsByDonorId(myUserId);
	}


	@Override
	public Claims approveClaim(int claimId) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
