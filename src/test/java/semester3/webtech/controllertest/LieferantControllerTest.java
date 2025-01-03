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
import semester3.webtech.controller.LieferantController;
import semester3.webtech.model.Lieferant;
import semester3.webtech.service.LieferantService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LieferantControllerTest {

    @Mock
    private LieferantService lieferantService;

    @InjectMocks
    private LieferantController lieferantController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lieferantController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllSuppliers() throws Exception {
        // Beispielhafte Lieferanten
        Lieferant lieferant1 = new Lieferant(1L, "Lieferant A", "Straße 1", "123456789", "lieferantA@example.com");
        Lieferant lieferant2 = new Lieferant(2L, "Lieferant B", "Straße 2", "987654321", "lieferantB@example.com");

        // Mocking der Rückgabe der Lieferanten
        when(lieferantService.getAllSuppliers()).thenReturn(Arrays.asList(lieferant1, lieferant2));

        // Durchführung des Tests
        mockMvc.perform(get("/api/lieferanten"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Lieferant A"))
                .andExpect(jsonPath("$[1].name").value("Lieferant B"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(lieferantService, times(1)).getAllSuppliers();
    }

    @Test
    void testGetSupplierById() throws Exception {
        // Beispielhafter Lieferant
        Lieferant lieferant = new Lieferant(1L, "Lieferant A", "Straße 1", "123456789", "lieferantA@example.com");

        // Mocking der Rückgabe des Lieferanten
        when(lieferantService.getSupplierById(1L)).thenReturn(Optional.of(lieferant));

        // Durchführung des Tests
        mockMvc.perform(get("/api/lieferanten/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Lieferant A"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(lieferantService, times(1)).getSupplierById(1L);
    }

    @Test
    void testGetSupplierByIdNotFound() throws Exception {
        // Mocking der Rückgabe von einem nicht gefundenen Lieferanten
        when(lieferantService.getSupplierById(1L)).thenReturn(Optional.empty());

        // Durchführung des Tests
        mockMvc.perform(get("/api/lieferanten/{id}", 1L))
                .andExpect(status().isNotFound());

        // Verifizieren, dass der Service aufgerufen wurde
        verify(lieferantService, times(1)).getSupplierById(1L);
    }

    @Test
    void testCreateSupplier() throws Exception {
        // Beispielhafter Lieferant
        Lieferant lieferant = new Lieferant(null, "Lieferant A", "Straße 1", "123456789", "lieferantA@example.com");

        // Mocking der Rückgabe des neuen Lieferanten
        Lieferant createdLieferant = new Lieferant(1L, "Lieferant A", "Straße 1", "123456789", "lieferantA@example.com");
        when(lieferantService.createSupplier(any(Lieferant.class))).thenReturn(createdLieferant);

        // Durchführung des Tests
        mockMvc.perform(post("/api/lieferanten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lieferant)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Lieferant A"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(lieferantService, times(1)).createSupplier(any(Lieferant.class));
    }

    @Test
    void testUpdateSupplier() throws Exception {
        // Beispielhafter Lieferant
        Lieferant lieferant = new Lieferant(1L, "Lieferant A", "Straße 1", "123456789", "lieferantA@example.com");

        // Mocking der Rückgabe des aktualisierten Lieferanten
        when(lieferantService.updateSupplier(eq(1L), any(Lieferant.class))).thenReturn(lieferant);

        // Durchführung des Tests
        mockMvc.perform(put("/api/lieferanten/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lieferant)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Lieferant A"));

        // Verifizieren, dass der Service aufgerufen wurde
        verify(lieferantService, times(1)).updateSupplier(eq(1L), any(Lieferant.class));
    }

    @Test
    void testDeleteSupplier() throws Exception {
        // Durchführung des Tests
        mockMvc.perform(delete("/api/lieferanten/{id}", 1L))
                .andExpect(status().isNoContent());

        // Verifizieren, dass der Service aufgerufen wurde
        verify(lieferantService, times(1)).deleteSupplier(1L);
    }
}
