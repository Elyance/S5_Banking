package com.centralisateur.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.centralisateur.entity.Client;
import com.centralisateur.service.ClientService;
import com.centralisateur.service.CompteDepotService;

@WebServlet("/compte-depot/create")
public class CompteDepotController extends HttpServlet {
    @Inject
    private ClientService clientService;
    
    @Inject
    private CompteDepotService compteDepotService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Client> clients = clientService.getAllClients();
        req.setAttribute("clients", clients);
        req.setAttribute("contentPage", "/views/compte_depot/compte_depot_insertion.jsp");
        req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientId = req.getParameter("clientId");
        String solde = req.getParameter("solde");
        
        // Validation basique
        if (clientId == null || clientId.isEmpty()) {
            req.setAttribute("error", "L'ID client est requis");
            doGet(req, resp);
            return;
        }
        
        if (solde == null || solde.isEmpty()) {
            solde = "0.0";
        }

        // Créer le JSON
        String json = String.format("{\"clientId\":%s,\"solde\":%s}", clientId, solde);

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
        
        req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
    }
}