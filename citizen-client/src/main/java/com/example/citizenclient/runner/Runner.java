package com.example.citizenclient.runner;

import com.example.citizenclient.client.CitizenClient;
import com.example.citizenclient.model.Citizen;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Runner implements CommandLineRunner {

    private final CitizenClient client;

    public Runner(CitizenClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) {
        Citizen citizen = new Citizen();
        citizen.setAt("ΑΡ123456");
        citizen.setFirstName("Dimitrios");
        citizen.setLastName("Lavrentzos");
        citizen.setGender("M");
        citizen.setBirthDate("17-07-1972");
        citizen.setAfm("987654321");
        citizen.setAddress("Athens, Greece");

        // 1. Add new citizen
        System.out.println(client.addCitizen(citizen));

        // 2. Get citizen by at number
        Citizen fetched = client.getCitizenByAt("ΑΡ123456");
        if (fetched != null)
            System.out.println("Found citizen: " + fetched.getFirstName());

        // 3. Update citizen information
        System.out.println(client.updateCitizen("ΑΡ123456", "123456789", "Patra"));

        // 4. Search citizen by last name
        var searchParams = new HashMap<String, String>();
        searchParams.put("lastName", "Lavrentzos");
        Citizen[] results = client.searchCitizens(searchParams);
        for (Citizen c : results)
            System.out.println("Search result: " + c.getAt() + " - " + c.getFirstName());

        // 5. Delete citizen by at number
        System.out.println(client.deleteCitizen("ΑΡ123456"));
    }
}