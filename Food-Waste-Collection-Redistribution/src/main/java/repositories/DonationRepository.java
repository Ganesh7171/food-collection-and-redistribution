package repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  // ✅ Correct import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Donation;
import entities.Users;

@Repository
public interface DonationRepository extends JpaRepository<Donation,Integer> {

	
	List<Donation> findByDonorUserId(int userId);

	Page<Donation> findByDonationIdContaining(int id, Pageable pageable);

	Page<Donation> findAll(Pageable pageable);

	Page<Donation> findByMealType(String meal, Pageable pageable);

	Page<Donation> findByStatus(String status, Pageable pageable);
	
	long countByDonor(Users donor);

	


	
	Iterable<Donation> findTop5ByDonorOrderByClaimTimeDesc(Users donor);

   

	
}
