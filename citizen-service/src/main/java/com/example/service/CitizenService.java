
package com.example.service;

import com.example.domain.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    private final CitizenRepository repository;

    @Autowired
    public CitizenService(CitizenRepository repository) {
        this.repository = repository;
    }

    public Citizen insertCitizen(Citizen citizen) {
        if (repository.existsById(citizen.getId())) {
            throw new IllegalArgumentException("Ο συγκεκριμένος πολίτης υπάρχει ήδη στο μητρώο πολιτών");
        }
        return repository.save(citizen);
    }

    public void deleteCitizen(String id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Ο συγκεκριμένος πολίτης δεν υπάρχει στο μητρώο πολιτών");
        }
        repository.deleteById(id);
    }

    public Citizen updateCitizen(String id, String afm, String address) {
        Optional<Citizen> optional = repository.findById(id);
        if (optional.isEmpty()) throw new IllegalArgumentException("Ο συγκεκριμένος πολίτης δεν βρέθηκε στο μητρώο πολιτών");
        if (afm != null && !afm.matches("\\d{9}")) {
            throw new IllegalArgumentException("Invalid AFM.");
        }
        Citizen c = optional.get();
        c.setAfm(afm);
        c.setAddress(address);
        return repository.save(c);
    }

    public Citizen getCitizen(String id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Citizen not found"));
    }

    public List<Citizen> searchCitizens(String firstName, String lastName) {
        if (firstName != null) return repository.findByFirstName(firstName);
        if (lastName != null) return repository.findByLastName(lastName);
        return repository.findAll();
    }
}
