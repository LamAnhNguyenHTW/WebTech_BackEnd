package semester3.webtech.servicetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import semester3.webtech.model.Warenbewegung;
import semester3.webtech.persistence.WarenbewegungRepository;
import semester3.webtech.service.WarenbewegungService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class WarenbewegungServiceTest {

    @Autowired
    private WarenbewegungService warenbewegungService;

    @Autowired
    private WarenbewegungRepository warenbewegungRepository;

    private Warenbewegung warenbewegung;

    @BeforeEach
    public void setUp() {
        // Setup für jeden Test
        warenbewegung = new Warenbewegung();
        warenbewegung.setProduktName("Test Artikel");
        warenbewegung.setMenge(10);

        // Speichern der Warenbewegung in der Datenbank
        warenbewegungRepository.save(warenbewegung);
    }

    @Test
    public void testGetAllWarenbewegungen() {
        // Testen, ob alle Warenbewegungen korrekt abgerufen werden
        List<Warenbewegung> result = warenbewegungService.getAllWarenbewegungen();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size()); // Wir haben nur eine Warenbewegung gespeichert
    }

    @Test
    public void testSaveWarenbewegung() {
        // Eine neue Warenbewegung speichern
        Warenbewegung newWarenbewegung = new Warenbewegung();
        newWarenbewegung.setProduktName("Neuer Artikel");
        newWarenbewegung.setMenge(20);

        Warenbewegung savedWarenbewegung = warenbewegungService.saveWarenbewegung(newWarenbewegung);

        assertNotNull(savedWarenbewegung);
        assertEquals("Neuer Artikel", savedWarenbewegung.getProduktName());
        assertEquals(20, savedWarenbewegung.getMenge());
    }

    @Test
    public void testDeleteWarenbewegungSuccess() {
        // Löschen einer vorhandenen Warenbewegung
        boolean result = warenbewegungService.deleteWarenbewegung(warenbewegung.getId());

        assertTrue(result);
        // Sicherstellen, dass die Warenbewegung aus der DB gelöscht wurde
        Optional<Warenbewegung> deletedWarenbewegung = warenbewegungRepository.findById(warenbewegung.getId());
        assertFalse(deletedWarenbewegung.isPresent());
    }

    @Test
    public void testDeleteWarenbewegungNotFound() {
        // Löschen einer nicht existierenden Warenbewegung
        boolean result = warenbewegungService.deleteWarenbewegung(999L); // Eine ID, die nicht existiert

        assertFalse(result);
    }
}
