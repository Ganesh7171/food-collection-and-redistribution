package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import entities.NgoDetails;

public interface NgoDetailsRepository extends JpaRepository<NgoDetails, Integer> {
}
