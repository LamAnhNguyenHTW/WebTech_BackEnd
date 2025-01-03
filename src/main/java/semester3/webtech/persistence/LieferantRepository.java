package semester3.webtech.persistence;

import org.springframework.data.repository.CrudRepository;
import semester3.webtech.model.Lieferant;

public interface LieferantRepository extends CrudRepository <Lieferant, Long>{
}
