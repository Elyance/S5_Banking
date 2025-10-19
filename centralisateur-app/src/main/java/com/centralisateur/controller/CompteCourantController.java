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
import java.time.*;
import java.time.format.DateTimeFormatter;



@WebServlet(name = "CompteCourantController", urlPatterns = {"/compte-courant/create","/compte-courant/success","/compte-courant/list","/compte-courant/transaction","/compte-courant/preview","/compte-courant/confirm","/compte-courant/transactions/list","/compte-courant/decouvert"})
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

        } else if ("/compte-courant/decouvert".equals(req.getServletPath())) {
            req.setAttribute("contentPage", "/views/compte_courant/decouvert.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            return;


        } else if ("/compte-courant/list".equals(req.getServletPath())) {
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
                CompteCourantWithStatusDTO compte = compteCourantService.getCompteById(Long.parseLong(compteId));
                Map<Long, String> typeOperations = compteCourantService.getAllTypeOperations();
                req.setAttribute("compte", compte);
                req.setAttribute("typeOperations", typeOperations);
                req.setAttribute("decouvertAutorise", compteCourantService.getDecouvertValueByDate(compte.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
                req.setAttribute("contentPage", "/views/compte_courant/transaction.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
                return;
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre compteId manquant");
                return;
            }
        } else if ("/compte-courant/transactions/list".equals(req.getServletPath())) {
            String compteId = req.getParameter("compteId");
            if (compteId != null) {
                List<TransactionDTO> transactions = compteCourantService.getTransactionsByCompteId(Long.parseLong(compteId));
                CompteCourantWithStatusDTO compte = compteCourantService.getCompteById(Long.parseLong(compteId));
                req.setAttribute("compte", compte);
                req.setAttribute("transactions", transactions);
                req.setAttribute("decouvertAutorise", compteCourantService.getDecouvertValueByDate(compte.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
                req.setAttribute("contentPage", "/views/compte_courant/transactions_list.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
                return;
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre compteId manquant");
                return;
            }
        } else if ("/compte-courant/preview".equals(req.getServletPath())) {
            String clientId = req.getParameter("clientId");
            String dateCreationStr = req.getParameter("dateCreation");
            if (dateCreationStr != null) {
                dateCreationStr = dateCreationStr.replace("T", " ");
            } else {
                dateCreationStr = LocalDateTime.now().toString().replace("T", " ");
            }
            System.out.println("Client ID: " + clientId);
            System.out.println("Date de création: " + dateCreationStr);
            Client client = clientService.findClientById(Long.parseLong(clientId));
            req.setAttribute("client", client);
            req.setAttribute("dateCreation", dateCreationStr);
            // Découvert autorisé depuis le service (exemple: 500.00 Ariary)
            LocalDateTime dateCreation = parseDateTime(dateCreationStr);
            Long decouvertAutorise = compteCourantService.getDecouvertValueByDate(dateCreation);
            req.setAttribute("decouvertAutorise", new BigDecimal(decouvertAutorise));

            req.setAttribute("contentPage", "/views/compte_courant/preview.jsp");
            req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            return;
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return; 
        }
       
	}

    // Utility: parse a date/time string in several common formats into LocalDateTime
    private static LocalDateTime parseDateTime(String input) {
        if (input == null) return null;
        String s = input.trim();
        // Try ISO first (e.g. 2025-10-19T22:19:40.123)
        try {
            return LocalDateTime.parse(s);
        } catch (Exception ignored) {
        }
        // Try common patterns
        String[] patterns = new String[] {"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"};
        for (String p : patterns) {
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern(p);
                return LocalDateTime.parse(s, fmt);
            } catch (Exception ignored) {
            }
        }
        // As a last resort, if string contains 'T' with a space replacement
        try {
            return LocalDateTime.parse(s.replace(' ', 'T'));
        } catch (Exception ignored) {
        }
        throw new IllegalArgumentException("Unsupported date/time format: " + input);
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
            if ("/compte-courant/create".equals(req.getServletPath())) {
                String clientId = req.getParameter("clientId");
                String dateCreationStr = req.getParameter("dateCreation");

                if (clientId == null || dateCreationStr == null) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres manquants");
                    return;
                }

                LocalDateTime dateCreation = parseDateTime(dateCreationStr); // robust parse

                // Appel du service métier (à adapter selon ton architecture)
                compteCourantService.creerCompteCourant(Long.parseLong(clientId), dateCreation);

                // Redirige vers une page de succès ou la liste des comptes
                resp.sendRedirect(req.getContextPath() + "/compte-courant/success");
            } else if ("/compte-courant/transaction".equals(req.getServletPath())) {
                String compteId = req.getParameter("compteId");
                String typeOperationId = req.getParameter("typeOperationId");
                String montantStr = req.getParameter("montant");
                String description = req.getParameter("description");
                String dateTransactionStr = req.getParameter("dateTransaction");
                LocalDateTime dateTransaction;
                if (dateTransactionStr != null && !dateTransactionStr.isEmpty()) {
                    dateTransaction = parseDateTime(dateTransactionStr);
                } else {
                    dateTransaction = LocalDateTime.now();
                }

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
                compteCourantService.faireTransaction(Long.parseLong(compteId), Long.parseLong(typeOperationId), montant, description, dateTransaction);

                // Redirige vers une page de succès ou la liste des comptes
                resp.sendRedirect(req.getContextPath() + "/compte-courant/success");

            } else if ("/compte-courant/preview".equals(req.getServletPath())) {
                String clientId = req.getParameter("clientId");
                String dateCreationStr = req.getParameter("dateCreation");
                if (dateCreationStr != null) {
                    dateCreationStr = dateCreationStr.replace("T", " ");
                } else {
                    dateCreationStr = LocalDateTime.now().toString().replace("T", " ");
                }
                System.out.println("Client ID: " + clientId);
                System.out.println("Date de création: " + dateCreationStr);
                Client client = clientService.findClientById(Long.parseLong(clientId));
                req.setAttribute("client", client);
                req.setAttribute("dateCreation", dateCreationStr);
                // Découvert autorisé depuis le service (exemple: 500.00 Ariary)
                LocalDateTime dateCreation = parseDateTime(dateCreationStr);
                Long decouvertAutorise = compteCourantService.getDecouvertValueByDate(dateCreation);
                req.setAttribute("decouvertAutorise", new BigDecimal(decouvertAutorise));

                req.setAttribute("contentPage", "/views/compte_courant/preview.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);

            }else if ("/compte-courant/confirm".equals(req.getServletPath())) {
                String clientId = req.getParameter("clientId");
                String dateCreationStr = req.getParameter("dateCreation");

                if (clientId == null || dateCreationStr == null) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres manquants");
                    return;
                }

                LocalDateTime dateCreation = parseDateTime(dateCreationStr);

                // Appel du service métier (à adapter selon ton architecture)
                compteCourantService.creerCompteCourant(Long.parseLong(clientId), dateCreation);

                // Redirige vers une page de succès ou la liste des comptes
                req.setAttribute("contentPage", "/views/compte_courant/success.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);

            } else if ("/compte-courant/decouvert".equals(req.getServletPath())) {
                String nouveauDecouvertStr = req.getParameter("montant");
                String dateEffectiveStr = req.getParameter("dateEffective");
                if (nouveauDecouvertStr == null || dateEffectiveStr == null) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres manquants");
                    return;
                }
                BigDecimal nouveauDecouvert = new BigDecimal(nouveauDecouvertStr);
                LocalDateTime dateEffective = parseDateTime(dateEffectiveStr);
                compteCourantService.mettreAJourDecouvertAutorise(nouveauDecouvert, dateEffective);


                req.setAttribute("contentPage", "/views/compte_courant/success.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return; 
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
