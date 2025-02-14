package semester3.webtech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lieferant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String email;
    private int rating;

    public Lieferant(long l, String lieferantA, String s, String number, String mail) {
        this.id = l;
        this.name = lieferantA;
        this.address = s;
        this.phone = number;
        this.email = mail;
        this.rating = 0;
    }

    public Lieferant(Long o, String lieferantA, String s, String number, String mail) {
    }

    public Lieferant(long l, String lieferantA) {
        this.id = l;
        this.name = lieferantA;
    }
}
