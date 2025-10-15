package com.centralisateur.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

import java.math.BigDecimal;
import com.centralisateur.service.CompteCourantService;
import com.centralisateur.service.ClientService;
import com.centralisateur.dto.CompteCourantAvecClient;
import com.centralisateur.entity.Client;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import jakarta.inject.Inject;
import com.compte_courant.dto.*;


@WebServlet(name = "CompteCourantController", urlPatterns = {"/compte-courant/create","/compte-courant/success","/compte-courant/list","/compte-courant/transaction"})
public class CompteCourantController extends HttpServlet {
	private final CompteCourantService compteCourantService = new CompteCourantService();
    
    @Inject
	private ClientService clientService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/compte-courant/success".equals(req.getServletPath())) {
            req.setAttribute("contentPage", "/views/compte_courant/success.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            return;

        } else if("/compte-courant/list".equals(req.getServletPath())) {
            List<CompteCourantAvecClient> comptes = new ArrayList<>();
            List<CompteCourantWithStatusDTO> comptesDTO = compteCourantService.getAllComptesWithStatus();
            for (CompteCourantWithStatusDTO compte : comptesDTO) {
                Client client = clientService.findClientById(compte.getClientId());
                comptes.add(new CompteCourantAvecClient(compte, client));
            }
            req.setAttribute("comptes", comptes);
            req.setAttribute("contentPage", "/views/compte_courant/list.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            return;
        
        } else if ("/compte-courant/create".equals(req.getServletPath())) {
             List<Client> clients = clientService.getAllClients();
            req.setAttribute("clients", clients);
            req.setAttribute("contentPage", "/views/compte_courant/create.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            return;

        } else if ("/compte-courant/transaction".equals(req.getServletPath())) {
            String compteId = req.getParameter("compteId");
            if (compteId != null) {
                Map<Long, String> typeOperations = compteCourantService.getAllTypeOperations();
                req.setAttribute("compteId", compteId);
                req.setAttribute("typeOperations", typeOperations);
                req.setAttribute("contentPage", "/views/compte_courant/transaction.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
                return;
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre compteId manquant");
                return;
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return; 
        }
       
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
            if ("/compte-courant/create".equals(req.getServletPath())) {
                String clientId = req.getParameter("clientId");
                String decouvertAutorise = req.getParameter("decouvertAutorise");

                // Appel du service métier (à adapter selon ton architecture)
                compteCourantService.creerCompteCourant(Long.parseLong(clientId), new BigDecimal("0"), new BigDecimal(decouvertAutorise));

                // Redirige vers une page de succès ou la liste des comptes
                resp.sendRedirect(req.getContextPath() + "/compte-courant/success");
            } else if ("/compte-courant/transaction".equals(req.getServletPath())) {
                String compteId = req.getParameter("compteId");
                String typeOperationId = req.getParameter("typeOperationId");
                String montantStr = req.getParameter("montant");
                String description = req.getParameter("description");

                if (compteId == null || typeOperationId == null || montantStr == null || montantStr.isEmpty() || description == null || description.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres manquants ou invalides");
                    return;
                }

                BigDecimal montant = new BigDecimal(montantStr);
                if (montant.compareTo(BigDecimal.ZERO) <= 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le montant doit être positif");
                    return;
                }

                // Appel du service métier pour effectuer la transaction
                compteCourantService.faireTransaction(Long.parseLong(compteId), Long.parseLong(typeOperationId), montant, description);

                // Redirige vers une page de succès ou la liste des comptes
                resp.sendRedirect(req.getContextPath() + "/compte-courant/success");

            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                
            }
			
		} catch (Exception e) {
            System.out.println(e.getMessage());
			req.setAttribute("errorMessage", "Erreur lors de la création du compte : " + e.getMessage());
            req.setAttribute("clients", clientService.getAllClients());
			req.setAttribute("contentPage", "/views/compte_courant/create.jsp");
			req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
		}
	}
}
