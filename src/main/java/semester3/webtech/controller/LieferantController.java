package semester3.webtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.webtech.model.Lieferant;
import semester3.webtech.service.LieferantService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/lieferanten")
public class LieferantController {

    @Autowired
    private LieferantService lieferantService;

    @GetMapping
    public List<Lieferant> getAllSuppliers() {
        return lieferantService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lieferant> getSupplierById(@PathVariable("id") long id) {
        Optional<Lieferant> lieferant = lieferantService.getSupplierById(id);
        if (lieferant.isPresent()) {
            return ResponseEntity.ok(lieferant.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Lieferant> createSupplier(@RequestBody Lieferant lieferant) {
        Lieferant newLieferant = lieferantService.createSupplier(lieferant);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLieferant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lieferant> updateSupplier(@PathVariable("id") long id, @RequestBody Lieferant lieferantDetails) {
        Lieferant updatedLieferant = lieferantService.updateSupplier(id, lieferantDetails);
        return ResponseEntity.ok(updatedLieferant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("id") long id) {
        lieferantService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
