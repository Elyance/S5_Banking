package com.centralisateur.controller;

import com.centralisateur.dto.CompteDepotAvecClient;
import com.centralisateur.entity.Client;
import com.centralisateur.service.ClientService;
import com.centralisateur.service.CompteDepotService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = {"/compte-depot/doDepot", "/compte-depot/depot", "/compte-depot/list", "/compte-depot/taux", "/compte-depot/taux-interet", "/compte-depot/preview", "/compte-depot/confirm", "/compte-depot/transaction"})
public class CompteDepotController extends HttpServlet {
    @Inject
    private ClientService clientService;

    @Inject
    private CompteDepotService compteDepotService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/create")) {
            List<Client> clients = clientService.getAllClients();
            req.setAttribute("clients", clients);
            req.setAttribute("contentPage", "/views/compte_depot/compte_depot_insertion.jsp");

        } else if (uri.endsWith("/preview")) {
            String clientId = req.getParameter("clientId");
            String dateCreationStr = req.getParameter("dateCreation");
            if (clientId != null && !clientId.isEmpty()) {
                Client client = clientService.findClientById(Long.parseLong(clientId));
                req.setAttribute("client", client);
                req.setAttribute("dateCreation", dateCreationStr != null ? dateCreationStr : LocalDate.now().toString());
                req.setAttribute("solde", "0.0");
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_preview.jsp");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre clientId manquant");
                return;
            }
        } else if (uri.endsWith("/taux")) {
            // Show form to create a new taux d'intérêt
            req.setAttribute("contentPage", "/views/compte_depot/compte_depot_taux.jsp");

        } else if (uri.endsWith("/depot")) {
            String compteId = req.getParameter("compteId");
            if (compteId != null) {
                req.setAttribute("compteId", compteId);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre compteId manquant");
                return;
            }
            req.setAttribute("contentPage", "/views/compte_depot/compte_depot_depot.jsp");

        } else if (uri.endsWith("/transaction")) {

            String compteId = req.getParameter("compteId");
            if (compteId != null) {
                req.setAttribute("compteId", compteId);
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_choose_transaction.jsp");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre compteId manquant");
                return;
            }
        } else if (uri.endsWith("/list")) {
            try {
                String comptesJson = compteDepotService.listerComptesDepot();
                                List<CompteDepotAvecClient> comptesAvecClients = compteDepotService.parseComptesDepotJson(comptesJson);
                req.setAttribute("comptesAvecClients", comptesAvecClients);
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_list.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Erreur lors de la récupération des comptes dépôt: " + e.getMessage());
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_list.jsp");
            }
        }
        req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/taux-interet")) {
            String valeurStr = req.getParameter("valeur");
            String dateDebut = req.getParameter("dateDebut");
            if (valeurStr == null || valeurStr.isEmpty()) {
                req.setAttribute("error", "La valeur du taux est requise");
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_taux_error.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
                return;
            }

            if (dateDebut == null || dateDebut.isEmpty()) {
                req.setAttribute("error", "La date de début est requise");
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_taux_error.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
                return;
            }

            try {
                // parse as decimal but backend expects a long (integer percent), try to convert
                Double d = Double.parseDouble(valeurStr);
                Long valeurLong = Math.round(d);
                boolean success = compteDepotService.createTauxInteret(valeurLong, dateDebut);
                if (success) {
                    req.setAttribute("contentPage", "/views/compte_depot/compte_depot_taux_success.jsp");
                } else {
                    req.setAttribute("error", "Le service a renvoyé une erreur lors de la création du taux");
                    req.setAttribute("contentPage", "/views/compte_depot/compte_depot_taux_error.jsp");
                }
            } catch (NumberFormatException nfe) {
                req.setAttribute("error", "Valeur du taux invalide");
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_taux_error.jsp");
            }
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            return;
        }
        if (uri.endsWith("/confirm")) {
            String clientId = req.getParameter("clientId");
            String solde = req.getParameter("solde");
            String dateCreationStr = req.getParameter("dateCreation");

            // Validation basique
            if (clientId == null || clientId.isEmpty()) {
                req.setAttribute("error", "L'ID client est requis");
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_insertion_error.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
                return;
            }

            if (solde == null || solde.isEmpty()) {
                solde = "0.0";
            }

            if (dateCreationStr == null || dateCreationStr.isEmpty()) {
                dateCreationStr = java.time.LocalDate.now().toString();
            }

            // Créer le JSON
            String json = String.format("{\"clientId\":%s,\"solde\":%s,\"dateCreation\":\"%s\"}", clientId, solde, dateCreationStr);

            try {
                boolean success = compteDepotService.creerCompteDepot(json);

                if (success) {
                    req.setAttribute("contentPage", "/views/compte_depot/compte_depot_insertion_success.jsp");
                } else {
                    req.setAttribute("contentPage", "/views/compte_depot/compte_depot_insertion_error.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Erreur de connexion au service: " + e.getMessage());
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_insertion_error.jsp");
            }
        } 
        if (uri.endsWith("/preview")) {
            String clientId = req.getParameter("clientId");
            String dateCreationStr = req.getParameter("dateCreation");
            if (clientId != null && !clientId.isEmpty()) {
                Client client = clientService.findClientById(Long.parseLong(clientId));
                req.setAttribute("client", client);
                req.setAttribute("dateCreation", dateCreationStr != null ? dateCreationStr : LocalDate.now().toString());
                req.setAttribute("solde", "0.0");
                System.out.println("taux d'interet" + compteDepotService.getTauxInteretByDate(dateCreationStr));
                req.setAttribute("tauxInteret", compteDepotService.getTauxInteretByDate(dateCreationStr));
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_preview.jsp");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre clientId manquant");
                return;
            }
        }
        if (uri.endsWith("/doDepot")) {

            System.out.println("Received POST request for deposit");
            String compteDepotId = req.getParameter("compteDepotId");
            String montant = req.getParameter("montant");
            String dateDepot = req.getParameter("dateDepot");

            // Validation basique
            if (compteDepotId == null || compteDepotId.isEmpty() || montant == null || montant.isEmpty() || dateDepot == null || dateDepot.isEmpty()) {
                req.setAttribute("error", "Tous les champs sont requis");
                doGet(req, resp);
                return;
            }

            // Créer le JSON pour l'API
            String json = String.format("{\"montant\":%s,\"dateDepot\":\"%s\",\"compteDepotId\":%s}", montant, dateDepot, compteDepotId);

            try {
                boolean success = compteDepotService.deposerSurCompteDepot(json);
                if (success) {
                    req.setAttribute("contentPage", "/views/compte_depot/compte_depot_depot_success.jsp");
                } else {
                    req.setAttribute("contentPage", "/views/compte_depot/compte_depot_insertion_error.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Erreur de connexion au service: " + e.getMessage());
                req.setAttribute("contentPage", "/views/compte_depot/compte_depot_insertion_error.jsp");
            }
        }
        req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
    }
}