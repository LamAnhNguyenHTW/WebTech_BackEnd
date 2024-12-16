package semester3.webtech.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import semester3.webtech.model.Produkt;

@Repository
public interface ProduktRepository extends CrudRepository<Produkt, Integer> {
}
