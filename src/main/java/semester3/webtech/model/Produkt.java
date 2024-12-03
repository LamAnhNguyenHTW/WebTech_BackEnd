package semester3.webtech.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Produkt {
    private int id;
    private String name;
    private double price;
    private double quantity;
    private double totalValue;
    private double tempQuantity;
    public enum Kategorie {
        NUDELN, REIS, GETRAENKE
    }
    private Kategorie kategorie;
}
