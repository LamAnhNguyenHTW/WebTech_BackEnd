package semester3.webtech.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import semester3.webtech.model.Produkt;
import semester3.webtech.model.Warenbewegung;
import semester3.webtech.service.ProduktService;
import semester3.webtech.service.WarenbewegungService;

import java.util.List;

@Controller
@RequestMapping("/api/produkte")
public class ProduktController {

    private final ProduktService produktService;
    private final WarenbewegungService warenbewegungService;

    public ProduktController(ProduktService produktService, WarenbewegungService warenbewegungService) {
        this.produktService = produktService;
        this.warenbewegungService = warenbewegungService;
    }

    @GetMapping
    public ResponseEntity<List<Produkt>> getAllProdukte() {
        List<Produkt> produkte = produktService.getAllProdukte();
        return ResponseEntity.ok(produkte);
    }

    @PostMapping
    public ResponseEntity<Produkt> addProdukt(@RequestBody Produkt produkt) {
        return ResponseEntity.ok(produktService.addProdukt(produkt));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdukt(@PathVariable int id) {
        boolean deleted = produktService.deleteProdukt(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Produkt bearbeiten
    @PutMapping("/{id}")
    public ResponseEntity<Produkt> editProdukt(@PathVariable int id, @RequestBody Produkt produkt) {
        return produktService.editProdukt(id, produkt)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Wareneingang buchen
    @PutMapping("/{id}/entry")
    public ResponseEntity<Produkt> bookEntry(@PathVariable int id,
                                             @RequestParam int quantityToAdd,
                                             @RequestParam long lieferantId) {
        return produktService.bookEntry(id, quantityToAdd, lieferantId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Warenausgang buchen
    @PutMapping("/{id}/exit")
    public ResponseEntity<Produkt> bookExit(@PathVariable int id,
                                            @RequestParam int quantityToExit,
                                            @RequestParam long kundeId) {
        return produktService.bookExit(id, quantityToExit, kundeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/warenbewegungen")
    public ResponseEntity<List<Warenbewegung>> getWarenbewegungen() {
        List<Warenbewegung> bewegungen = warenbewegungService.getAllWarenbewegungen();
        return ResponseEntity.ok(bewegungen);
    }
}
