package entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ngo_booking")
public class NgoBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "ngo_id", nullable = false)
    private NgoDetails ngo;

    @ManyToOne
    @JoinColumn(name = "booked_by_user_id", nullable = false)
    private Users bookedByUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Slot slot;

    @Column(length = 255)
    private String otherDonations;
    
    private String mealType;

    public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	@Column(nullable = false)
    private LocalDateTime bookingDate = LocalDateTime.now();

    // ===== Enum for slots =====
    public enum Slot {
        Morning,
        Afternoon,
        Evening
    }

    // ===== Getters and Setters =====
    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }

    public NgoDetails getNgo() { return ngo; }
    public void setNgo(NgoDetails ngo) { this.ngo = ngo; }

    public Users getBookedByUser() { return bookedByUser; }
    public void setBookedByUser(Users bookedByUser) { this.bookedByUser = bookedByUser; }

    public Slot getSlot() { return slot; }
    public void setSlot(Slot slot) { this.slot = slot; }

    public String getOtherDonations() { return otherDonations; }
    public void setOtherDonations(String otherDonations) { this.otherDonations = otherDonations; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }
}
