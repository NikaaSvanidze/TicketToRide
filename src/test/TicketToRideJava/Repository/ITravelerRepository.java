package Repository;

import DataClasses.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITravelerRepository extends JpaRepository<Traveler, Long> {
    Traveler findByUsername(String username);

}
