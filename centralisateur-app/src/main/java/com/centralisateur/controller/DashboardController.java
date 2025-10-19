package com.centralisateur.controller;


import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.centralisateur.service.ClientService;
import com.centralisateur.service.CompteCourantService;
import com.centralisateur.service.ComptePretService;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
	private final CompteCourantService compteCourantService = new CompteCourantService();
	private final ComptePretService comptePretService = new ComptePretService();
	
	
	@Inject
	private ClientService clientService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// On définit le chemin du contenu à inclure dans le layout
		int nbClient = clientService.getAllClients().size();
		request.setAttribute("nbClient", nbClient);

		int nbCompteCourant = compteCourantService.getAllComptesWithStatus().size();
		request.setAttribute("nbCompteCourant", nbCompteCourant);

		int nbComptePret = comptePretService.listerComptesPret().size();
		request.setAttribute("nbComptePret", nbComptePret);

		request.setAttribute("contentPage", "/bienvenue.jsp");
		// On forward vers le layout.jsp qui inclura le contenu
		request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);
	}
}
