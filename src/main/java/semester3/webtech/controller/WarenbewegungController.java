package semester3.webtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.webtech.model.Warenbewegung;
import semester3.webtech.service.WarenbewegungService;

import java.util.List;

@RestController
@RequestMapping("/api/warenbewegungen")
public class WarenbewegungController {

    private final WarenbewegungService warenbewegungService;

    @Autowired
    public WarenbewegungController(WarenbewegungService warenbewegungService) {
        this.warenbewegungService = warenbewegungService;
    }

    // Abrufen aller Warenbewegungen
    @GetMapping
    public List<Warenbewegung> getAllWarenbewegungen() {
        return warenbewegungService.getAllWarenbewegungen();
    }

    // Warenbewegung speichern (Eingang oder Ausgang)
    @PostMapping
    public ResponseEntity<Warenbewegung> addWarenbewegung(@RequestBody Warenbewegung warenbewegung) {
        Warenbewegung savedWarenbewegung = warenbewegungService.saveWarenbewegung(warenbewegung);
        return ResponseEntity.ok(savedWarenbewegung);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarenbewegung(@PathVariable int id) {
        boolean deleted = warenbewegungService.deleteWarenbewegung(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
