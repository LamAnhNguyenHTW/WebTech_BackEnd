package semester3.webtech.service;

import org.springframework.stereotype.Service;
import semester3.webtech.model.Produkt;

import java.util.*;

@Service
public class ProduktService {

    private final Map<Integer, Produkt> produkte = new HashMap<>(Map.of(
            1, new Produkt(1, "Jasmine Reis 3A", 10, 50, 500, 0, Produkt.Kategorie.REIS),
            2, new Produkt(2, "Samyang Nudeln", 12, 30, 360, 0, Produkt.Kategorie.NUDELN)
    ));
    private int nextId = 3;

    // Alle Produkte abrufen
    public List<Produkt> getAllProdukte() {
        return new ArrayList<>(produkte.values());
    }

    // Produkt hinzufügen
    public Produkt addProdukt(Produkt produkt) {
        produkt.setId(nextId++);
        produkt.setTotalValue(produkt.getPrice() * produkt.getQuantity()); // Gesamtwert berechnen
        produkte.put(produkt.getId(), produkt);
        return produkt;
    }

    // Produkt löschen
    public boolean deleteProdukt(int id) {
        return produkte.remove(id) != null;
    }

    // Warenausgang buchen
    public Optional<Produkt> bookExit(int id, int quantityToExit) {
        Produkt produkt = produkte.get(id);
        if (produkt != null && quantityToExit > 0 && produkt.getQuantity() >= quantityToExit) {
            produkt.setQuantity(produkt.getQuantity() - quantityToExit);
            produkt.setTotalValue(produkt.getPrice() * produkt.getQuantity()); // Gesamtwert aktualisieren
            return Optional.of(produkt);
        }
        return Optional.empty();
    }
}
