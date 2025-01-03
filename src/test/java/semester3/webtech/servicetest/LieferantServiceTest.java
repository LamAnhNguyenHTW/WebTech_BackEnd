package semester3.webtech.servicetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import semester3.webtech.model.Lieferant;
import semester3.webtech.persistence.LieferantRepository;
import semester3.webtech.service.LieferantService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LieferantServiceTest {

    @Autowired
    private LieferantService lieferantService;

    @Autowired
    private LieferantRepository lieferantRepository;

    private Lieferant lieferant;

    @BeforeEach
    public void setUp() {
        // Erstelle einen Lieferanten vor jedem Test
        lieferant = new Lieferant();
        lieferant.setName("Test Lieferant");
        lieferant.setAddress("Test Adresse");
        lieferant.setPhone("123456789");
        lieferant.setEmail("test@lieferant.de");
        lieferant.setRating(5);

        // Speichern des Lieferanten in der Datenbank
        lieferantRepository.save(lieferant);
    }

    @Test
    public void testGetAllSuppliers() {
        // Hole alle Lieferanten
        List<Lieferant> result = lieferantService.getAllSuppliers();

        // Überprüfe, ob das Ergebnis nicht leer ist
        assertNotNull(result, "Die Liste der Lieferanten ist null");
        assertFalse(result.isEmpty(), "Die Liste der Lieferanten ist leer");

        // Überprüfe, ob der gespeicherte Lieferant in der Liste enthalten ist
        assertTrue(result.stream().anyMatch(l -> l.getName().equals("Test Lieferant")),
                "Der Test Lieferant wurde nicht in der Liste gefunden");
    }


    @Test
    public void testGetSupplierById() {
        // Hole den Lieferanten anhand der ID
        Optional<Lieferant> result = lieferantService.getSupplierById(lieferant.getId());
        assertTrue(result.isPresent());
        assertEquals("Test Lieferant", result.get().getName());
    }

    @Test
    public void testCreateSupplier() {
        // Erstelle einen neuen Lieferanten
        Lieferant newLieferant = new Lieferant();
        newLieferant.setName("Neuer Lieferant");
        newLieferant.setAddress("Neue Adresse");
        newLieferant.setPhone("987654321");
        newLieferant.setEmail("neuer@lieferant.de");
        newLieferant.setRating(4);

        // Speichern des neuen Lieferanten
        Lieferant createdLieferant = lieferantService.createSupplier(newLieferant);

        assertNotNull(createdLieferant);
        assertEquals("Neuer Lieferant", createdLieferant.getName());
    }

    @Test
    public void testUpdateSupplier() {
        // Ändere die Details des Lieferanten
        lieferant.setName("Geänderter Lieferant");
        lieferant.setAddress("Geänderte Adresse");
        lieferant.setPhone("999888777");

        // Speichern der Änderungen
        Lieferant updatedLieferant = lieferantService.updateSupplier(lieferant.getId(), lieferant);

        assertNotNull(updatedLieferant);
        assertEquals("Geänderter Lieferant", updatedLieferant.getName());
        assertEquals("Geänderte Adresse", updatedLieferant.getAddress());
    }

    @Test
    public void testDeleteSupplier() {
        // Lösche den Lieferanten
        lieferantService.deleteSupplier(lieferant.getId());

        // Überprüfe, ob der Lieferant wirklich gelöscht wurde
        Optional<Lieferant> deletedLieferant = lieferantService.getSupplierById(lieferant.getId());
        assertFalse(deletedLieferant.isPresent());
    }

    @Test
    public void testDeleteSupplierNotFound() {
        // Versuche, einen nicht existierenden Lieferanten zu löschen
        assertThrows(RuntimeException.class, () -> lieferantService.deleteSupplier(999L), "Lieferant nicht gefunden");
    }
}
