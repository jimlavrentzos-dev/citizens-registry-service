package com.example.service;

import com.example.domain.Citizen;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CitizenControllerTest {

    @Autowired
    private MockMvc mockMvc;
    

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testInsertAndGetCitizen() throws Exception {
        Citizen citizen = new Citizen();
        citizen.setId("17071972");
        citizen.setFirstName("Dimitrios");
        citizen.setLastName("Lavrentzos");
        citizen.setGender("male");
        citizen.setBirthDate("1972-07-17"); // ISO format για LocalDate

        
        mockMvc.perform(post("/citizens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(citizen)))
            .andExpect(status().isOk()) 
            .andExpect(jsonPath("$.firstName").value("Dimitrios"))
            .andExpect(jsonPath("$.lastName").value("Lavrentzos"));

        // Test ανάκτησης πολίτη
        mockMvc.perform(get("/citizens/17071972"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Dimitrios"));
    }

    @Test
    void testInsertInvalidCitizen() throws Exception {
        Citizen invalidCitizen = new Citizen(); // Κενά όλα

        mockMvc.perform(post("/citizens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidCitizen)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("firstName")))
            .andExpect(content().string(containsString("birthDate")));
    }
}