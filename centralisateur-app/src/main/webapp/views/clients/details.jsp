<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Page Header -->
<div class="page-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h1 class="page-title">
                <i class="fas fa-user-circle me-3"></i>
                Détails du Client
            </h1>
            <p class="page-subtitle">Informations complètes du client ${client.nom} ${client.prenom}</p>
        </div>
        <div class="page-actions">
            <a href="${pageContext.request.contextPath}/clients/edit?id=${client.id}" class="btn btn-warning me-2">
                <i class="fas fa-edit me-2"></i>
                Modifier
            </a>
            <a href="${pageContext.request.contextPath}/clients" class="btn btn-outline-secondary">
                <i class="fas fa-arrow-left me-2"></i>
                Retour à la liste
            </a>
        </div>
    </div>
</div>

<c:if test="${not empty client}">
    <!-- Informations principales -->
    <div class="row mb-4">
        <div class="col-lg-8">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title">
                        <i class="fas fa-id-card me-2"></i>
                        Informations Personnelles
                    </h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label text-muted small">ID Client</label>
                                <div class="d-flex align-items-center">
                                    <span class="badge bg-primary me-2">${client.id}</span>
                                    <code class="text-primary">${client.numeroClient}</code>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label text-muted small">Statut</label>
                                <div>
                                    <c:choose>
                                        <c:when test="${client.statutLibelle == 'ACTIF'}">
                                            <span class="badge bg-success fs-6">
                                                <i class="fas fa-check-circle me-1"></i>
                                                ${client.statutLibelle}
                                            </span>
                                        </c:when>
                                        <c:when test="${client.statutLibelle == 'INACTIF'}">
                                            <span class="badge bg-warning text-dark fs-6">
                                                <i class="fas fa-pause-circle me-1"></i>
                                                ${client.statutLibelle}
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary fs-6">
                                                <i class="fas fa-question-circle me-1"></i>
                                                ${client.statutLibelle}
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label text-muted small">
                                    <i class="fas fa-user me-1"></i>
                                    Nom complet
                                </label>
                                <h5 class="mb-0">${client.nom} ${client.prenom}</h5>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label text-muted small">
                                    <i class="fas fa-birthday-cake me-1"></i>
                                    Date de naissance
                                </label>
                                <p class="mb-0">${client.dateNaissance}</p>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label text-muted small">
                                    <i class="fas fa-envelope me-1"></i>
                                    Email
                                </label>
                                <p class="mb-0">
                                    <a href="mailto:${client.email}" class="text-decoration-none">
                                        ${client.email}
                                    </a>
                                </p>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label text-muted small">
                                    <i class="fas fa-phone me-1"></i>
                                    Téléphone
                                </label>
                                <p class="mb-0">
                                    <a href="tel:${client.telephone}" class="text-decoration-none">
                                        ${client.telephone}
                                    </a>
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label text-muted small">
                                    <i class="fas fa-map-marker-alt me-1"></i>
                                    Adresse
                                </label>
                                <p class="mb-0">${client.adresse}</p>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label text-muted small">
                                    <i class="fas fa-briefcase me-1"></i>
                                    Profession
                                </label>
                                <p class="mb-0">${client.profession}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Informations temporelles -->
        <div class="col-lg-4">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title">
                        <i class="fas fa-clock me-2"></i>
                        Historique
                    </h5>
                </div>
                <div class="card-body">
                    <div class="mb-3">
                        <label class="form-label text-muted small">
                            <i class="fas fa-calendar-plus me-1"></i>
                            Date de création
                        </label>
                        <p class="mb-0 fw-bold text-primary">${client.dateCreation}</p>
                    </div>

                    <div class="mb-3">
                        <label class="form-label text-muted small">
                            <i class="fas fa-history me-1"></i>
                            Dernière modification
                        </label>
                        <p class="mb-0">${client.dateDernierStatut}</p>
                    </div>

                    <hr>

                    <div class="d-grid gap-2">
                        <a href="${pageContext.request.contextPath}/clients/edit?id=${client.id}" class="btn btn-warning">
                            <i class="fas fa-edit me-2"></i>
                            Modifier le client
                        </a>
                        <button type="button" class="btn btn-outline-danger" onclick="confirmDelete('${client.id}', '${client.nom} ${client.prenom}')">
                            <i class="fas fa-trash me-2"></i>
                            Supprimer le client
                        </button>
                    </div>
                </div>
            </div>

            <!-- Avatar -->
            <div class="card mt-3">
                <div class="card-body text-center">
                    <div class="avatar-circle mb-3" style="width: 80px; height: 80px; margin: 0 auto;">
                        <i class="fas fa-user fa-2x"></i>
                    </div>
                    <h6>${client.nom} ${client.prenom}</h6>
                    <p class="text-muted small mb-0">Client #${client.numeroClient}</p>
                </div>
            </div>
        </div>
    </div>
</c:if>

<!-- Modal de confirmation de suppression -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">
                    <i class="fas fa-exclamation-triangle text-warning me-2"></i>
                    Confirmation de suppression
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Êtes-vous sûr de vouloir supprimer le client <strong id="clientName"></strong> ?</p>
                <p class="text-danger mb-0">
                    <i class="fas fa-warning me-1"></i>
                    Cette action est irréversible.
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="fas fa-times me-1"></i>
                    Annuler
                </button>
                <form id="deleteForm" action="${pageContext.request.contextPath}/clients/delete" method="post" style="display: inline;">
                    <input type="hidden" name="id" id="deleteClientId" />
                    <button type="submit" class="btn btn-danger">
                        <i class="fas fa-trash me-1"></i>
                        Supprimer
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
function confirmDelete(clientId, clientName) {
    document.getElementById('deleteClientId').value = clientId;
    document.getElementById('clientName').textContent = clientName;
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}
</script>
