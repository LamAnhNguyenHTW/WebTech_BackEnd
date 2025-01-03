package semester3.webtech.persistence;

import org.springframework.data.repository.CrudRepository;
import semester3.webtech.model.Kunde;

public interface KundeRepository extends CrudRepository <Kunde, Long> {
}
