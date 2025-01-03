package semester3.webtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import semester3.webtech.model.Kunde;
import semester3.webtech.persistence.KundeRepository;

import java.util.List;

@Service
public class KundeService {

    @Autowired
    private KundeRepository kundeRepository;

    public List<Kunde> getAllKunden() {
        return (List<Kunde>) kundeRepository.findAll();
    }

    public Kunde getKundeById(Long id) {
        return kundeRepository.findById(id).orElseThrow(() -> new RuntimeException("Kunde nicht gefunden"));
    }

    public Kunde addKunde(Kunde kunde) {
        return kundeRepository.save(kunde);
    }

    public Kunde updateKunde(Long id, Kunde kundeDetails) {
        Kunde kunde = getKundeById(id);
        kunde.setName(kundeDetails.getName());
        kunde.setAdresse(kundeDetails.getAdresse());
        kunde.setTelefon(kundeDetails.getTelefon());
        kunde.setEmail(kundeDetails.getEmail());
    //    kunde.setRating(kundeDetails.getRating());
        return kundeRepository.save(kunde);
    }

    public void deleteKunde(Long id) {
        kundeRepository.deleteById(id);
    }
}
