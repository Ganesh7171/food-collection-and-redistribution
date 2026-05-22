package repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import entities.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation,String> {

	
	List<Donation> findByUserUserId(int userId);

}
