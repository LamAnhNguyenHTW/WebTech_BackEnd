package semester3.webtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semester3.webtech.model.Warenbewegung;
import semester3.webtech.persistence.WarenbewegungRepository;

import java.util.List;

@Service
public class WarenbewegungService {

    @Autowired
    private WarenbewegungRepository repository;

    // Alle Warenbewegungen abrufen
    public List<Warenbewegung> getAllWarenbewegungen() {
        return repository.findAll();
    }

    // Eine neue Warenbewegung speichern
    public Warenbewegung saveWarenbewegung(Warenbewegung warenbewegung) {
        return repository.save(warenbewegung);
    }

    // Warenbewegung löschen
    public boolean deleteWarenbewegung(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
