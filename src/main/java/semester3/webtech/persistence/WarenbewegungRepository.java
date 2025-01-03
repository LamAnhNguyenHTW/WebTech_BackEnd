package semester3.webtech.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import semester3.webtech.model.Warenbewegung;

@Repository
public interface WarenbewegungRepository extends JpaRepository<Warenbewegung, Long> {
}
