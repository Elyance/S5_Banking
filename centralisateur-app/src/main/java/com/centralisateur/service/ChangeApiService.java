package com.centralisateur.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
public class ChangeApiService {

    private static final String CHANGE_SERVICE_URL = "http://localhost:6660/change/api/change/devises";

    public List<String> getDevises() {
        Client client = ClientBuilder.newClient();
        try {
            return client.target(CHANGE_SERVICE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<String>>() {});
        } finally {
            client.close();
        }
    }
}
