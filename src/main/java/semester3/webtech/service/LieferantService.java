package semester3.webtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semester3.webtech.model.Lieferant;
import semester3.webtech.persistence.LieferantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LieferantService {

    @Autowired
    private LieferantRepository lieferantRepository;

    public List<Lieferant> getAllSuppliers() {
        return (List<Lieferant>) lieferantRepository.findAll();
    }

    public Optional<Lieferant> getSupplierById(long id) {
        return lieferantRepository.findById(id);
    }

    public Lieferant createSupplier(Lieferant lieferant) {
        return lieferantRepository.save(lieferant);
    }

    public Lieferant updateSupplier(long id, Lieferant lieferantDetails) {
        Lieferant lieferant = lieferantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lieferant nicht gefunden"));

        lieferant.setName(lieferantDetails.getName());
        lieferant.setAddress(lieferantDetails.getAddress());
        lieferant.setPhone(lieferantDetails.getPhone());
        lieferant.setEmail(lieferantDetails.getEmail());
        lieferant.setRating(lieferantDetails.getRating());

        return lieferantRepository.save(lieferant);
    }

    public void deleteSupplier(long id) {
        Lieferant lieferant = lieferantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lieferant nicht gefunden"));

        lieferantRepository.delete(lieferant);
    }
}
