package semester3.webtech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produkt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Produktname darf nicht null sein.")
    private String name;

    @Positive(message = "Der Preis muss größer als 0 sein.")
    private double price;

    @Min(value = 0, message = "Die Menge muss mindestens 0 sein.")
    private double quantity;

    private double totalValue;

    private int temp_quantity = 0;

    private Kategorie kategorie;

    // Enum für Produktkategorien
    public enum Kategorie {
        NUDELN, REIS, GETRAENKE
    }

    // Methode zur Berechnung des Gesamtwertes
    public void updateTotalValue() {
        this.totalValue = this.price * this.quantity;
    }

    // Methode zum Hinzufügen einer Menge
    public void addQuantity(double quantityToAdd) {
        if (quantityToAdd > 0) {
            this.quantity += quantityToAdd;
            updateTotalValue(); // Gesamtwert nach Menge ändern
        }
    }

    // Methode zum Reduzieren der Menge
    public boolean reduceQuantity(double quantityToReduce) {
        if (quantityToReduce > 0 && this.quantity >= quantityToReduce) {
            this.quantity -= quantityToReduce;
            updateTotalValue(); // Gesamtwert nach Menge ändern
            return true;
        }
        return false;
    }

}
