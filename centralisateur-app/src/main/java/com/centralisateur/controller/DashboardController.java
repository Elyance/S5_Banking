package com.centralisateur.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// On définit le chemin du contenu à inclure dans le layout
		request.setAttribute("contentPage", "/bienvenue.jsp");
		// On forward vers le layout.jsp qui inclura le contenu
		request.getRequestDispatcher("/views/includes/layout.jsp").forward(request, response);
	}
}
