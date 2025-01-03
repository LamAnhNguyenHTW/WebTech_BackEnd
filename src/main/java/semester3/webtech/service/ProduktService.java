package semester3.webtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semester3.webtech.model.Kunde;
import semester3.webtech.model.Lieferant;
import semester3.webtech.model.Produkt;
import semester3.webtech.model.Warenbewegung;
import semester3.webtech.persistence.KundeRepository;
import semester3.webtech.persistence.LieferantRepository;
import semester3.webtech.persistence.ProduktRepository;
import semester3.webtech.persistence.WarenbewegungRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProduktService {
    @Autowired
    private ProduktRepository produktRepository;

    @Autowired
    private LieferantRepository lieferantRepository;

    @Autowired
    private KundeRepository kundeRepository;

    private final WarenbewegungRepository warenbewegungRepository;

    public ProduktService(ProduktRepository produktRepository, WarenbewegungRepository warenbewegungRepository,
                          LieferantRepository lieferantRepository, KundeRepository kundeRepository) {
        this.produktRepository = produktRepository;
        this.warenbewegungRepository = warenbewegungRepository;
        this.lieferantRepository = lieferantRepository;
        this.kundeRepository = kundeRepository;
    }

    // Alle Produkte abrufen
    public List<Produkt> getAllProdukte() {
        return (List<Produkt>) produktRepository.findAll();
    }

    // Produkt hinzufügen
    public Produkt addProdukt(Produkt produkt) {
        produkt.setTotalValue(produkt.getPrice() * produkt.getQuantity());
        return produktRepository.save(produkt);
    }

    // Produkt löschen
    public boolean deleteProdukt(int id) {
        if (produktRepository.existsById(id)) {
            produktRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Produkt bearbeiten
    public Optional<Produkt> editProdukt(int id, Produkt produkt) {
        return produktRepository.findById(id)
                .map(existingProduct -> {
                    // Produktdaten aktualisieren
                    existingProduct.setName(produkt.getName());
                    existingProduct.setPrice(produkt.getPrice());
                    existingProduct.setQuantity(produkt.getQuantity());
                    existingProduct.setTotalValue(existingProduct.getPrice() * existingProduct.getQuantity());
                    return produktRepository.save(existingProduct);
                });
    }

    // Wareneingang buchen
    public Optional<Produkt> bookEntry(int produktId, int quantityToAdd, long lieferantId) {
        Optional<Produkt> produkt = produktRepository.findById(produktId);
        Optional<Lieferant> lieferant = lieferantRepository.findById(lieferantId);

        if (produkt.isPresent() && lieferant.isPresent()) {
            Produkt p = produkt.get();
            Lieferant l = lieferant.get();

            p.setQuantity(p.getQuantity() + quantityToAdd);
            produktRepository.save(p);

            // Warenbewegung speichern
            Warenbewegung bewegung = new Warenbewegung();
            bewegung.setProduktName(p.getName());
            bewegung.setMenge(quantityToAdd);
            bewegung.setTyp("Eingang");
            bewegung.setDatum(LocalDateTime.now());
            bewegung.setLieferant(l);  // Lieferant zuweisen
            warenbewegungRepository.save(bewegung);

            return Optional.of(p);
        }
        return Optional.empty();
    }

    // Warenausgang buchen
    public Optional<Produkt> bookExit(int produktId, int quantityToExit, long kundeId) {
        Optional<Produkt> produkt = produktRepository.findById(produktId);
        Optional<Kunde> kunde = kundeRepository.findById(kundeId);

        if (produkt.isPresent() && kunde.isPresent()) {
            Produkt p = produkt.get();
            Kunde k = kunde.get();

            if (p.getQuantity() >= quantityToExit) {
                p.setQuantity(p.getQuantity() - quantityToExit);
                produktRepository.save(p);

                // Warenbewegung speichern
                Warenbewegung bewegung = new Warenbewegung();
                bewegung.setProduktName(p.getName());
                bewegung.setMenge(quantityToExit);
                bewegung.setTyp("Ausgang");
                bewegung.setDatum(LocalDateTime.now());
                bewegung.setKunde(k);  // Kunde zuweisen
                warenbewegungRepository.save(bewegung);

                return Optional.of(p);
            } else {
                return Optional.empty(); // Nicht genug Bestand
            }
        }
        return Optional.empty();
    }



}