
package com.example.service;

import com.example.domain.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    @Autowired
    private CitizenService service;

    @PostMapping
    public ResponseEntity<?> insert(@Validated @RequestBody Citizen citizen) {
        try {
            return ResponseEntity.ok(service.insertCitizen(citizen));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            service.deleteCitizen(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @RequestParam(required = false) String afm,
                                    @RequestParam(required = false) String address) {
        try {
            return ResponseEntity.ok(service.updateCitizen(id, afm, address));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getCitizen(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Citizen> search(@RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName) {
        return service.searchCitizens(firstName, lastName);
    }
}
