package repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entities.Claims;
import entities.Users;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ClaimsRepository extends JpaRepository<Claims,Integer> {
	
	
	List<Claims> findByUserUserId(int userId, Sort sort);

	List<Claims> findByDonationDonationId(int donationId);

	@Query(value="select * from claims where donation_id=:dId && claimer_user_id=:uId ", nativeQuery=true)
	public Optional<Claims> claimChecker(@Param("dId") int donationId, @Param("uId")int userId );
	

	
	/*
	 * @Query("SELECT c FROM Claims c WHERE c.donation.user.userId = :myUserId AND c.user.userId <> :myUserId"
	 * ) List<Claims> findRequestsByOthersOnMyDonations(@Param("myUserId") int
	 * myUserId);
	 */
	 
	  @Modifying
	  @Query("UPDATE Claims c SET c.claimStatus = :status WHERE c.claimId = :claimId")
	  void updateClaimStatus(@Param("status") String status,@Param("claimId") int claimId);

	  
	  @Query(value = "SELECT c.* " +
              "FROM claims c " +
              "JOIN food_donation d ON c.donation_id = d.donation_id " +
              "JOIN users du ON d.user_id = du.user_id " +
              "WHERE du.user_id = :userId", nativeQuery = true)
	  		List<Claims> findClaimsByDonorId(@Param("userId") int userId);

	
	  long countByUser(Users receiver);
	 
}


	


