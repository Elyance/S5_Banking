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

    
}
