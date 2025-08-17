package entities;

import java.time.LocalDateTime;
import java.util.Date;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="Claims")
public class Claims {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int claimId;
	
	@ManyToOne()
	@JoinColumn(name="donation_id", referencedColumnName="donation_id")
	private Donation donation;
	
	@ManyToOne()
	@JoinColumn(name="claimer_user_id", referencedColumnName="user_id")
	private Users user;
	
	
	private String claimerName;
    private String contactNumber;
    private String claimedAddress;
    private String googlePlusCode;
    private LocalDateTime claimTime;
    private String claimStatus;
    private String requestFor;
    
    
    
	public Donation getDonation() {
		return donation;
	}
	public Users getUser() {
		return user;
	}
	public void setDonation(Donation donation) {
		this.donation = donation;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getRequestFor() {
		return requestFor;
	}
	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}
	public String getClaimerName() {
		return claimerName;
	}
	public void setClaimerName(String claimerName) {
		this.claimerName = claimerName;
	}
	public int getClaimId() {
		return claimId;
	}

	public String getContactNumber() {
		return contactNumber;
	}
	public String getClaimedAddress() {
		return claimedAddress;
	}
	public String getGooglePlusCode() {
		return googlePlusCode;
	}
	public LocalDateTime getClaimTime() {
		return claimTime;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public void setClaimedAddress(String claimedAddress) {
		this.claimedAddress = claimedAddress;
	}
	public void setGooglePlusCode(String googlePlusCode) {
		this.googlePlusCode = googlePlusCode;
	}
	public void setClaimTime(LocalDateTime claimTime) {
		this.claimTime = claimTime;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
    
    
    
    

}
