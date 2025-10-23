<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="pageTitle" value="Aperçu du compte prêt" />

<div class="row">
    <div class="col-12">
        <!-- Page Header -->
        <div class="page-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1 class="page-title">
                        <i class="fas fa-eye me-3"></i>
                        Aperçu du compte prêt
                    </h1>
                    <p class="page-subtitle">Vérifiez les informations avant de créer le compte prêt</p>
                </div>
                <div class="page-actions">
                    <a href="${pageContext.request.contextPath}/comptes/select-type?clientId=${client.id}" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left me-2"></i>
                        Retour
                    </a>
                </div>
            </div>
        </div>

        <!-- Informations du client -->
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">
                    <i class="fas fa-user me-2"></i>
                    Informations du client
                </h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Nom :</strong> ${client.nom}</p>
                        <p><strong>Prénom :</strong> ${client.prenom}</p>
                        <p><strong>Numéro client :</strong> ${client.numeroClient}</p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Email :</strong> ${client.email}</p>
                        <p><strong>Téléphone :</strong> ${client.telephone}</p>
                        <p><strong>Profession :</strong> ${client.profession}</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Détails du compte prêt -->
        <div class="card mb-4">
            <div class="card-header bg-warning text-dark">
                <h5 class="mb-0">
                    <i class="fas fa-hand-holding-usd me-2"></i>
                    Détails du compte prêt
                </h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Date de création</label>
                            <p class="form-control-plaintext">
                                ${dateCreationFormatted}
                            </p>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold">Taux d'intérêt à la date de création</label>
                            <p class="form-control-plaintext">
                                <fmt:formatNumber value="${tauxInteret}" type="percent" minFractionDigits="2" maxFractionDigits="2"/>
                            </p>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Solde initial</label>
                            <p class="form-control-plaintext">
                                <fmt:formatNumber value="${soldeInitial}" type="currency" currencySymbol="Ariary"/>
                            </p>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold">Type de compte</label>
                            <p class="form-control-plaintext">Compte Prêt</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Actions -->
        <div class="card">
            <div class="card-body text-center">
                <p class="mb-4">Confirmez-vous la création de ce compte prêt ?</p>
                <form action="${pageContext.request.contextPath}/compte-pret/preview" method="post" class="d-inline">
                    <input type="hidden" name="clientId" value="${client.id}">
                    <input type="hidden" name="dateCreation" value="${dateCreationStr}">
                    <input type="hidden" name="confirm" value="true">
                    <button type="submit" class="btn btn-success btn-lg me-3">
                        <i class="fas fa-check me-2"></i>
                        Confirmer et créer
                    </button>
                </form>
                <a href="${pageContext.request.contextPath}/comptes/select-type?clientId=${client.id}" class="btn btn-secondary btn-lg">
                    <i class="fas fa-times me-2"></i>
                    Annuler
                </a>
            </div>
        </div>

        <!-- Message d'erreur -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger mt-3">
                <i class="fas fa-exclamation-triangle me-2"></i>
                ${errorMessage}
            </div>
        </c:if>
    </div>
</div>