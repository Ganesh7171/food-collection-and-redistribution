package entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ngo_details")
public class NgoDetails {

    @Id
    @Column(name = "ngo_id")
    private int ngoId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ngo_id")
    private Users user;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "donation_types")
    private String donationTypes;

    @Column(name = "visiting_hours")
    private String visitingHours;

    @Column(name = "slot_booking_enabled")
    private Boolean slotBookingEnabled = true;

    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @Column(name = "image_type")
    private String imageType;
    
    public List<NgoImage> getImages() {
		return images;
	}
	public void setImages(List<NgoImage> images) {
		this.images = images;
	}
	public void setNgoId(int ngoId) {
		this.ngoId = ngoId;
	}
	@OneToMany(mappedBy = "ngo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NgoImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "ngo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NgoBooking> bookings = new ArrayList<>();

    // getters and setters
    public List<NgoBooking> getBookings() { return bookings; }
    public void setBookings(List<NgoBooking> bookings) { this.bookings = bookings; }


    // Bi-directional setter
    public void setUser(Users user) {
        this.user = user;
        if (user.getNgoDetails() != this) {
            user.setNgoDetails(this);
        }
    }

    // Getters & Setters
    public int getNgoId() { return ngoId; }
    public Users getUser() { return user; }
    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
    public String getDonationTypes() { return donationTypes; }
    public void setDonationTypes(String donationTypes) { this.donationTypes = donationTypes; }
    public String getVisitingHours() { return visitingHours; }
    public void setVisitingHours(String visitingHours) { this.visitingHours = visitingHours; }
    public Boolean getSlotBookingEnabled() { return slotBookingEnabled; }
    public void setSlotBookingEnabled(Boolean slotBookingEnabled) { this.slotBookingEnabled = slotBookingEnabled; }
    public byte[] getImageData() { return imageData; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }
    public String getImageType() { return imageType; }
    public void setImageType(String imageType) { this.imageType = imageType; }
}
