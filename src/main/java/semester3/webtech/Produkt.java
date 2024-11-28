package semester3.webtech;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Produkt {
    private int id;
    private String bez;
    private String beschreibung;
    private double preis;
    public enum Kategorie {
        NUDELN, REIS, GETRAENKE
    } // neue Datei - extra Klasse
    private Kategorie kategorie;
}
