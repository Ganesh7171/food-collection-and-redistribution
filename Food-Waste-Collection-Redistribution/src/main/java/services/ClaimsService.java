package services;

import entities.Claims;


public interface ClaimsService {


	public String submitClaim(Claims claim);

	public void editClaim();
	public void approveClaim(int claimId);
	void rejectClaim(int claimId);
	
	
}
