package services;

import entities.Claims;


public interface ClaimsService {


	public String submitClaim(Claims claim);

	public void editClaim();
	public Claims approveClaim(int claimId);
	Claims rejectClaim(int claimId);
	
	
}
