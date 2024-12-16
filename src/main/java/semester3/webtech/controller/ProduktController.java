package semester3.webtech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import semester3.webtech.model.Produkt;
import semester3.webtech.service.ProduktService;


import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/api/produkte")
public class ProduktController {

    private final ProduktService produktService;

    public ProduktController(ProduktService produktService) {
        this.produktService = produktService;
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

    @PutMapping("/{id}/exit")
    public ResponseEntity<Produkt> bookExit(@PathVariable int id, @RequestBody int quantityToExit) {
        return produktService.bookExit(id, quantityToExit)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}