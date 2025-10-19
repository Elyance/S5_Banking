<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="pageTitle" value="Situation de ${client.prenom} ${client.nom}" />

<div class="row">
    <div class="col-12">
        <!-- En-tête avec informations client -->
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h4 class="mb-1">
                            <i class="fas fa-user-circle me-2"></i>
                            Situation de ${client.prenom} ${client.nom}
                        </h4>
                        <p class="mb-0 opacity-75">
                            <i class="fas fa-id-card me-1"></i>
                            ${client.numeroClient}
                        </p>
                    </div>
                    <div class="text-end">
                        <small class="text-white-50">Dernière mise à jour</small>
                        <div class="fw-bold">
                            <fmt:formatDate value="${dateCreationFormatted}" pattern="dd/MM/yyyy"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <h5 class="text-primary mb-3">
                            <i class="fas fa-info-circle me-2"></i>
                            Informations personnelles
                        </h5>
                        <div class="row g-3">
                            <div class="col-sm-6">
                                <div class="border-start border-primary border-4 ps-3">
                                    <small class="text-muted">Email</small>
                                    <div class="fw-bold">${client.email}</div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="border-start border-primary border-4 ps-3">
                                    <small class="text-muted">Téléphone</small>
                                    <div class="fw-bold">${client.telephone}</div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="border-start border-primary border-4 ps-3">
                                    <small class="text-muted">Profession</small>
                                    <div class="fw-bold">${client.profession}</div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="border-start border-primary border-4 ps-3">
                                    <small class="text-muted">Date de naissance</small>
                                    <div class="fw-bold">
                                        <fmt:formatDate value="${dateNaissanceFormatted}" pattern="dd/MM/yyyy"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <h5 class="text-success mb-3">
                            <i class="fas fa-chart-line me-2"></i>
                            Résumé financier
                        </h5>
                        <div class="card bg-light">
                            <div class="card-body text-center">
                                <c:choose>
                                    <c:when test="${not empty totalBalance}">
                                        <div class="display-4 text-success fw-bold mb-2">
                                            <i class="fas fa-euro-sign me-2"></i>
                                            <fmt:formatNumber value="${totalBalance}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="display-4 text-success fw-bold mb-2">
                                            <i class="fas fa-euro-sign me-2"></i>
                                            0,00 €
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="text-muted">Solde total dans la banque</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Comptes du client -->
        <div class="card">
            <div class="card-header bg-success text-white">
                <h5 class="mb-0">
                    <i class="fas fa-credit-card me-2"></i>
                    Comptes bancaires
                </h5>
            </div>
            <div class="card-body">
                <!-- Comptes Courants -->
                <h5 class="mb-3">Comptes Courants</h5>
                <div class="row mb-4">
                    <div class="col-12">
                        <div class="d-flex overflow-x-auto pb-2">
                            <c:forEach var="compte" items="${comptesCourant}">
                                <div class="card border-primary h-100 me-3" style="min-width: 300px;">
                                    <div class="card-header bg-primary text-white">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <h6 class="mb-0">
                                                <i class="fas fa-credit-card me-2"></i>
                                                Compte Courant
                                            </h6>
                                            <span class="badge bg-light text-primary">${compte.statusLibelle}</span>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-6">
                                                <small class="text-muted">Numéro de compte</small>
                                                <div class="fw-bold">${compte.numeroCompte}</div>
                                            </div>
                                            <div class="col-6">
                                                <small class="text-muted">Solde actuel</small>
                                                <div class="fw-bold text-primary fs-5">
                                                    <fmt:formatNumber value="${compte.solde}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €
                                                </div>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-6">
                                                <small class="text-muted">Découvert autorisé</small>
                                                <div class="fw-bold">
                                                    <fmt:formatNumber value="${compte.decouvertAutorise}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <small class="text-muted">Date d'ouverture</small>
                                                <div class="fw-bold">
                                                    <fmt:formatDate value="${compte.dateCreation}" pattern="dd/MM/yyyy"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-footer">
                                        <div class="btn-group w-100">
                                            <a href="${pageContext.request.contextPath}/compte-courant/transaction?compteId=${compte.id}"
                                               class="btn btn-outline-primary btn-sm">
                                                <i class="fas fa-exchange-alt me-1"></i>
                                                Transaction
                                            </a>
                                            <a href="${pageContext.request.contextPath}/compte-courant/transactions/list?compteId=${compte.id}"
                                               class="btn btn-outline-info btn-sm">
                                                <i class="fas fa-eye me-1"></i>
                                                Détails
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <!-- Comptes Prêts -->
                <h5 class="mb-3">Comptes Prêts</h5>
                <div class="row mb-4">
                    <div class="col-12">
                        <div class="d-flex overflow-x-auto pb-2">
                            <!-- Exemple de compte prêt -->
                            <div class="card border-warning h-100 me-3" style="min-width: 300px;">
                                <div class="card-header bg-warning text-dark">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <h6 class="mb-0">
                                            <i class="fas fa-hand-holding-usd me-2"></i>
                                            Compte Prêt
                                        </h6>
                                        <span class="badge bg-light text-warning">En cours</span>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-6">
                                            <small class="text-muted">Numéro de prêt</small>
                                            <div class="fw-bold">PR00123456</div>
                                        </div>
                                        <div class="col-6">
                                            <small class="text-muted">Montant restant</small>
                                            <div class="fw-bold text-warning fs-5">-15,000.00 €</div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-6">
                                            <small class="text-muted">Taux d'intérêt</small>
                                            <div class="fw-bold">4.5%</div>
                                        </div>
                                        <div class="col-6">
                                            <small class="text-muted">Mensualités</small>
                                            <div class="fw-bold">450.00 €</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-footer">
                                    <div class="btn-group w-100">
                                        <a href="${pageContext.request.contextPath}/compte-pret/transaction?compteId=2"
                                           class="btn btn-outline-warning btn-sm">
                                            <i class="fas fa-exchange-alt me-1"></i>
                                            Transaction
                                        </a>
                                        <a href="${pageContext.request.contextPath}/compte-pret/list"
                                           class="btn btn-outline-info btn-sm">
                                            <i class="fas fa-eye me-1"></i>
                                            Détails
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Comptes Dépôts -->
                <h5 class="mb-3">Comptes Dépôts</h5>
                <div class="row">
                    <div class="col-12">
                        <div class="d-flex overflow-x-auto pb-2">
                            <!-- Exemple de compte dépôt -->
                            <div class="card border-info h-100 me-3" style="min-width: 300px;">
                                <div class="card-header bg-info text-white">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <h6 class="mb-0">
                                            <i class="fas fa-piggy-bank me-2"></i>
                                            Compte Dépôt
                                        </h6>
                                        <span class="badge bg-light text-info">Actif</span>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-6">
                                            <small class="text-muted">Numéro de compte</small>
                                            <div class="fw-bold">DP00123456</div>
                                        </div>
                                        <div class="col-6">
                                            <small class="text-muted">Solde actuel</small>
                                            <div class="fw-bold text-info fs-5">5,200.00 €</div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-6">
                                            <small class="text-muted">Taux d'intérêt</small>
                                            <div class="fw-bold">1.25%</div>
                                        </div>
                                        <div class="col-6">
                                            <small class="text-muted">Date d'ouverture</small>
                                            <div class="fw-bold">20/06/2023</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-footer">
                                    <div class="btn-group w-100">
                                        <a href="${pageContext.request.contextPath}/compte-depot/transaction?compteId=3"
                                           class="btn btn-outline-info btn-sm">
                                            <i class="fas fa-exchange-alt me-1"></i>
                                            Transaction
                                        </a>
                                        <a href="${pageContext.request.contextPath}/compte-depot/list"
                                           class="btn btn-outline-info btn-sm">
                                            <i class="fas fa-eye me-1"></i>
                                            Détails
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Message si aucun compte -->
                <c:if test="${empty comptesCourant}">
                    <div class="text-center py-5">
                        <i class="fas fa-credit-card fa-3x text-muted mb-3"></i>
                        <h5 class="text-muted">Aucun compte trouvé</h5>
                        <p class="text-muted">Ce client n'a pas encore de compte bancaire.</p>
                        <a href="${pageContext.request.contextPath}/comptes/select-type?clientId=${client.id}"
                           class="btn btn-primary">
                            <i class="fas fa-plus me-2"></i>
                            Créer un compte
                        </a>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Actions rapides -->
        <div class="row mt-4">
            <div class="col-12">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">
                            <i class="fas fa-bolt me-2"></i>
                            Actions rapides
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="row g-3">
                            <div class="col-md-3">
                                <a href="${pageContext.request.contextPath}/clients/edit?id=${client.id}"
                                   class="btn btn-outline-primary w-100">
                                    <i class="fas fa-edit fa-2x mb-2"></i>
                                    <div>Modifier le client</div>
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a href="${pageContext.request.contextPath}/comptes/select-type?clientId=${client.id}"
                                   class="btn btn-outline-success w-100">
                                    <i class="fas fa-credit-card fa-2x mb-2"></i>
                                    <div>Nouveau compte</div>
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a href="${pageContext.request.contextPath}/compte-courant/transaction?compteId=1"
                                   class="btn btn-outline-info w-100">
                                    <i class="fas fa-exchange-alt fa-2x mb-2"></i>
                                    <div>Transaction</div>
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a href="${pageContext.request.contextPath}/clients"
                                   class="btn btn-outline-secondary w-100">
                                    <i class="fas fa-arrow-left fa-2x mb-2"></i>
                                    <div>Retour à la liste</div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>