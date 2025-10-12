package com.example.citizenclient.client;

import com.example.citizenclient.model.Citizen;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
public class CitizenClient {
    private final String BASE_URL = "http://localhost:8080/api/citizens";
    private final RestTemplate restTemplate = new RestTemplate();

    public String addCitizen(Citizen citizen) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, citizen, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Error adding citizen: " + e.getMessage();
        }
    }

    public String deleteCitizen(String at) {
        try {
            restTemplate.delete(BASE_URL + "/" + at);
            return "Ο πολίτης έχει διαγραφεί με επιτυχία από το μητρώο πολιτών";
        } catch (Exception e) {
            return "Error deleting citizen: " + e.getMessage();
        }
    }

    public String updateCitizen(String at, String afm, String address) {
        try {
            Citizen partial = new Citizen();
            partial.setAfm(afm);
            partial.setAddress(address);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Citizen> request = new HttpEntity<>(partial, headers);
            restTemplate.put(BASE_URL + "/" + at, request);
            return "Τα στοιχεία του πολίτη έχουν ενημερωθεί με επιτυχία στο μητρώο πολιτών";
        } catch (Exception e) {
            return "Error updating citizen: " + e.getMessage();
        }
    }

    public Citizen getCitizenByAt(String at) {
        try {
            return restTemplate.getForObject(BASE_URL + "/" + at, Citizen.class);
        } catch (Exception e) {
            System.err.println("Error fetching citizen: " + e.getMessage());
            return null;
        }
    }

    public Citizen[] searchCitizens(Map<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/search");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        try {
            ResponseEntity<Citizen[]> response = restTemplate.getForEntity(builder.toUriString(), Citizen[].class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Search error: " + e.getMessage());
            return new Citizen[0];
        }
    }
}