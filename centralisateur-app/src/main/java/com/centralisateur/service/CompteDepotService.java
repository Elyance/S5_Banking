package com.centralisateur.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompteDepotService {

    public boolean creerCompteDepot(String jsonCompteDepot) throws Exception {
        URL url = new URL("http://localhost:5125/api/CompteDepot");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonCompteDepot.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();
        System.out.println("Réponse HTTP: " + code);
        
        // Lire la réponse pour debug
        if (code >= 200 && code < 300) {
            // Succès
            try (InputStream is = con.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String response = br.readLine();
                System.out.println("Réponse: " + response);
            }
            return true;
        } else {
            // Erreur
            try (InputStream is = con.getErrorStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String errorResponse = br.readLine();
                System.out.println("Erreur: " + errorResponse);
            }
            return false;
        }
    }
}