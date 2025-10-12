package com.example.service;

import com.example.domain.Citizen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CitizenServiceTest {

    private CitizenRepository repo;
    private CitizenService service;

    @BeforeEach
    void setUp() {
        repo = mock(CitizenRepository.class);
        service = new CitizenService(repo);
    }

    @Test
    void testInsertCitizenSuccess() {
        Citizen citizen = new Citizen();
        citizen.setId("01051987");
        citizen.setFirstName("Kostantina");
        citizen.setLastName("Kefala");
        citizen.setGender("female");
        citizen.setBirthDate("01-05-1987");

        when(repo.existsById("01051987")).thenReturn(false);
        when(repo.save(any())).thenReturn(citizen);

        Citizen saved = service.insertCitizen(citizen);
        assertEquals("Kostantina", saved.getFirstName());
    }

    @Test
    void testInsertCitizenDuplicateId() {
        Citizen citizen = new Citizen();
        citizen.setId("01051987");

        when(repo.existsById("01051987")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            service.insertCitizen(citizen);
        });
    }

    @Test
    void testDeleteCitizenNotFound() {
        when(repo.existsById("11111111")).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteCitizen("11111111");
        });
    }

    @Test
    void testUpdateCitizenInvalidAfm() {
        Citizen citizen = new Citizen();
        citizen.setId("01051987");

        when(repo.findById("01051987")).thenReturn(Optional.of(citizen));

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateCitizen("01051987", "abc", "Patra");
        });
    }
}