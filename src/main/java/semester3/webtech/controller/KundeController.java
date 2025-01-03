package semester3.webtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.webtech.model.Kunde;
import semester3.webtech.service.KundeService;

import java.util.List;

@RestController
@RequestMapping("/api/kunden")
public class KundeController {

    @Autowired
    private KundeService kundeService;

    @GetMapping
    public ResponseEntity<List<Kunde>> getAllKunden() {
        return ResponseEntity.ok(kundeService.getAllKunden());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kunde> getKundeById(@PathVariable Long id) {
        return ResponseEntity.ok(kundeService.getKundeById(id));
    }

    @PostMapping
    public ResponseEntity<Kunde> addKunde(@RequestBody Kunde kunde) {
        return ResponseEntity.ok(kundeService.addKunde(kunde));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kunde> updateKunde(@PathVariable Long id, @RequestBody Kunde kunde) {
        return ResponseEntity.ok(kundeService.updateKunde(id, kunde));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKunde(@PathVariable Long id) {
        kundeService.deleteKunde(id);
        return ResponseEntity.noContent().build();
    }
}
