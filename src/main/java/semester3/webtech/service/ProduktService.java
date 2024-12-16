package semester3.webtech.service;

import org.springframework.stereotype.Service;
import semester3.webtech.model.Produkt;

import java.util.*;

@Service
public class ProduktService {

    private final ArrayList<Produkt> produkte = new ArrayList<>(List.of(
            new Produkt(1, "Jasmine Reis 3A", 10, 50, 500, 0, Produkt.Kategorie.REIS),
            new Produkt(2, "Samyang Nudeln", 12, 30, 360, 0, Produkt.Kategorie.NUDELN)
    ));
    private int nextId = 3;

    // Alle Produkte abrufen
    public ArrayList<Produkt> getAllProdukte() {
        return produkte;
    }

    // Produkt hinzufügen
    public Produkt addProdukt(Produkt produkt) {
        produkt.setId(nextId++);
        produkt.setTotalValue(produkt.getPrice() * produkt.getQuantity()); // Gesamtwert berechnen
        produkte.add(produkt); // Produkt zur Liste hinzufügen
        return produkt;
    }

    // Produkt löschen
    public boolean deleteProdukt(int id) {
        return produkte.removeIf(produkt -> produkt.getId() == id);
    }

    // Warenausgang buchen
    public Optional<Produkt> bookExit(int id, int quantityToExit) {
        Produkt produkt = produkte.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (produkt != null && quantityToExit > 0 && produkt.getQuantity() >= quantityToExit) {
            produkt.setQuantity(produkt.getQuantity() - quantityToExit);
            produkt.setTotalValue(produkt.getPrice() * produkt.getQuantity()); // Gesamtwert aktualisieren
            return Optional.of(produkt);
        }
        return Optional.empty();
    }
}
