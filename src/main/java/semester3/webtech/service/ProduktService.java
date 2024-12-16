package semester3.webtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semester3.webtech.model.Produkt;
import semester3.webtech.persistence.ProduktRepository;

import java.util.*;

@Service
public class ProduktService {
    @Autowired
    private ProduktRepository produktRepository;

    // Alle Produkte abrufen
    public List<Produkt> getAllProdukte() {  // Änderung von Iterable zu List
        return (List<Produkt>) produktRepository.findAll();
    }

    // Produkt hinzufügen
    public Produkt addProdukt(Produkt produkt) {
        produkt.setTotalValue(produkt.getPrice() * produkt.getQuantity());
        return produktRepository.save(produkt);  // Speichern in der Datenbank
    }

    // Produkt löschen
    public boolean deleteProdukt(int id) {
        if (produktRepository.existsById(id)) {
            produktRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Warenausgang buchen
    public Optional<Produkt> bookExit(int id, int quantityToExit) {
        return produktRepository.findById(id)
                .map(produkt -> {
                    if (quantityToExit > 0 && produkt.getQuantity() >= quantityToExit) {
                        produkt.setQuantity(produkt.getQuantity() - quantityToExit);
                        produkt.setTotalValue(produkt.getPrice() * produkt.getQuantity());
                        return produktRepository.save(produkt);
                    }
                    return null;
                });
    }
}