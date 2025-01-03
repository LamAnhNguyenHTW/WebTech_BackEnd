package semester3.webtech.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import semester3.webtech.controller.ProduktController;
import semester3.webtech.model.Kunde;
import semester3.webtech.model.Lieferant;
import semester3.webtech.model.Produkt;
import semester3.webtech.model.Warenbewegung;
import semester3.webtech.service.ProduktService;
import semester3.webtech.service.WarenbewegungService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProduktControllerTest {

    @Mock
    private ProduktService produktService;

    @Mock
    private WarenbewegungService warenbewegungService;

    @InjectMocks
    private ProduktController produktController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produktController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllProdukte() throws Exception {
        // Beispielhafte Produkte
        Produkt produkt1 = new Produkt(1, "Produkt A", 10, 100.0);
        Produkt produkt2 = new Produkt(2, "Produkt B", 20, 200.0);

        // Mocking der Rückgabe der Produkte
        when(produktService.getAllProdukte()).thenReturn(Arrays.asList(produkt1, produkt2));

        // Durchführung des Tests
        mockMvc.perform(get("/api/produkte"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Produkt A"))
                .andExpect(jsonPath("$[1].name").value("Produkt B"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(produktService, times(1)).getAllProdukte();
    }

    @Test
    void testAddProdukt() throws Exception {
        // Beispielhaftes Produkt
        Produkt produkt = new Produkt(1, "Produkt A", 10, 100.0);

        // Mocking der Rückgabe des hinzugefügten Produkts
        when(produktService.addProdukt(any(Produkt.class))).thenReturn(produkt);

        // Durchführung des Tests
        mockMvc.perform(post("/api/produkte")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produkt)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Produkt A"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(produktService, times(1)).addProdukt(any(Produkt.class));
    }

    @Test
    void testDeleteProdukt() throws Exception {
        // Mocking der Rückgabe von einem erfolgreichen Löschvorgang
        when(produktService.deleteProdukt(1)).thenReturn(true);

        // Durchführung des Tests
        mockMvc.perform(delete("/api/produkte/{id}", 1))
                .andExpect(status().isNoContent());

        // Verifizieren, dass der Service aufgerufen wurde
        verify(produktService, times(1)).deleteProdukt(1);
    }

    @Test
    void testDeleteProduktNotFound() throws Exception {
        // Mocking der Rückgabe von einem fehlgeschlagenen Löschvorgang
        when(produktService.deleteProdukt(1)).thenReturn(false);

        // Durchführung des Tests
        mockMvc.perform(delete("/api/produkte/{id}", 1))
                .andExpect(status().isNotFound());

        // Verifizieren, dass der Service aufgerufen wurde
        verify(produktService, times(1)).deleteProdukt(1);
    }

    @Test
    void testEditProdukt() throws Exception {
        // Beispielhaftes Produkt
        Produkt produkt = new Produkt(1, "Produkt A", 10, 100.0);

        // Mocking der Rückgabe des bearbeiteten Produkts
        when(produktService.editProdukt(eq(1), any(Produkt.class))).thenReturn(Optional.of(produkt));

        // Durchführung des Tests
        mockMvc.perform(put("/api/produkte/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produkt)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Produkt A"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(produktService, times(1)).editProdukt(eq(1), any(Produkt.class));
    }

    @Test
    void testEditProduktNotFound() throws Exception {
        // Beispielhaftes Produkt
        Produkt produkt = new Produkt(1, "Produkt A", 10, 100.0);

        // Mocking der Rückgabe von einem nicht gefundenen Produkt
        when(produktService.editProdukt(eq(1), any(Produkt.class))).thenReturn(Optional.empty());

        // Durchführung des Tests
        mockMvc.perform(put("/api/produkte/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produkt)))
                .andExpect(status().isNotFound());

        // Verifizieren, dass der Service aufgerufen wurde
        verify(produktService, times(1)).editProdukt(eq(1), any(Produkt.class));
    }

    @Test
    void testBookEntry() throws Exception {
        // Beispielhafte Lieferant und Produkt
        Lieferant lieferant = new Lieferant(1L, "Lieferant A");
        Produkt produkt = new Produkt(1, "Produkt A", 10, 100.0);

        // Beispielhafte Warenbewegung (Wareneingang)
        Warenbewegung warenbewegung = new Warenbewegung(1L, "Produkt A", 5, "Eingang", LocalDateTime.now(), lieferant, null);

        // Mocking der Rückgabe des bearbeiteten Produkts nach der Buchung
        when(produktService.bookEntry(eq(1), eq(5), eq(1L))).thenReturn(Optional.of(produkt));

        // Durchführung des Tests
        mockMvc.perform(put("/api/produkte/{id}/entry", 1)
                        .param("quantityToAdd", "5")
                        .param("lieferantId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Produkt A"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(produktService, times(1)).bookEntry(eq(1), eq(5), eq(1L));
    }

    @Test
    void testBookExit() throws Exception {
        // Beispielhafte Kunde und Produkt
        Kunde kunde = new Kunde(1L, "Kunde A");
        Produkt produkt = new Produkt(1, "Produkt A", 10, 100.0);

        // Beispielhafte Warenbewegung (Warenausgang)
        Warenbewegung warenbewegung = new Warenbewegung(1L, "Produkt A", 5, "Ausgang", LocalDateTime.now(), null, kunde);

        // Mocking der Rückgabe des bearbeiteten Produkts nach der Buchung
        when(produktService.bookExit(eq(1), eq(5), eq(1L))).thenReturn(Optional.of(produkt));

        // Durchführung des Tests
        mockMvc.perform(put("/api/produkte/{id}/exit", 1)
                        .param("quantityToExit", "5")
                        .param("kundeId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Produkt A"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(produktService, times(1)).bookExit(eq(1), eq(5), eq(1L));
    }

    @Test
    void testGetWarenbewegungen() throws Exception {
        // Beispielhafte Warenbewegungen
        Lieferant lieferant = new Lieferant(1L, "Lieferant A");
        Kunde kunde = new Kunde(2L, "Kunde B");
        Warenbewegung warenbewegung1 = new Warenbewegung(1L, "Produkt A", 10, "Eingang", LocalDateTime.now(), lieferant, null);
        Warenbewegung warenbewegung2 = new Warenbewegung(2L, "Produkt B", 5, "Ausgang", LocalDateTime.now(), null, kunde);

        // Mocking der Rückgabe der Warenbewegungen
        when(warenbewegungService.getAllWarenbewegungen()).thenReturn(Arrays.asList(warenbewegung1, warenbewegung2));

        // Durchführung des Tests
        mockMvc.perform(get("/api/produkte/warenbewegungen"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].produktName").value("Produkt A"))
                .andExpect(jsonPath("$[0].lieferantName").value("Lieferant A"))
                .andExpect(jsonPath("$[1].produktName").value("Produkt B"))
                .andExpect(jsonPath("$[1].kundeName").value("Kunde B"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(warenbewegungService, times(1)).getAllWarenbewegungen();
    }
}
