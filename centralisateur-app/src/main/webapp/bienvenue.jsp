<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.centralisateur.service.AuthService" %>
<%@ page import="com.compte_courant.dto.UtilisateurDTO" %>

<!-- Message de bienvenue personnalisé -->
<%
    HttpSession currentSession = request.getSession(false);
    if (currentSession != null) {
        AuthService authService = (AuthService) currentSession.getAttribute("authService");
        if (authService != null && authService.isLoggedIn()) {
            UtilisateurDTO currentUser = authService.getCurrentUser();
%>
<div class="row mb-4">
    <div class="col-12">
        <div class="alert alert-info border-0 shadow-sm">
            <div class="d-flex align-items-center">
                <div class="avatar-circle me-3">
                    <%= currentUser.getLogin().substring(0, 1).toUpperCase() %>
                </div>
                <div>
                    <h5 class="mb-1">Bienvenue, <%= currentUser.getLogin() %> !</h5>
                    <p class="mb-0 text-muted">Vous êtes connecté au système bancaire</p>
                </div>
            </div>
        </div>
    </div>
</div>
<%
        }
    }
%>

<!-- Vue d'ensemble -->
<div class="row mb-4">
    <div class="col-12">
        <div class="card">
            <div class="card-body text-center py-4">
                <h2 class="text-primary mb-2">
                    <i class="fas fa-university fa-2x me-3"></i>
                    Système Bancaire
                </h2>
                <p class="text-muted mb-0">Bienvenue dans votre plateforme de gestion bancaire</p>
            </div>
        </div>
    </div>
</div>

<!-- Statistiques principales -->
<div class="stats-grid mb-4">
    <div class="stat-card">
        <div class="stat-icon primary">
            <i class="fas fa-users"></i>
        </div>
        <div class="stat-content">
            <div class="stat-value">${ nbClient != null ? nbClient : 0}</div>
            <div class="stat-label">Total Clients</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon success">
            <i class="fas fa-credit-card"></i>
        </div>
        <div class="stat-content">
            <div class="stat-value">${nbCompteCourant != null ? nbCompteCourant : 0}</div>
            <div class="stat-label">Comptes Courants</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon warning">
            <i class="fas fa-hand-holding-usd"></i>
        </div>
        <div class="stat-content">
            <div class="stat-value">${nbComptePret != null ? nbComptePret : 0}</div>
            <div class="stat-label">Comptes Prêt</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon danger">
            <i class="fas fa-piggy-bank"></i>
        </div>
        <div class="stat-content">
            <div class="stat-value">0</div>
            <div class="stat-label">Comptes Dépôt</div>
        </div>
    </div>
</div>

<!-- Actions principales -->
<div class="row">
    <!-- Gestion Clients -->
    <div class="col-lg-4 mb-4">
        <div class="card h-100">
            <div class="card-header bg-primary text-white">
                <h5 class="card-title mb-0">
                    <i class="fas fa-users me-2"></i>
                    Clients
                </h5>
            </div>
            <div class="card-body">
                <p class="text-muted mb-3">Gérez votre base de clients</p>
                <div class="d-grid gap-2">
                    <a href="${pageContext.request.contextPath}/clients" class="btn btn-outline-primary">
                        <i class="fas fa-list me-2"></i>
                        Voir tous les clients
                    </a>
                    <a href="${pageContext.request.contextPath}/clients/create" class="btn btn-primary">
                        <i class="fas fa-user-plus me-2"></i>
                        Nouveau client
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Gestion Comptes -->
    <div class="col-lg-4 mb-4">
        <div class="card h-100">
            <div class="card-header bg-success text-white">
                <h5 class="card-title mb-0">
                    <i class="fas fa-credit-card me-2"></i>
                    Comptes
                </h5>
            </div>
            <div class="card-body">
                <p class="text-muted mb-3">Administrez les comptes bancaires</p>
                <div class="d-grid gap-2">
                    <a href="${pageContext.request.contextPath}/compte-courant/list" class="btn btn-outline-success">
                        <i class="fas fa-credit-card me-2"></i>
                        Comptes courants
                    </a>
                    <a href="${pageContext.request.contextPath}/compte-pret/list" class="btn btn-outline-warning">
                        <i class="fas fa-hand-holding-usd me-2"></i>
                        Comptes prêts
                    </a>
                    <a href="${pageContext.request.contextPath}/compte-depot/list" class="btn btn-outline-info">
                        <i class="fas fa-piggy-bank me-2"></i>
                        Comptes dépôts
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!--    Operations -->
    <div class="col-lg-4 mb-4">
        <div class="card h-100">
            <div class="card-header bg-info text-white">
                <h5 class="card-title mb-0">
                    <i class="fas fa-exchange-alt me-2"></i>
                    Opérations
                </h5>
            </div>
            <div class="card-body">
                <p class="text-muted mb-3">Gérez les opérations</p>
                <div class="d-grid gap-2">
                    <a href="${pageContext.request.contextPath}/compte-courant/decouvert" class="btn btn-outline-info">
                        <i class="fas fa-exchange-alt me-2"></i>
                        Nouveau découvert
                    </a>
                    <a href="${pageContext.request.contextPath}/compte-pret/taux-interet" class="btn btn-outline-secondary">
                        <i class="fas fa-percent me-2"></i>
                        Taux d'intérêt
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Section Analyses (placeholder) -->
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <h5 class="card-title">
                    <i class="fas fa-chart-line me-2"></i>
                    Analyses & Rapports
                </h5>
            </div>
            <div class="card-body text-center py-5">
                <i class="fas fa-chart-bar fa-3x text-muted mb-3"></i>
                <h5 class="text-muted">Fonctionnalité à venir</h5>
                <p class="text-muted mb-4">Graphiques et analyses détaillées seront bientôt disponibles</p>
                <a href="${pageContext.request.contextPath}/rapports" class="btn btn-outline-primary">
                    <i class="fas fa-chart-line me-2"></i>
                    Accéder aux rapports
                </a>
            </div>
        </div>
    </div>
</div>