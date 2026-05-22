package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "volunteer_details")
public class VolunteerDetails {

    @Id
    @Column(name = "volunteer_id")
    private int volunteerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "volunteer_id")
    private Users user;

    @Column(name = "skills")
    private String skills;

    @Column(name = "availability")
    private String availability;

    // Getters & Setters
    public int getVolunteerId() { return volunteerId; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
}
