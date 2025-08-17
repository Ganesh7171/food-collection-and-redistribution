package repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entities.Users;

	

	@Repository
	public interface AuthRepository extends JpaRepository<Users, Long> {
		
		@Query(value="select * from users where username=:username", nativeQuery=true)
		public Users getPasswordByUsername(@Param("username") String Username);
		

	}

		
