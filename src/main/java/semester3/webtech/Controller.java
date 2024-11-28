package semester3.webtech;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping(path ="/homepage")
    public String homepage() {
        return "Willkommen auf der Homepage!";
    }

    @GetMapping(path ="/products")
    public ResponseEntity<List<Produkt>> getAllProducts() {
        List<Produkt> produkte = new LinkedList<>();
        produkte.add(new Produkt(1,"SAMYANG Hot Chicken Buldak Ramen Carbonara 140g","SAMYANG",1.59, Produkt.Kategorie.NUDELN));
        produkte.add(new Produkt(2,"ROYALTHAI Jasmin White Scented Rice 1kg","Reis",3.59, Produkt.Kategorie.REIS));
        produkte.add(new Produkt(3,"OKF Aloe Vera Drink Original 1,5L ","Aloe Vera",3.99, Produkt.Kategorie.GETRAENKE));
        return ResponseEntity.ok(produkte);
    }
}
