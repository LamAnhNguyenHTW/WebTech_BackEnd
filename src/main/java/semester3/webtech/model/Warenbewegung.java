package semester3.webtech.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warenbewegung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String produktName;
    private int menge;
    private String typ; // "Eingang" oder "Ausgang"
    private LocalDateTime datum;  // Datum der Warenbewegung
    @ManyToOne
    private Lieferant lieferant;  // Für Wareneingang
    @ManyToOne
    private Kunde kunde;  // Für Warenausgang


    @JsonGetter("lieferantName") // Serialisieren von Lieferantname
    public String getLieferantName() {
        return lieferant != null ? lieferant.getName() : null;
    }

    @JsonGetter("kundeName") // Serialisieren von Kundenname
    public String getKundeName() {
        return kunde != null ? kunde.getName() : null;
    }
}
