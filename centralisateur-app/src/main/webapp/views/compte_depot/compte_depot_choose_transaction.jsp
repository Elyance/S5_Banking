<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="Choisir le type de transaction" />

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">
                        <i class="fas fa-exchange-alt me-2"></i>
                        Choisir le type de transaction
                    </h4>
                </div>
                <div class="card-body">
                    <p class="text-muted">Sélectionnez le type d'opération que vous souhaitez effectuer sur ce compte dépôt.</p>

                    <div class="row g-4">
                        <!-- Dépôt -->
                        <div class="col-md-6">
                            <div class="card h-100 border-success">
                                <div class="card-body text-center">
                                    <i class="fas fa-plus-circle fa-3x text-success mb-3"></i>
                                    <h5 class="card-title text-success">Dépôt</h5>
                                    <p class="card-text">Ajouter de l'argent sur le compte dépôt.</p>
                                    <a href="${pageContext.request.contextPath}/compte-depot/depot?compteId=${compteId}"
                                       class="btn btn-success btn-lg">
                                        <i class="fas fa-plus me-2"></i>
                                        Effectuer un dépôt
                                    </a>
                                </div>
                            </div>
                        </div>

                        <!-- Décaissement -->
                        <div class="col-md-6">
                            <div class="card h-100 border-warning">
                                <div class="card-body text-center">
                                    <i class="fas fa-minus-circle fa-3x text-warning mb-3"></i>
                                    <h5 class="card-title text-warning">Décaissement</h5>
                                    <p class="card-text">Retirer de l'argent du compte dépôt.</p>
                                    <button class="btn btn-warning btn-lg" disabled>
                                        <i class="fas fa-minus me-2"></i>
                                        Fonctionnalité à venir
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="text-center mt-4">
                        <a href="${pageContext.request.contextPath}/clients" class="btn btn-outline-secondary">
                            <i class="fas fa-arrow-left me-2"></i>
                            Retour à la liste des clients
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>