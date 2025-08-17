package entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="Food_Donation")
public class Donation {
	
	
	@Id
	@Column(name = "donation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
		private int donationId;
		private String mealType;
		private String address;
		private String status;
		private String googlePlusCode;
		private LocalDateTime claimTime;
		
		@OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, orphanRemoval = true)
		private List<Claims> claims;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private Users user;
	
	

	public List<Claims> getClaims() {
		return claims;
	}
	public void setClaims(List<Claims> claims) {
		this.claims = claims;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public int getDonationId() {
		return donationId;
	}
	public void setDonationId(int donationId) {
		this.donationId = donationId;
	}

	public String getMealType() {
		return mealType;
	}
	public String getAddress() {
		return address;
	}
	public String getStatus() {
		return status;
	}
	public String getGooglePlusCode() {
		return googlePlusCode;
	}
	public LocalDateTime getClaimTime() {
		return claimTime;
	}
	
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setGooglePlusCode(String googlePlusCode) {
		this.googlePlusCode = googlePlusCode;
	}
	public void setClaimTime(LocalDateTime claimTime) {
		this.claimTime = claimTime;
	}
	


}
