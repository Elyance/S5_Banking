package com.centralisateur.controller;

import com.centralisateur.service.ComptePretService;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        System.out.println("PathInfo: " + pathInfo); // Debug
        
        if (pathInfo != null && pathInfo.equals("/taux-interet")) {
            request.setAttribute("contentPage", "/views/compte_pret/create-taux-interet.jsp");
            request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);
        } else if (pathInfo != null && pathInfo.equals("/success")) {
            request.setAttribute("successMessage", "Taux d'intérêt ajouté avec succès !");
            request.setAttribute("contentPage", "/views/compte_pret/create-taux-interet.jsp");
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
                // Le champ "datetime-local" HTML renvoie "yyyy-MM-dd'T'HH:mm"
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
        } else {
            // Toute autre URL en POST n'est pas autorisée
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "POST non supporté pour cette URL");
        }
    }
}