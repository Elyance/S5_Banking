<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Page Header -->
<div class="page-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h1 class="page-title">
                <i class="fas fa-user-edit me-3"></i>
                Modifier le Client
            </h1>
            <p class="page-subtitle">Mettre à jour les informations du client ${client.nom} ${client.prenom}</p>
        </div>
        <div class="page-actions">
            <a href="${pageContext.request.contextPath}/clients/details?id=${client.id}" class="btn btn-outline-info me-2">
                <i class="fas fa-eye me-2"></i>
                Voir détails
            </a>
            <a href="${pageContext.request.contextPath}/clients" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left me-2"></i>
                Retour à la liste
            </a>
        </div>
    </div>
</div>

<c:if test="${not empty client}">
    <!-- Alert pour les erreurs -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-triangle me-2"></i>
            <strong>Erreur !</strong> ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Formulaire de modification -->
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title">
                        <i class="fas fa-user-edit me-2"></i>
                        Modifier les Informations
                    </h5>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/clients/edit" method="post" id="editForm" novalidate>
                        <input type="hidden" name="id" value="${client.id}" />

                        <!-- Informations d'identification -->
                        <div class="mb-4">
                            <h6 class="text-muted mb-3">
                                <i class="fas fa-id-card me-1"></i>
                                Informations d'identification
                            </h6>
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="mb-3">
                                        <label class="form-label text-muted small">ID Client</label>
                                        <input type="text" class="form-control" value="${client.id}" readonly>
                                    </div>
                                </div>
                                <div class="col-md-8">
                                    <div class="mb-3">
                                        <label class="form-label text-muted small">Numéro Client</label>
                                        <input type="text" class="form-control" value="${client.numeroClient}" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <hr>

                        <!-- Informations personnelles -->
                        <div class="mb-4">
                            <h6 class="text-muted mb-3">
                                <i class="fas fa-user me-1"></i>
                                Informations personnelles
                            </h6>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="nom" class="form-label">
                                            Nom <span class="text-danger">*</span>
                                        </label>
                                        <input type="text" class="form-control" id="nom" name="nom"
                                               value="${client.nom}" required>
                                        <div class="invalid-feedback">
                                            Le nom est obligatoire.
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="prenom" class="form-label">
                                            Prénom <span class="text-danger">*</span>
                                        </label>
                                        <input type="text" class="form-control" id="prenom" name="prenom"
                                               value="${client.prenom}" required>
                                        <div class="invalid-feedback">
                                            Le prénom est obligatoire.
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="dateNaissance" class="form-label">
                                            <i class="fas fa-birthday-cake me-1"></i>
                                            Date de naissance <span class="text-danger">*</span>
                                        </label>
                                        <input type="date" class="form-control" id="dateNaissance" name="dateNaissance"
                                               value="${client.dateNaissance}" required>
                                        <div class="invalid-feedback">
                                            La date de naissance est obligatoire.
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="profession" class="form-label">
                                            <i class="fas fa-briefcase me-1"></i>
                                            Profession <span class="text-danger">*</span>
                                        </label>
                                        <input type="text" class="form-control" id="profession" name="profession"
                                               value="${client.profession}" required>
                                        <div class="invalid-feedback">
                                            La profession est obligatoire.
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <hr>

                        <!-- Coordonnées -->
                        <div class="mb-4">
                            <h6 class="text-muted mb-3">
                                <i class="fas fa-address-book me-1"></i>
                                Coordonnées
                            </h6>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="email" class="form-label">
                                            <i class="fas fa-envelope me-1"></i>
                                            Email <span class="text-danger">*</span>
                                        </label>
                                        <input type="email" class="form-control" id="email" name="email"
                                               value="${client.email}" required>
                                        <div class="invalid-feedback">
                                            Veuillez entrer un email valide.
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="telephone" class="form-label">
                                            <i class="fas fa-phone me-1"></i>
                                            Téléphone <span class="text-danger">*</span>
                                        </label>
                                        <input type="text" class="form-control" id="telephone" name="telephone"
                                               value="${client.telephone}" required>
                                        <div class="invalid-feedback">
                                            Le numéro de téléphone est obligatoire.
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="adresse" class="form-label">
                                    <i class="fas fa-map-marker-alt me-1"></i>
                                    Adresse <span class="text-danger">*</span>
                                </label>
                                <textarea class="form-control" id="adresse" name="adresse" rows="3" required>${client.adresse}</textarea>
                                <div class="invalid-feedback">
                                    L'adresse est obligatoire.
                                </div>
                            </div>
                        </div>

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <a href="${pageContext.request.contextPath}/clients" class="btn btn-outline-secondary me-md-2">
                                <i class="fas fa-times me-1"></i>
                                Annuler
                            </a>
                            <button type="submit" class="btn btn-success">
                                <i class="fas fa-save me-2"></i>
                                Enregistrer les modifications
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Informations du statut -->
            <div class="card mt-4">
                <div class="card-body">
                    <h6 class="card-title">
                        <i class="fas fa-info-circle me-2"></i>
                        Informations du compte
                    </h6>
                    <div class="row">
                        <div class="col-md-6">
                            <p class="mb-1">
                                <strong>Statut actuel :</strong>
                                <c:choose>
                                    <c:when test="${client.statutLibelle == 'ACTIF'}">
                                        <span class="badge bg-success ms-2">
                                            <i class="fas fa-check-circle me-1"></i>
                                            ${client.statutLibelle}
                                        </span>
                                    </c:when>
                                    <c:when test="${client.statutLibelle == 'INACTIF'}">
                                        <span class="badge bg-warning text-dark ms-2">
                                            <i class="fas fa-pause-circle me-1"></i>
                                            ${client.statutLibelle}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary ms-2">
                                            <i class="fas fa-question-circle me-1"></i>
                                            ${client.statutLibelle}
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="col-md-6">
                            <p class="mb-1">
                                <strong>Dernière modification :</strong>
                                <span class="text-muted">${client.dateDernierStatut}</span>
                            </p>
                        </div>
                    </div>
                    <p class="mb-0">
                        <strong>Date de création :</strong>
                        <span class="text-muted">${client.dateCreation}</span>
                    </p>
                </div>
            </div>
        </div>
    </div>
</c:if>

<script>
// Validation du formulaire Bootstrap
(function () {
    'use strict'

    var forms = document.querySelectorAll('#editForm')

    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()

// Validation en temps réel de l'email
document.getElementById('email').addEventListener('blur', function() {
    const email = this.value;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (email && !emailRegex.test(email)) {
        this.classList.add('is-invalid');
    } else {
        this.classList.remove('is-invalid');
    }
});

// Formatage automatique du téléphone
document.getElementById('telephone').addEventListener('input', function() {
    let value = this.value.replace(/\D/g, '');
    if (value.length >= 10) {
        if (value.startsWith('33')) {
            this.value = '+' + value.slice(0, 2) + ' ' + value.slice(2, 4) + ' ' +
                         value.slice(4, 6) + ' ' + value.slice(6, 8) + ' ' + value.slice(8, 10);
        } else {
            this.value = value.slice(0, 2) + ' ' + value.slice(2, 4) + ' ' +
                         value.slice(4, 6) + ' ' + value.slice(6, 8) + ' ' + value.slice(8, 10);
        }
    }
});
</script>
