package com.centralisateur.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import com.centralisateur.service.ClientService;



@WebServlet("/comptes/*")
public class CompteController extends HttpServlet {
    
    private ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle requests related to comptes here
        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();
        
        if ("/comptes/select-type".equals(servletPath) || "/select-type".equals(pathInfo)) {
            String clientId = req.getParameter("clientId");
            if (clientId != null) {
                try {
                    var client = clientService.findClientWithCurrentStatusById(Long.parseLong(clientId));
                    req.setAttribute("client", client);
                } catch (Exception e) {
                    // Handle client not found
                    req.setAttribute("errorMessage", "Client non trouvé");
                }
            }
            req.setAttribute("clientId", clientId);
            req.setAttribute("contentPage", "/views/comptes/select-type.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
        } else {
            // Default behavior - redirect to clients list or show error
            resp.sendRedirect(req.getContextPath() + "/clients");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();
        if ("/comptes/create".equals(servletPath) || "/create".equals(pathInfo)) {
            createCompte(req, resp);
        } else {
            // Default behavior - redirect to clients list or show error
            resp.sendRedirect(req.getContextPath() + "/clients");
        }
    }

    private void createCompte(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Implementation for creating a compte based on form submission
        String clientId = req.getParameter("clientId");
        String dateCreation = req.getParameter("dateCreation");
        String typeCompte = req.getParameter("typeCompte");

        // Validate and process the form data
        if (clientId != null && dateCreation != null && typeCompte != null) {
            try {
                // Call the service layer to create the compte
                if (typeCompte.equals("compte-courant")) {
                    // Call CompteCourantService to create compte courant
                    // compteCourantService.creerCompteCourant(...);
                    resp.sendRedirect(req.getContextPath() + "/compte-courant/preview?clientId=" + clientId + "&dateCreation=" + dateCreation);
                } else if (typeCompte.equals("compte-pret")) {
                    // Call ComptePretService to create compte pret
                    // comptePretService.creerComptePret(...);
                    resp.sendRedirect(req.getContextPath() + "/compte-pret/preview?clientId=" + clientId + "&dateCreation=" + dateCreation);
                } else if (typeCompte.equals("compte-depot")) {
                    // Call CompteDepotService to create compte depot
                    // compteDepotService.creerCompteDepot(...);
                    resp.sendRedirect(req.getContextPath() + "/compte-depot/preview?clientId=" + clientId + "&dateCreation=" + dateCreation);
                } else {
                    // Handle unknown compte type
                    req.setAttribute("errorMessage", "Type de compte inconnu");
                    req.getRequestDispatcher("/views/includes/error.jsp").forward(req, resp);
                }
            } catch (Exception e) {
                // Handle errors (e.g., invalid data, service errors)
                req.setAttribute("errorMessage", "Erreur lors de la création du compte");
                req.getRequestDispatcher("/views/includes/error.jsp").forward(req, resp);
            }
        } else {
            // Handle missing parameters
            req.setAttribute("errorMessage", "Paramètres manquants");
            req.getRequestDispatcher("/views/includes/error.jsp").forward(req, resp);
        }
    }

    
}
