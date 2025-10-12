
package com.example.service;

import com.example.domain.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, String> {
    List<Citizen> findByFirstName(String firstName);
    List<Citizen> findByLastName(String lastName);
}
