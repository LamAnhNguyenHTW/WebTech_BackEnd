package semester3.webtech.servicetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import semester3.webtech.model.Kunde;
import semester3.webtech.persistence.KundeRepository;
import semester3.webtech.service.KundeService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KundeServiceTest {

    @Mock
    private KundeRepository kundeRepository;

    @InjectMocks
    private KundeService kundeService;

    private Kunde kunde;

    @BeforeEach
    public void setUp() {
        kunde = new Kunde(1L, "Max Mustermann", "Musterstraße 1", "0123456789", "max@mustermann.de");
    }

    @Test
    public void testGetAllKunden() {
        when(kundeRepository.findAll()).thenReturn(List.of(kunde));

        List<Kunde> kunden = kundeService.getAllKunden();
        assertNotNull(kunden);
        assertEquals(1, kunden.size());
        assertEquals("Max Mustermann", kunden.get(0).getName());
    }

    @Test
    public void testGetKundeById() {
        when(kundeRepository.findById(1L)).thenReturn(Optional.of(kunde));

        Kunde foundKunde = kundeService.getKundeById(1L);
        assertNotNull(foundKunde);
        assertEquals("Max Mustermann", foundKunde.getName());
    }

    @Test
    public void testGetKundeByIdNotFound() {
        when(kundeRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            kundeService.getKundeById(1L);
        });
        assertEquals("Kunde nicht gefunden", thrown.getMessage());
    }

    @Test
    public void testAddKunde() {
        when(kundeRepository.save(any(Kunde.class))).thenReturn(kunde);

        Kunde savedKunde = kundeService.addKunde(kunde);
        assertNotNull(savedKunde);
        assertEquals("Max Mustermann", savedKunde.getName());
    }

    @Test
    public void testUpdateKunde() {
        Kunde updatedKunde = new Kunde(1L, "Max Mustermann", "Neue Straße 2", "0123456789", "max@mustermann.de");
        when(kundeRepository.findById(1L)).thenReturn(Optional.of(kunde));
        when(kundeRepository.save(any(Kunde.class))).thenReturn(updatedKunde);

        Kunde result = kundeService.updateKunde(1L, updatedKunde);

        assertNotNull(result);
        assertEquals("Max Mustermann", result.getName());
        assertEquals("Neue Straße 2", result.getAdresse());
    }

    @Test
    public void testDeleteKunde() {
        doNothing().when(kundeRepository).deleteById(1L);

        assertDoesNotThrow(() -> kundeService.deleteKunde(1L));

        verify(kundeRepository, times(1)).deleteById(1L);
    }
}
