package com.centralisateur.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.xml.ws.Action;
import jakarta.servlet.ServletException;
import java.io.IOException;

import java.math.BigDecimal;
import com.centralisateur.service.CompteCourantService;
import com.centralisateur.service.AuthService;
import com.centralisateur.service.ClientService;
import com.centralisateur.dto.CompteCourantAvecClient;
import com.centralisateur.service.ChangeService;
import com.centralisateur.entity.Client;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import jakarta.inject.Inject;
import com.compte_courant.dto.*;
import java.time.*;
import java.time.format.DateTimeFormatter;



@WebServlet(name = "CompteCourantController", urlPatterns = {"/compte-courant/create","/compte-courant/success","/compte-courant/list","/compte-courant/transaction","/compte-courant/preview","/compte-courant/confirm","/compte-courant/transactions/list","/compte-courant/decouvert","/compte-courant/valider"})
public class CompteCourantController extends HttpServlet {
	private final CompteCourantService compteCourantService = new CompteCourantService();
    
    @Inject
	private ClientService clientService;

    @Inject
    private ChangeService changeService;

    // Simuler l'utilisateur connecté (à remplacer par la gestion réelle des utilisateurs)

    // private final UtilisateurDTO utilisateur = (AuthSercice) session.getAttribute("utilisateur"); // Exemple d'utilisateur avec rôle administrateur
    private AuthService getAuthServiceFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            AuthService authService = (AuthService) session.getAttribute("authService");
            return authService;
        }
        return null;
    }

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
            String path = "change.csv";
            List<String> devises = changeService.getListeDevises(path);
            String compteId = req.getParameter("compteId");
            String error = req.getParameter("error");
            if (compteId != null) {
                CompteCourantWithStatusDTO compte = compteCourantService.getCompteById(Long.parseLong(compteId));
                Map<Long, String> typeOperations = compteCourantService.getAllTypeOperations();
                req.setAttribute("compte", compte);
                req.setAttribute("typeOperations", typeOperations);
                req.setAttribute("decouvertAutorise", compteCourantService.getDecouvertValueByDate(compte.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
                req.setAttribute("devises", devises);
                if (error != null) {
                    req.setAttribute("errorMessage", error);
                }
                req.setAttribute("contentPage", "/views/compte_courant/transaction.jsp");
                req.getRequestDispatcher("/views/includes/layout.jsp").forward(req, resp);
                return;
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre compteId manquant");
                return;
            }
        } else if ("/compte-courant/transactions/list".equals(req.getServletPath())) {
            String compteId = req.getParameter("compteId");
            String error = req.getParameter("error");
            if (compteId != null) {
                List<TransactionDTO> transactions = compteCourantService.getTransactionsByCompteId(Long.parseLong(compteId));
                CompteCourantWithStatusDTO compte = compteCourantService.getCompteById(Long.parseLong(compteId));
                req.setAttribute("compte", compte);
                req.setAttribute("transactions", transactions);
                req.setAttribute("decouvertAutorise", compteCourantService.getDecouvertValueByDate(compte.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
                if (error != null) {
                    req.setAttribute("errorMessage", error);
                }
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
            String path = "change.csv";
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
                String devise = req.getParameter("devise");
                System.out.println("devise = " + devise);
                LocalDateTime dateTransaction;
                if (devise == null || devise.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "La devise est requise");
                    return;
                }
                
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

                AuthService authService = getAuthServiceFromSession(req);
                if (authService == null || !authService.isLoggedIn()) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }

                try {
                    boolean hasPermission = false;
                    int requiredRole = Integer.MAX_VALUE;
                    List<ActionRoleDTO> actions = authService.getActionRoles();
                    for (ActionRoleDTO actionRoleDTO : actions) {
                        if (actionRoleDTO.getAction().equals("CREATE") && actionRoleDTO.getNomTable().equals("transactions")) {
                            requiredRole = actionRoleDTO.getRole();
                            hasPermission = true;
                            break;
                        }
                    }
                    if (!hasPermission) {
                        resp.sendRedirect(req.getContextPath() + "/compte-courant/transaction?compteId=" + compteId + "&error=" + java.net.URLEncoder.encode("Vous n'avez pas les droits nécessaires pour créer une transaction.", "UTF-8"));
                        return;
                    } else if (requiredRole > authService.getCurrentUser().getRole()) {
                        resp.sendRedirect(req.getContextPath() + "/compte-courant/transaction?compteId=" + compteId + "&error=" + java.net.URLEncoder.encode("Vous n'avez pas les droits nécessaires pour créer une transaction.", "UTF-8"));
                        return;
                    }
                    BigDecimal montantAvecDevise = changeService.calculate(path, montant, devise,dateTransaction.toLocalDate());
                    System.out.println("devise = " + devise);
                    System.out.println("montantAvecDevise = " + montantAvecDevise);
                    
                    compteCourantService.faireTransaction(Long.parseLong(compteId), Long.parseLong(typeOperationId), montantAvecDevise, description, dateTransaction);

                    // Redirige vers une page de succès ou la liste des comptes
                    resp.sendRedirect(req.getContextPath() + "/compte-courant/success");
                } catch (Exception e) {
                    // En cas d'erreur, rediriger vers la page de transaction avec l'erreur
                    resp.sendRedirect(req.getContextPath() + "/compte-courant/transaction?compteId=" + compteId + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
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
            } else if ("/compte-courant/valider".equals(req.getServletPath())) {
                String transactionIdStr = req.getParameter("transactionId");
                String compteIdStr = req.getParameter("compteId");
                if (transactionIdStr == null || compteIdStr == null) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres manquants");
                    return;
                }
                Long transactionId = Long.parseLong(transactionIdStr);
                AuthService authService = getAuthServiceFromSession(req);
                if (authService == null || !authService.isLoggedIn()) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
                try {
                    boolean hasPermission = false;
                    int requiredRole = Integer.MAX_VALUE;
                    List<ActionRoleDTO> actions = authService.getActionRoles();
                    for (ActionRoleDTO actionRoleDTO : actions) {
                        if (actionRoleDTO.getAction().equals("VALIDATE") && actionRoleDTO.getNomTable().equals("transactions")) {
                            requiredRole = actionRoleDTO.getRole();
                            hasPermission = true;
                            break;
                        }
                    }
                    if (!hasPermission) {
                        resp.sendRedirect(req.getContextPath() + "/compte-courant/transactions/list?compteId=" + compteIdStr + "&error=" + java.net.URLEncoder.encode("Vous n'avez pas les droits nécessaires pour valider cette transaction.", "UTF-8"));
                    } else if (requiredRole > authService.getCurrentUser().getRole()) {
                        resp.sendRedirect(req.getContextPath() + "/compte-courant/transactions/list?compteId=" + compteIdStr + "&error=" + java.net.URLEncoder.encode("Vous n'avez pas les droits nécessaires pour valider cette transaction.", "UTF-8"));
                    } else {
                        compteCourantService.validerTransaction(transactionId);
                        resp.sendRedirect(req.getContextPath() + "/compte-courant/transactions/list?compteId=" + compteIdStr);
                    }
                } catch (Exception e) {
                    // En cas d'erreur, rediriger vers la liste des transactions avec l'erreur
                    resp.sendRedirect(req.getContextPath() + "/compte-courant/transactions/list?compteId=" + compteIdStr + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
                }
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
