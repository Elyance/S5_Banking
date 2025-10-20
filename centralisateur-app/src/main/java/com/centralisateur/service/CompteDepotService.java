package com.centralisateur.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonNumber;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import java.util.Map;
import java.math.BigDecimal;

import com.centralisateur.dto.CompteCourantAvecClient;
import com.centralisateur.dto.CompteDepotAvecClient;
import com.centralisateur.repository.ClientRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CompteDepotService {

    @Inject
    ClientRepository clientRepository;

    public boolean creerCompteDepot(String jsonCompteDepot) throws Exception {
        URL url = new URL("http://localhost:5126/api/CompteDepot");
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

    public boolean deposerSurCompteDepot(String jsonDepot) throws Exception {
        URL url = new URL("http://localhost:5126/api/CompteDepot/depot");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonDepot.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();
        System.out.println("Réponse HTTP dépôt: " + code);
        if (code >= 200 && code < 300) {
            try (InputStream is = con.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String response = br.readLine();
                System.out.println("Réponse dépôt: " + response);
            }
            return true;
        } else {
            try (InputStream is = con.getErrorStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String errorResponse = br.readLine();
                System.out.println("Erreur dépôt: " + errorResponse);
            }
            return false;
        }
    }

    public String listerTypeOperation() throws Exception {
        URL url = new URL("http://localhost:5126/api/TypeOperation");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int code = con.getResponseCode();
        System.out.println("Réponse HTTP liste TypeOperation: " + code);
        if (code >= 200 && code < 300) {
            try (InputStream is = con.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                return response.toString();
            }
        } else {
            try (InputStream is = con.getErrorStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String errorResponse = br.readLine();
                System.out.println("Erreur liste TypeOperation: " + errorResponse);
            }
            throw new Exception("Erreur lors de la récupération des types d'opération");
        }
    }

    public String getComptesDepotByClientId(long clientId) throws Exception {
        URL url = new URL("http://localhost:5126/api/CompteDepot/client/" + clientId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int code = con.getResponseCode();
        System.out.println("Réponse HTTP getComptesDepotByClientId: " + code);
        if (code >= 200 && code < 300) {
            try (InputStream is = con.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                return response.toString();
            }
        } else {
            try (InputStream is = con.getErrorStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String errorResponse = br.readLine();
                System.out.println("Erreur getComptesDepotByClientId: " + errorResponse);
            }
            throw new Exception("Erreur lors de la récupération des comptes dépôt pour le client " + clientId);
        }
    }

    public String listerComptesDepot() throws Exception {
        URL url = new URL("http://localhost:5126/api/CompteDepot/list");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int code = con.getResponseCode();
        System.out.println("Réponse HTTP getAllComptesDepots: " + code);
        if (code >= 200 && code < 300) {
            try (InputStream is = con.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                return response.toString();
            }
        } else {
            try (InputStream is = con.getErrorStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String errorResponse = br.readLine();
                System.out.println("Erreur getAllComptesDepots: " + errorResponse);
            }
            throw new Exception("Erreur lors de la récupération des comptes dépôt");
        }

    }


    public List<CompteDepotAvecClient> parseComptesDepotJson(String comptesJson) throws Exception {
        List<CompteDepotAvecClient> result = new ArrayList<>();

        if (comptesJson == null || comptesJson.isEmpty()) return result;

        try (JsonReader reader = Json.createReader(new StringReader(comptesJson))) {
            JsonValue root = reader.read();
            if (root == null) return result;

            if (root.getValueType() == JsonValue.ValueType.ARRAY) {
                JsonArray arr = root.asJsonArray();
                for (JsonValue v : arr) {
                    if (v.getValueType() != JsonValue.ValueType.OBJECT) continue;
                    JsonObject obj = v.asJsonObject();
                    CompteDepotAvecClient compteDepotAvecClient = new CompteDepotAvecClient(v.toString(), null);

                    // Get account key: prefer numeroCompte variants, otherwise id
                    String key = null;
                    if (obj.containsKey("numeroCompte")) key = obj.getString("numeroCompte", null);
                    if (key == null && obj.containsKey("numero_compte")) key = obj.getString("numero_compte", null);
                    if (key == null && obj.containsKey("NumeroCompte")) key = obj.getString("NumeroCompte", null);

                    if (key == null && obj.containsKey("id")) {
                        // use id as fallback key
                        JsonValue idv = obj.get("id");
                        if (idv.getValueType() == JsonValue.ValueType.NUMBER) key = Long.toString(obj.getJsonNumber("id").longValue());
                        else key = obj.getString("id", null);
                    }

                    // Find clientId under multiple possible keys
                    Long clientId = null;
                    if (obj.containsKey("clientId")) {
                        JsonValue cid = obj.get("clientId");
                        if (cid.getValueType() == JsonValue.ValueType.NUMBER) clientId = obj.getJsonNumber("clientId").longValue();
                        else {
                            String s = obj.getString("clientId", null);
                            if (s != null) clientId = Long.parseLong(s);
                        }
                    }
                    if (clientId == null && obj.containsKey("client_id")) {
                        JsonValue cid = obj.get("client_id");
                        if (cid.getValueType() == JsonValue.ValueType.NUMBER) clientId = obj.getJsonNumber("client_id").longValue();
                        else {
                            String s = obj.getString("client_id", null);
                            if (s != null) clientId = Long.parseLong(s);
                        }
                    }

                    // nested client object
                    if (clientId == null && obj.containsKey("client")) {
                        JsonValue clientVal = obj.get("client");
                        if (clientVal.getValueType() == JsonValue.ValueType.OBJECT) {
                            JsonObject clientObj = clientVal.asJsonObject();
                            if (clientObj.containsKey("id")) {
                                JsonValue idv = clientObj.get("id");
                                if (idv.getValueType() == JsonValue.ValueType.NUMBER) clientId = clientObj.getJsonNumber("id").longValue();
                                else {
                                    String s = clientObj.getString("id", null);
                                    if (s != null) clientId = Long.parseLong(s);
                                }
                            } else if (clientObj.containsKey("Id")) {
                                JsonValue idv = clientObj.get("Id");
                                if (idv.getValueType() == JsonValue.ValueType.NUMBER) clientId = clientObj.getJsonNumber("Id").longValue();
                                else {
                                    String s = clientObj.getString("Id", null);
                                    if (s != null) clientId = Long.parseLong(s);
                                }
                            }
                        }
                    }

                    // fill parsed display fields
                    if (obj.containsKey("id")) {
                        JsonValue idv = obj.get("id");
                        if (idv.getValueType() == JsonValue.ValueType.NUMBER) compteDepotAvecClient.setId(obj.getJsonNumber("id").longValue());
                        else {
                            String s = obj.getString("id", null);
                            if (s != null) try { compteDepotAvecClient.setId(Long.parseLong(s)); } catch (NumberFormatException ignored) {}
                        }
                    }
                    if (key != null) {
                        compteDepotAvecClient.setNumeroCompte(key);
                    }

                    // dateCreation variants
                    String dateCreation = null;
                    if (obj.containsKey("dateCreation")) dateCreation = obj.getString("dateCreation", null);
                    if (dateCreation == null && obj.containsKey("date_creation")) dateCreation = obj.getString("date_creation", null);
                    if (dateCreation == null && obj.containsKey("DateCreation")) dateCreation = obj.getString("DateCreation", null);
                    compteDepotAvecClient.setDateCreation(dateCreation);

                    // Fetch tauxInteret using dateCreation
                    if (dateCreation != null) {
                        BigDecimal taux = getTauxInteretByDate(dateCreation);
                        compteDepotAvecClient.setTauxInteret(taux);
                    }

                    // solde
                    if (obj.containsKey("solde")) {
                        JsonValue sv = obj.get("solde");
                        if (sv.getValueType() == JsonValue.ValueType.NUMBER) {
                            BigDecimal bd = obj.getJsonNumber("solde").bigDecimalValue();
                            compteDepotAvecClient.setSolde(bd);
                        } else {
                            String s = obj.getString("solde", null);
                            if (s != null) {
                                try {
                                    compteDepotAvecClient.setSolde(new BigDecimal(s));
                                } catch (NumberFormatException ignored) {}
                            }
                        }
                    }

                    if (clientId != null) {
                        compteDepotAvecClient.setClient(clientRepository.findById(clientId));
                    }
                    // always add (client may be null)
                    result.add(compteDepotAvecClient);
                }
            }
        }
        return result;
    }

    public boolean createTauxInteret(Long valeur, String dateDebut) {
        try {
            URL url = new URL("http://localhost:5126/api/CompteDepot/taux");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // Build JSON body with valeur and dateDebut
            String body = String.format("{\"valeur\": %s, \"dateDebut\": \"%s\"}", valeur == null ? "null" : valeur.toString(), dateDebut);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            System.out.println("Réponse HTTP createTauxInteret: " + code);
            if (code >= 200 && code < 300) {
                try (InputStream is = con.getInputStream();
                     BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) response.append(line.trim());
                    System.out.println("createTauxInteret réponse: " + response.toString());
                }
                return true;
            } else {
                try (InputStream is = con.getErrorStream();
                     BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder err = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) err.append(line.trim());
                    System.out.println("createTauxInteret erreur: " + err.toString());
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public BigDecimal getTauxInteretByDate(String date) {
        try {
            URL url = new URL("http://localhost:5126/api/CompteDepot/taux/by-date?date=" + date);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            int code = con.getResponseCode();
            System.out.println("Réponse HTTP getTauxInteretByDate: " + code);

            if (code >= 200 && code < 300) {
                try (InputStream is = con.getInputStream();
                     BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) response.append(line.trim());
                    String jsonResponse = response.toString();
                    System.out.println("getTauxInteretByDate réponse: " + jsonResponse);

                    try (JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse))) {
                        JsonObject obj = jsonReader.readObject();

                        // Récupère seulement la valeur du taux d'intérêt
                        if (obj.containsKey("valeur") && !obj.isNull("valeur")) {
                            long valeur = obj.getJsonNumber("valeur").longValue();
                            return BigDecimal.valueOf(valeur);
                        } else {
                            System.out.println("Clé 'valeur' non trouvée dans la réponse");
                            return BigDecimal.ZERO;
                        }
                    }
                }
            } else {
                try (InputStream is = con.getErrorStream();
                     BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder err = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) err.append(line.trim());
                    System.out.println("getTauxInteretByDate erreur: " + err.toString());
                }
                return BigDecimal.ZERO;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }


    
}