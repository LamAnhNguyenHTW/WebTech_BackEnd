package semester3.webtech.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import semester3.webtech.controller.WarenbewegungController;
import semester3.webtech.model.Kunde;
import semester3.webtech.model.Lieferant;
import semester3.webtech.model.Warenbewegung;
import semester3.webtech.service.WarenbewegungService;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WarenbewegungControllerTest {

    @Mock
    private WarenbewegungService warenbewegungService;

    @InjectMocks
    private WarenbewegungController warenbewegungController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(warenbewegungController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllWarenbewegungen() throws Exception {
        // Beispielhafte Warenbewegungen
        Lieferant lieferant = new Lieferant(1L, "Lieferant A");
        Kunde kunde = new Kunde(2L, "Kunde B");
        Warenbewegung warenbewegung1 = new Warenbewegung(1L, "Produkt A", 10, "Eingang", LocalDateTime.now(), lieferant, null);
        Warenbewegung warenbewegung2 = new Warenbewegung(2L, "Produkt B", 5, "Ausgang", LocalDateTime.now(), null, kunde);

        // Mocking der Rückgabe der Warenbewegungen
        when(warenbewegungService.getAllWarenbewegungen()).thenReturn(Arrays.asList(warenbewegung1, warenbewegung2));

        // Durchführung des Tests
        mockMvc.perform(get("/api/warenbewegungen"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].produktName").value("Produkt A"))
                .andExpect(jsonPath("$[0].lieferantName").value("Lieferant A"))
                .andExpect(jsonPath("$[1].produktName").value("Produkt B"))
                .andExpect(jsonPath("$[1].kundeName").value("Kunde B"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(warenbewegungService, times(1)).getAllWarenbewegungen();
    }

    @Test
    void testDeleteWarenbewegung() throws Exception {
        // Mocking der Rückgabe von einem erfolgreichen Löschvorgang
        when(warenbewegungService.deleteWarenbewegung(1)).thenReturn(true);

        // Durchführung des Tests
        mockMvc.perform(delete("/api/warenbewegungen/{id}", 1))
                .andExpect(status().isNoContent());

        // Verifizieren, dass der Service aufgerufen wurde
        verify(warenbewegungService, times(1)).deleteWarenbewegung(1);
    }

    @Test
    void testDeleteWarenbewegungNotFound() throws Exception {
        // Mocking der Rückgabe von einem fehlgeschlagenen Löschvorgang
        when(warenbewegungService.deleteWarenbewegung(1)).thenReturn(false);

        // Durchführung des Tests
        mockMvc.perform(delete("/api/warenbewegungen/{id}", 1))
                .andExpect(status().isNotFound());

        // Verifizieren, dass der Service aufgerufen wurde
        verify(warenbewegungService, times(1)).deleteWarenbewegung(1);
    }
}
