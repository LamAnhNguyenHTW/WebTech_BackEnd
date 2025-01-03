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
import semester3.webtech.controller.KundeController;
import semester3.webtech.model.Kunde;
import semester3.webtech.service.KundeService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class KundeControllerTest {

    @Mock
    private KundeService kundeService;

    @InjectMocks
    private KundeController kundeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(kundeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllKunden() throws Exception {
        // Beispielkunden für den Test
        Kunde kunde1 = new Kunde(1L, "Max Mustermann", "Musterstraße 1", "0123456789", "max@example.com");
        Kunde kunde2 = new Kunde(2L, "Erika Mustermann", "Musterstraße 2", "9876543210", "erika@example.com");

        // Mocking der Rückgabe von Kunden
        when(kundeService.getAllKunden()).thenReturn(Arrays.asList(kunde1, kunde2));

        // Durchführung des Tests
        mockMvc.perform(get("/api/kunden"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Max Mustermann"))
                .andExpect(jsonPath("$[1].name").value("Erika Mustermann"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(kundeService, times(1)).getAllKunden();
    }

    @Test
    void testGetKundeById() throws Exception {
        // Beispielkunde für den Test
        Kunde kunde = new Kunde(1L, "Max Mustermann", "Musterstraße 1", "0123456789", "max@example.com");

        // Mocking der Rückgabe von einem Kunden
        when(kundeService.getKundeById(1L)).thenReturn(kunde);

        // Durchführung des Tests
        mockMvc.perform(get("/api/kunden/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Max Mustermann"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(kundeService, times(1)).getKundeById(1L);
    }

    @Test
    void testAddKunde() throws Exception {
        // Beispielkunde für den Test
        Kunde kunde = new Kunde(null, "Max Mustermann", "Musterstraße 1", "0123456789", "max@example.com");

        // Mocking der Rückgabe des hinzugefügten Kunden
        Kunde savedKunde = new Kunde(1L, "Max Mustermann", "Musterstraße 1", "0123456789", "max@example.com");
        when(kundeService.addKunde(any(Kunde.class))).thenReturn(savedKunde);

        // Durchführung des Tests
        mockMvc.perform(post("/api/kunden")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(kunde)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Max Mustermann"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(kundeService, times(1)).addKunde(any(Kunde.class));
    }

    @Test
    void testUpdateKunde() throws Exception {
        // Beispielkunde für den Test
        Kunde kunde = new Kunde(1L, "Max Mustermann", "Musterstraße 1", "0123456789", "max@example.com");

        // Mocking der Rückgabe des aktualisierten Kunden
        when(kundeService.updateKunde(eq(1L), any(Kunde.class))).thenReturn(kunde);

        // Durchführung des Tests
        mockMvc.perform(put("/api/kunden/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(kunde)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Max Mustermann"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(kundeService, times(1)).updateKunde(eq(1L), any(Kunde.class));
    }

    @Test
    void testDeleteKunde() throws Exception {
        // Durchführung des Tests
        mockMvc.perform(delete("/api/kunden/{id}", 1L))
                .andExpect(status().isNoContent());

        // Verifizieren, dass der Service aufgerufen wurde
        verify(kundeService, times(1)).deleteKunde(1L);
    }
}
