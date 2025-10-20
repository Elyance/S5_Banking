<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="Créer un compte bancaire" />

<div class="row">
    <div class="col-12">
        <!-- Page Header -->
        <div class="page-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h1 class="page-title">
                        <i class="fas fa-plus-circle me-3"></i>
                        Créer un compte bancaire
                    </h1>
                    <p class="page-subtitle">Choisissez le type de compte que vous souhaitez créer pour <strong>${client.prenom} ${client.nom}</strong></p>
                </div>
                <div class="page-actions">
                    <a href="${pageContext.request.contextPath}/clients/situation?id=${client.id}" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left me-2"></i>
                        Retour à la situation
                    </a>
                </div>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/comptes/create" method="post">
            <input type="hidden" name="clientId" value="${param.clientId}">
            <input type="hidden" name="typeCompte" value="">

            <!-- Date de création -->
            <div class="card mb-4">
                <div class="card-body">
                    <div class="mb-3">
                        <label for="dateCreation" class="form-label">Date de création du compte</label>
                        <input type="datetime-local" class="form-control" id="dateCreation" name="dateCreation" required>
                    </div>
                </div>
            </div>

            <!-- Types de comptes -->
            <div class="row g-4">
                <!-- Compte Courant -->
                <div class="col-lg-4">
                    <div class="card h-100 border-primary">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">
                                <i class="fas fa-credit-card me-2"></i>
                                Compte Courant
                            </h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text">
                                Un compte bancaire classique pour vos opérations quotidiennes :
                                dépôts, retraits, virements, paiements par carte...
                            </p>
                            <ul class="list-unstyled">
                                <li><i class="fas fa-check text-success me-2"></i>Découvert autorisé</li>
                                <li><i class="fas fa-check text-success me-2"></i>Carte bancaire</li>
                                <li><i class="fas fa-check text-success me-2"></i>Virements SEPA</li>
                                <li><i class="fas fa-check text-success me-2"></i>Prélèvements</li>
                            </ul>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary w-100" onclick="this.form.typeCompte.value='compte-courant';">
                                <i class="fas fa-plus me-2"></i>
                                Créer un compte courant
                            </button>
                        </div>
                    </div>
                </div>

            <!-- Compte Prêt -->
            <div class="col-lg-4">
                    <div class="card h-100 border-warning">
                        <div class="card-header bg-warning text-dark">
                            <h5 class="mb-0">
                                <i class="fas fa-hand-holding-usd me-2"></i>
                                Compte Prêt
                            </h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text">
                                Un prêt bancaire pour financer vos projets :
                                immobilier, consommation, travaux, véhicule...
                            </p>
                            <ul class="list-unstyled">
                                <li><i class="fas fa-check text-success me-2"></i>Taux d'intérêt fixe</li>
                                <li><i class="fas fa-check text-success me-2"></i>Mensualités constantes</li>
                                <li><i class="fas fa-check text-success me-2"></i>Durée flexible</li>
                                <li><i class="fas fa-check text-success me-2"></i>Assurance optionnelle</li>
                            </ul>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-warning w-100" onclick="this.form.action='${pageContext.request.contextPath}/compte-pret/preview'; this.form.typeCompte.value='compte-pret'; setDateForPret();">
                                <i class="fas fa-plus me-2"></i>
                                Créer un compte prêt
                            </button>
                        </div>
                    </div>
            </div>

            <!-- Compte Dépôt -->
            <div class="col-lg-4">
                <div class="card h-100 border-success">
                    <div class="card-header bg-success text-white">
                        <h5 class="mb-0">
                            <i class="fas fa-piggy-bank me-2"></i>
                            Compte Dépôt
                        </h5>
                    </div>
                    <div class="card-body">
                        <p class="card-text">
                            Un compte d'épargne pour faire fructifier votre argent :
                            livrets, dépôts à terme, assurance-vie...
                        </p>
                        <ul class="list-unstyled">
                            <li><i class="fas fa-check text-success me-2"></i>Taux d'intérêt attractif</li>
                            <li><i class="fas fa-check text-success me-2"></i>Capital garanti</li>
                            <li><i class="fas fa-check text-success me-2"></i>Fiscalité avantageuse</li>
                            <li><i class="fas fa-check text-success me-2"></i>Disponibilité flexible</li>
                        </ul>
                    </div>
                    <div class="card-footer">
                        <button type="submit" class="btn btn-success w-100" onclick="this.form.action='${pageContext.request.contextPath}/compte-depot/preview'; this.form.typeCompte.value='compte-depot';">
                            <i class="fas fa-plus me-2"></i>
                            Créer un compte dépôt
                        </button>
                    </div>
                </div>
            </div>
        </div>
        </form>

        <!-- Informations complémentaires -->
        <div class="row mt-4">
            <div class="col-12">
                <div class="card bg-light">
                    <div class="card-body">
                        <h6 class="card-title">
                            <i class="fas fa-info-circle me-2"></i>
                            Informations importantes
                        </h6>
                        <div class="row">
                            <div class="col-md-4">
                                <h6>Compte Courant</h6>
                                <p class="small mb-0">
                                    Idéal pour vos dépenses quotidiennes. Frais de tenue de compte possibles selon l'offre choisie.
                                </p>
                            </div>
                            <div class="col-md-4">
                                <h6>Compte Prêt</h6>
                                <p class="small mb-0">
                                    Étudiez bien vos capacités de remboursement. L'assurance emprunteur est recommandée.
                                </p>
                            </div>
                            <div class="col-md-4">
                                <h6>Compte Dépôt</h6>
                                <p class="small mb-0">
                                    Comparez les taux et conditions. Certains dépôts ont des conditions de retrait.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
function setDateForPret() {
    var dateValue = document.getElementById('dateCreation').value;
    document.getElementById('dateCreationPret').value = dateValue;
}
</script>