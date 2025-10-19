<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Transactions du compte prêt" />

<div class="row">
    <div class="col-12">
        <!-- Page Header -->
        <div class="page-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1 class="page-title">
                        <i class="fas fa-exchange-alt me-3"></i>
                        Transactions du compte prêt
                    </h1>
                    <p class="page-subtitle">Effectuez des opérations sur le compte prêt</p>
                </div>
                <div class="page-actions">
                    <a href="${pageContext.request.contextPath}/compte-pret/list" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left me-2"></i>
                        Retour à la liste
                    </a>
                </div>
            </div>
        </div>

        <!-- Choix de l'opération -->
        <div class="row g-4 mb-4">
            <div class="col-md-6">
                <div class="card h-100 border-success" onclick="showSection('preter')">
                    <div class="card-body text-center">
                        <i class="fas fa-hand-holding-usd fa-3x text-success mb-3"></i>
                        <h5 class="card-title">Prêter de l'argent</h5>
                        <p class="card-text">Accorder un nouveau prêt à ce compte</p>
                        <button class="btn btn-success">Sélectionner</button>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card h-100 border-warning" onclick="window.location.href='${pageContext.request.contextPath}/compte-pret/contrats?compteId=${param.compteId}'">
                    <div class="card-body text-center">
                        <i class="fas fa-money-bill-wave fa-3x text-warning mb-3"></i>
                        <h5 class="card-title">Rembourser</h5>
                        <p class="card-text">Effectuer un remboursement sur ce prêt</p>
                        <button class="btn btn-warning">Sélectionner</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Section Prêter -->
        <div id="section-preter" class="d-none">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h5 class="mb-0">
                        <i class="fas fa-hand-holding-usd me-2"></i>
                        Effectuer un prêt
                    </h5>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/compte-pret/preter" method="post" class="form">
                        <input type="hidden" name="comptePretId" value="${param.compteId}" />
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="montant" class="form-label">Montant du prêt</label>
                                    <input type="number" step="0.01" min="0" name="montant" id="montant" class="form-control" required />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="typePaiementId" class="form-label">Type de paiement</label>
                                    <select name="typePaiementId" id="typePaiementId" class="form-control" required>
                                        <option value="">-- Sélectionner un type --</option>
                                        <c:forEach var="type" items="${typesPaiement}">
                                            <option value="${type.key}">${type.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="duree" class="form-label">Durée (en mois)</label>
                                    <input type="number" min="1" name="duree" id="duree" class="form-control" required />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="datePret" class="form-label">Date du prêt</label>
                                    <input type="datetime-local" name="datePret" id="datePret" class="form-control" required />
                                </div>
                            </div>
                        </div>
                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-success">
                                <i class="fas fa-check me-2"></i>
                                Valider le prêt
                            </button>
                            <button type="button" class="btn btn-secondary" onclick="hideSections()">
                                <i class="fas fa-times me-2"></i>
                                Annuler
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Section Rembourser -->
        <div id="section-rembourser" class="d-none">
            <div class="card">
                <div class="card-header bg-warning text-dark">
                    <h5 class="mb-0">
                        <i class="fas fa-money-bill-wave me-2"></i>
                        Rembourser
                    </h5>
                </div>
                <div class="card-body">
                    <div class="alert alert-info">
                        <i class="fas fa-info-circle me-2"></i>
                        Fonctionnalité de remboursement en cours de développement.
                        Cette section permettra bientôt d'effectuer des remboursements sur les prêts existants.
                    </div>
                    <div class="text-center">
                        <button type="button" class="btn btn-secondary" onclick="hideSections()">
                            <i class="fas fa-times me-2"></i>
                            Fermer
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Messages -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger mt-3">
                <i class="fas fa-exclamation-triangle me-2"></i>
                ${errorMessage}
            </div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success mt-3">
                <i class="fas fa-check-circle me-2"></i>
                ${successMessage}
            </div>
        </c:if>
    </div>
</div>

<script>
function showSection(section) {
    // Masquer toutes les sections
    document.getElementById('section-preter').classList.add('d-none');
    document.getElementById('section-rembourser').classList.add('d-none');

    // Afficher la section sélectionnée
    document.getElementById('section-' + section).classList.remove('d-none');

    // Faire défiler vers la section
    document.getElementById('section-' + section).scrollIntoView({ behavior: 'smooth' });
}

function hideSections() {
    document.getElementById('section-preter').classList.add('d-none');
    document.getElementById('section-rembourser').classList.add('d-none');
}
</script>