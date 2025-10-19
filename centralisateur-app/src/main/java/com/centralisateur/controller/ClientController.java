package com.centralisateur.controller;

import com.centralisateur.service.ClientService;
import com.centralisateur.service.CompteCourantService;
import com.centralisateur.entity.*;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;

import com.compte_courant.dto.CompteCourantWithStatusDTO;
import java.util.List;

@WebServlet({"/clients", "/clients/create", "/clients/details", "/clients/edit", "/clients/delete", "/clients/situation"})
public class ClientController extends HttpServlet {
    private final CompteCourantService compteCourantService = new CompteCourantService();

	@Inject
	private ClientService clientService;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
    if ("/clients/create".equals(servletPath)) {
            req.setAttribute("contentPage", "/views/clients/create.jsp");
            
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
        } else if ("/clients/details".equals(servletPath)) {
            String id = req.getParameter("id");
            if (id != null) {
                var client = clientService.findClientWithCurrentStatusById(Long.parseLong(id));
                req.setAttribute("client", client);
            }
            req.setAttribute("contentPage", "/views/clients/details.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
        } else if ("/clients/situation".equals(servletPath)) {
            String id = req.getParameter("id");
            if (id != null) {
                var client = clientService.findClientWithCurrentStatusById(Long.parseLong(id));
                req.setAttribute("client", client);
                // Set page title and (optional) financial data placeholders
                req.setAttribute("pageTitle", "Situation de " + client.getPrenom() + " " + client.getNom());
                
                // Convertir LocalDate/LocalDateTime en Date pour la compatibilité avec fmt:formatDate
                if (client.getDateCreation() != null) {
                    req.setAttribute("dateCreationFormatted", java.sql.Timestamp.valueOf(client.getDateCreation()));
                }
                if (client.getDateNaissance() != null) {
                    req.setAttribute("dateNaissanceFormatted", java.sql.Date.valueOf(client.getDateNaissance()));
                }
                
                // If you compute total balance elsewhere, set attribute name 'totalBalance'
                // req.setAttribute("totalBalance", soldeTotal);
                List<CompteCourantWithStatusDTO> comptesCourantDuClient = compteCourantService.getComptesByClientId(client.getId());
                req.setAttribute("comptesCourant", comptesCourantDuClient);
                // TODO: Ajouter les données des comptes et soldes ici
                // req.setAttribute("comptes", comptesDuClient);
                // req.setAttribute("soldeTotal", soldeTotal);
            }
            req.setAttribute("contentPage", "/views/clients/situation.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
        } else if ("/clients/edit".equals(servletPath)) {
            String id = req.getParameter("id");
            if (id != null) {
                var client = clientService.findClientWithCurrentStatusById(Long.parseLong(id));
                req.setAttribute("client", client);
            }
            req.setAttribute("contentPage", "/views/clients/edit.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            
        }else {
            var clients = clientService.getClientsWithCurrentStatus();
            req.setAttribute("clients", clients);
            req.setAttribute("contentPage", "/views/clients/list.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
        }
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		
        if ("/clients/create".equals(servletPath)) {
            try {
                // Récupérer les données du formulaire
                String nom = req.getParameter("nom");
                String prenom = req.getParameter("prenom");
                String dateNaissance = req.getParameter("dateNaissance");
                String email = req.getParameter("email");
                String telephone = req.getParameter("telephone");
                String adresse = req.getParameter("adresse");
                String profession = req.getParameter("profession");
                String dateCreationStr = req.getParameter("dateCreation");
                LocalDateTime dateCreation = LocalDateTime.parse(dateCreationStr);


                // Générer le numéro client
                long count = clientService.countClients();
                String numeroClient = String.format("CLI%05d", count + 1);

                // Créer le DTO
                Client client = new Client();
                client.setNumeroClient(numeroClient);
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setDateNaissance(LocalDate.parse(dateNaissance));
                client.setEmail(email);
                client.setTelephone(telephone);
                client.setAdresse(adresse);
                client.setProfession(profession);
                client.setDateCreation(dateCreation);

                // Enregistrer le client
                clientService.saveClient(client);
                // Rediriger vers la liste
                resp.sendRedirect(req.getContextPath() + "/clients");
            } catch (Exception e) {
                req.setAttribute("errorMessage", "Erreur lors de la création du client : " + e.getMessage());
                req.setAttribute("contentPage", "/views/clients/create.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            }
        } 
        if ("/clients/delete".equals(servletPath)) {
            try {
                Long clientId = Long.parseLong(req.getParameter("id"));
                clientService.changeClientStatus(clientId, 3L);
                resp.sendRedirect(req.getContextPath() + "/clients");
            } catch (Exception e) {
                req.setAttribute("errorMessage", "Erreur lors de la suppression du client : " + e.getMessage());
                var clients = clientService.getClientsWithCurrentStatus();
                req.setAttribute("clients", clients);
                req.setAttribute("contentPage", "/views/clients/list.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            }
        }
        if ("/clients/edit".equals(servletPath)) {
            try {
                System.out.println("Processing client edit request");
                // Récupérer les données du formulaire
                Long id = Long.parseLong(req.getParameter("id"));
                String nom = req.getParameter("nom");
                String prenom = req.getParameter("prenom");
                String dateNaissance = req.getParameter("dateNaissance");
                String email = req.getParameter("email");
                String telephone = req.getParameter("telephone");
                String adresse = req.getParameter("adresse");
                String profession = req.getParameter("profession");

                // Trouver le client existant
                Client client = clientService.findClientById(id);
                if (client == null) {
                    throw new Exception("Client non trouvé avec l'ID : " + id);
                }

                // Mettre à jour les informations du client
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setDateNaissance(LocalDate.parse(dateNaissance));
                client.setEmail(email);
                client.setTelephone(telephone);
                client.setAdresse(adresse);
                client.setProfession(profession);

                // Enregistrer les modifications
                clientService.updateClient(client);

                // Rediriger vers la liste
                resp.sendRedirect(req.getContextPath() + "/clients");
            } catch (Exception e) {
                req.setAttribute("errorMessage", "Erreur lors de la mise à jour du client : " + e.getMessage());
                req.setAttribute("contentPage", "/views/clients/edit.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            }
        }
	}
}
