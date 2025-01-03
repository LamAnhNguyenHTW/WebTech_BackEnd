package semester3.webtech.servicetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import semester3.webtech.model.Produkt;
import semester3.webtech.model.Lieferant;
import semester3.webtech.model.Kunde;
import semester3.webtech.model.Warenbewegung;
import semester3.webtech.persistence.KundeRepository;
import semester3.webtech.persistence.LieferantRepository;
import semester3.webtech.persistence.ProduktRepository;
import semester3.webtech.persistence.WarenbewegungRepository;
import semester3.webtech.service.ProduktService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProduktServiceTest {

    @InjectMocks
    private ProduktService produktService;

    @Mock
    private ProduktRepository produktRepository;

    @Mock
    private WarenbewegungRepository warenbewegungRepository;

    @Mock
    private LieferantRepository lieferantRepository;

    @Mock
    private KundeRepository kundeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProdukt() {
        Produkt produkt = new Produkt();
        produkt.setName("TestProdukt");
        produkt.setPrice(100.0);
        produkt.setQuantity(5);

        when(produktRepository.save(produkt)).thenReturn(produkt);

        Produkt result = produktService.addProdukt(produkt);

        assertEquals(500.0, result.getTotalValue());
        verify(produktRepository, times(1)).save(produkt);
    }

    @Test
    void testBookEntry() {
        Produkt produkt = new Produkt();
        produkt.setId(1);
        produkt.setName("TestProdukt");
        produkt.setQuantity(10);

        Lieferant lieferant = new Lieferant();
        lieferant.setId(1L);

        when(produktRepository.findById(1)).thenReturn(Optional.of(produkt));
        when(lieferantRepository.findById(1L)).thenReturn(Optional.of(lieferant));
        when(produktRepository.save(produkt)).thenReturn(produkt);

        Optional<Produkt> result = produktService.bookEntry(1, 5, 1L);

        assertTrue(result.isPresent());
        assertEquals(15, result.get().getQuantity());
        verify(warenbewegungRepository, times(1)).save(any(Warenbewegung.class));
    }

    @Test
    void testBookExit() {
        Produkt produkt = new Produkt();
        produkt.setId(1);
        produkt.setName("TestProdukt");
        produkt.setQuantity(10);

        Kunde kunde = new Kunde();
        kunde.setId(1L);

        when(produktRepository.findById(1)).thenReturn(Optional.of(produkt));
        when(kundeRepository.findById(1L)).thenReturn(Optional.of(kunde));
        when(produktRepository.save(produkt)).thenReturn(produkt);

        Optional<Produkt> result = produktService.bookExit(1, 5, 1L);

        assertTrue(result.isPresent());
        assertEquals(5, result.get().getQuantity());
        verify(warenbewegungRepository, times(1)).save(any(Warenbewegung.class));
    }

    @Test
    void testDeleteProdukt() {
        when(produktRepository.existsById(1)).thenReturn(true);

        boolean result = produktService.deleteProdukt(1);

        assertTrue(result);
        verify(produktRepository, times(1)).deleteById(1);
    }
}
