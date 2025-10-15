package com.centralisateur.controller;

import com.centralisateur.service.ComptePretService;
import com.compte_pret.dto.ComptePretWithStatusDTO;
import com.centralisateur.service.ClientService;
import com.centralisateur.dto.ComptePretAvecClient;
import com.centralisateur.entity.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@WebServlet(name = "ComptePretController", urlPatterns = {"/compte-pret/*"})
public class ComptePretController extends HttpServlet { // Étendre HttpServlet
    private final ComptePretService comptePretService = new ComptePretService();

    @Inject
    private ClientService clientService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        System.out.println("PathInfo: " + pathInfo); // Debug
        
        if (pathInfo != null && pathInfo.equals("/taux-interet")) {
            request.setAttribute("contentPage", "/views/compte_pret/create-taux-interet.jsp");
            request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);
        
        } else if (pathInfo != null && pathInfo.equals("/create")) {
            
            // Charger la liste des clients pour le select
            List<Client> clients = clientService.getAllClients();
            request.setAttribute("clients", clients);
            request.setAttribute("contentPage", "/views/compte_pret/create-compte-pret.jsp");
            request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);
        
        } else if (pathInfo != null && pathInfo.equals("/success")) {
        
            String successMessage = (String) request.getSession().getAttribute("successMessage");
            request.getSession().removeAttribute("successMessage");
            request.setAttribute("successMessage", successMessage);
            request.setAttribute("contentPage", "/views/compte_pret/create-compte-pret.jsp");
            request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);
        
        } else if (pathInfo != null && pathInfo.equals("/list")) {
            List<ComptePretAvecClient> comptes = new ArrayList<>();
            List<ComptePretWithStatusDTO> comptesDTO = comptePretService.listerComptesPret();
            System.err.println("[ComptePretController][doGet] comptesDTO size=" + comptesDTO.size());
            for (ComptePretWithStatusDTO dto : comptesDTO) {
                Long clientId = dto.getClientId();
                if (clientId == null) {
                    System.err.println("ComptePretWithStatusDTO sans clientId, id compte: " + dto.getId());
                    continue;
                }
                Client client = clientService.findClientById(clientId);
                if (client == null) {
                    System.err.println("Aucun client trouvé pour l'id: " + clientId);
                    continue;
                }
                comptes.add(new ComptePretAvecClient(client, dto));
            }
            request.setAttribute("comptes", comptes);
            request.setAttribute("contentPage", "/views/compte_pret/list.jsp");
            request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);

        } else if (pathInfo != null && pathInfo.equals("/preter")) {
            // Charger la liste des clients pour le select
            Map<Long, String> typesPaiement = comptePretService.listerTypesPaiement();
            request.setAttribute("typesPaiement", typesPaiement);
            request.setAttribute("contentPage", "/views/compte_pret/preter.jsp");
            request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);

        } else {
            // Page par défaut ou gestion d'erreur
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        System.out.println("[ComptePretController][doPost] pathInfo=" + pathInfo);

        if (pathInfo != null && pathInfo.equals("/taux-interet")) {
            try {
                String valeurStr = request.getParameter("valeur");
                BigDecimal valeur = new BigDecimal(valeurStr);

                String dateDebutStr = request.getParameter("dateDebut");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dateDebut = LocalDateTime.parse(dateDebutStr, formatter);

                comptePretService.ajouterTauxInteret(valeur, dateDebut);
                response.sendRedirect(request.getContextPath() + "/compte-pret/success");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Erreur lors de l'ajout du taux d'intérêt : " + e.getMessage());
                request.setAttribute("contentPage", "/views/compte_pret/create-taux-interet.jsp");
                request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);
            }
        } else if (pathInfo != null && pathInfo.equals("/create")) {
            try {
                String clientIdStr = request.getParameter("clientId");
                

                if (clientIdStr == null) {
                    throw new IllegalArgumentException("Tous les champs sont obligatoires.");
                }

                Long clientId = Long.parseLong(clientIdStr);
                BigDecimal soldeRestantDu = new BigDecimal("0"); // Valeur par défaut

                comptePretService.creerComptePret(clientId, soldeRestantDu, LocalDateTime.now());
                request.getSession().setAttribute("successMessage", "Compte prêt créé avec succès !");
                response.sendRedirect(request.getContextPath() + "/compte-pret/success");
            } catch (Exception e) {
                e.printStackTrace();
                // Recharge la liste des clients pour le formulaire en cas d'erreur
                List<Client> clients = clientService.getAllClients();
                request.setAttribute("clients", clients);
                request.setAttribute("errorMessage", "Erreur lors de la création du compte prêt : " + e.getMessage());
                request.setAttribute("contentPage", "/views/compte_pret/create-compte-pret.jsp");
                request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);
            }
        } else if (pathInfo != null && pathInfo.equals("/preter")) {
            try {
                String comptePretIdStr = request.getParameter("comptePretId");
                String montantStr = request.getParameter("montant");
                String typePaiementIdStr = request.getParameter("typePaiementId");
                String dureeStr = request.getParameter("duree");
                if (comptePretIdStr == null || montantStr == null || typePaiementIdStr == null || dureeStr == null) {
                    throw new IllegalArgumentException("Tous les champs sont obligatoires.");
                }
                Long comptePretId = Long.parseLong(comptePretIdStr);
                BigDecimal montant = new BigDecimal(montantStr);
                Long typePaiementId = Long.parseLong(typePaiementIdStr);
                int duree = Integer.parseInt(dureeStr); // en mois
                LocalDateTime datePret = LocalDateTime.now();
                comptePretService.preter(comptePretId, montant, typePaiementId, duree, datePret);
                request.getSession().setAttribute("successMessage", "Prêt effectué avec succès !");
                response.sendRedirect(request.getContextPath() + "/compte-pret/success");
            } catch (Exception e) {
                e.printStackTrace();
                // Recharge la liste des types de paiement pour le formulaire en cas d'erreur
                Map<Long, String> typesPaiement = comptePretService.listerTypesPaiement();
                request.setAttribute("typesPaiement", typesPaiement);
                request.setAttribute("errorMessage", "Erreur lors de l'exécution du prêt : " + e.getMessage());
                request.setAttribute("contentPage", "/views/compte_pret/preter.jsp");
                request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);
            }
        } else {
            // Toute autre URL en POST n'est pas autorisée
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "POST non supporté pour cette URL");
        }
    }
}