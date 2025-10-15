package com.centralisateur.controller;

import com.centralisateur.service.AdminService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
@WebServlet("/login")
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());

    @Inject
    private AdminService adminService;

    // GET /login -> affiche le formulaire (index.html)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward vers la page d'accueil contenant le formulaire
        req.getRequestDispatcher("/index.html").forward(req, resp);
    }

    // POST /login -> tente d'authentifier l'administrateur
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        LOGGER.info("Attempting login for email: " + email);
        // Never log raw passwords in production. Only log masked length for debug if needed.
        if (password != null) {
            LOGGER.fine("Password length: " + password.length());
        }

        // Basic validation
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/index.html?error=missing");
            return;
        }

        boolean authenticated = false;
        try {
            if (adminService == null) {
                LOGGER.severe("AdminService injection failed: adminService is null");
                throw new IllegalStateException("AdminService not available");
            }

            authenticated = adminService.login(email, password);
            LOGGER.info("Authentication result: " + authenticated);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while authenticating admin", e);
            // Log if you have a logging framework; for now, fail gracefully
            // e.printStackTrace();
        }

        if (authenticated) {
            // Set a session attribute to mark the admin as logged in
            req.getSession(true).setAttribute("adminEmail", email.trim());
            // Redirect to dashboard page
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            // Authentication failed - redirect back to login with an error flag
            resp.sendRedirect(req.getContextPath() + "/index.html?error=1");
        }
    }
}
