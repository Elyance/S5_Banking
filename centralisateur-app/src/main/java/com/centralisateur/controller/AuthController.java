package com.centralisateur.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import java.io.IOException;

import com.centralisateur.service.AuthService;
import com.compte_courant.dto.UtilisateurDTO;
import com.compte_courant.dto.DirectionDTO;
import com.compte_courant.dto.ActionRoleDTO;
import java.util.List;

@WebServlet(name = "AuthController", urlPatterns = {"/auth/login", "/auth/logout", "/auth/status", "/login"})
public class AuthController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/auth/status".equals(path)) {
            handleStatus(req, resp);
        } else if ("/login".equals(path)) {
            handleLoginPage(req, resp);
        } else if ("/auth/logout".equals(path)) {
            handleLogout(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/auth/login".equals(path)) {
            handleLogin(req, resp);
        } else if ("/auth/logout".equals(path)) {
            handleLogout(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Login et mot de passe requis");
            return;
        }

        HttpSession session = req.getSession();
        AuthService authService = (AuthService) session.getAttribute("authService");
        if (authService == null) {
            authService = new AuthService();
            session.setAttribute("authService", authService);
        }

        boolean success = authService.login(login, password);
        if (success) {
            resp.sendRedirect(req.getContextPath() + "/dashboard"); // Redirect to dashboard or home
        } else {
            resp.sendRedirect(req.getContextPath() + "/login?error=1"); // Redirect to login with error
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            AuthService authService = (AuthService) session.getAttribute("authService");
            if (authService != null) {
                authService.logout();
            }
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private void handleStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            AuthService authService = (AuthService) session.getAttribute("authService");
            if (authService != null && authService.isLoggedIn()) {
                UtilisateurDTO user = authService.getCurrentUser();
                DirectionDTO direction = authService.getCurrentUserDirection();
                List<ActionRoleDTO> roles = authService.getActionRoles();
                // You can return JSON or set attributes for JSP
                resp.setContentType("application/json");
                resp.getWriter().write("{\"loggedIn\": true, \"user\": \"" + user.getLogin() + "\", \"direction\": \"" + (direction != null ? direction.getLibelle() : "") + "\"}");
                return;
            }
        }
        resp.setContentType("application/json");
        resp.getWriter().write("{\"loggedIn\": false}");
    }

    private void handleLoginPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            AuthService authService = (AuthService) session.getAttribute("authService");
            if (authService != null && authService.isLoggedIn()) {
                resp.sendRedirect(req.getContextPath() + "/dashboard");
                return;
            }
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}