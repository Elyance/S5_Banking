<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Page Header -->
<div class="page-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h1 class="page-title">
                <i class="fas fa-user-plus me-3"></i>
                Nouveau Client
            </h1>
            <p class="page-subtitle">Créer un nouveau client dans le système bancaire</p>
        </div>
        <div class="page-actions">
            <a href="${pageContext.request.contextPath}/clients" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left me-2"></i>
                Retour à la liste
            </a>
        </div>
    </div>
</div>

<!-- Alert pour les erreurs -->
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="fas fa-exclamation-triangle me-2"></i>
        <strong>Erreur !</strong> ${errorMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<!-- Formulaire de création -->
<div class="row justify-content-center">
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header">
                <h5 class="card-title">
                    <i class="fas fa-user-plus me-2"></i>
                    Informations du Nouveau Client
                </h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/clients/create" method="post" id="createForm" novalidate>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="nom" class="form-label">
                                    <i class="fas fa-user me-1"></i>
                                    Nom <span class="text-danger">*</span>
                                </label>
                                <input type="text" class="form-control" id="nom" name="nom"
                                       placeholder="Entrez le nom" required>
                                <div class="invalid-feedback">
                                    Le nom est obligatoire.
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="prenom" class="form-label">
                                    <i class="fas fa-user me-1"></i>
                                    Prénom <span class="text-danger">*</span>
                                </label>
                                <input type="text" class="form-control" id="prenom" name="prenom"
                                       placeholder="Entrez le prénom" required>
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
                                <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" required>
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
                                       placeholder="Ex: Développeur, Enseignant..." required>
                                <div class="invalid-feedback">
                                    La profession est obligatoire.
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="email" class="form-label">
                                    <i class="fas fa-envelope me-1"></i>
                                    Email <span class="text-danger">*</span>
                                </label>
                                <input type="email" class="form-control" id="email" name="email"
                                       placeholder="exemple@email.com" required>
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
                                       placeholder="+33 6 XX XX XX XX" required>
                                <div class="invalid-feedback">
                                    Le numéro de téléphone est obligatoire.
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="adresse" class="form-label">
                                    <i class="fas fa-map-marker-alt me-1"></i>
                                    Adresse <span class="text-danger">*</span>
                                </label>
                                <input type="text" class="form-control" id="adresse" name="adresse"
                                       placeholder="Ex: 123 Rue de Paris..." required>
                                <div class="invalid-feedback">
                                    L'adresse est obligatoire.
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="dateCreation" class="form-label">
                                    <i class="fas fa-calendar-alt me-1"></i>
                                    Date de création <span class="text-danger">*</span>
                                </label>
                                <input type="datetime-local" class="form-control" id="dateCreation" name="dateCreation" required>
                                <div class="invalid-feedback">
                                    La date de création est obligatoire.
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <a href="${pageContext.request.contextPath}/clients" class="btn btn-outline-secondary me-md-2">
                            <i class="fas fa-times me-1"></i>
                            Annuler
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>
                            Créer le client
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Informations supplémentaires -->
        <div class="card mt-4">
            <div class="card-body">
                <h6 class="card-title">
                    <i class="fas fa-info-circle me-2"></i>
                    Informations importantes
                </h6>
                <ul class="mb-0 small text-muted">
                    <li>Tous les champs marqués d'un <span class="text-danger">*</span> sont obligatoires</li>
                    <li>L'email doit être unique dans le système</li>
                    <li>Le numéro de téléphone doit être au format international</li>
                    <li>Le client sera créé avec le statut "ACTIF" par défaut</li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script>
// Validation du formulaire Bootstrap
(function () {
    'use strict'

    var forms = document.querySelectorAll('.needs-validation')

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
