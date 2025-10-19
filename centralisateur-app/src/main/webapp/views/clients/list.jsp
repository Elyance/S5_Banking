<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Page Header -->
<div class="page-header">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <h1 class="page-title">
                <i class="fas fa-users me-3"></i>
                Liste des Clients
            </h1>
            <p class="page-subtitle">Gestion et suivi de tous les clients de la banque</p>
        </div>
        <div class="page-actions">
            <a href="${pageContext.request.contextPath}/clients/create" class="btn btn-primary">
                <i class="fas fa-user-plus me-2"></i>
                Nouveau Client
            </a>
        </div>
    </div>
</div>

<!-- Stats Cards -->
<div class="stats-grid mb-4">
    <div class="stat-card">
        <div class="stat-icon primary">
            <i class="fas fa-users"></i>
        </div>
        <div class="stat-content">
            <div class="stat-value">${clients.size()}</div>
            <div class="stat-label">Total Clients</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon success">
            <i class="fas fa-user-check"></i>
        </div>
        <div class="stat-content">
            <div class="stat-value">
                <c:set var="activeCount" value="0" />
                <c:forEach var="client" items="${clients}">
                    <c:if test="${client.statutLibelle == 'ACTIF'}">
                        <c:set var="activeCount" value="${activeCount + 1}" />
                    </c:if>
                </c:forEach>
                ${activeCount}
            </div>
            <div class="stat-label">Clients Actifs</div>
        </div>
    </div>
    <div class="stat-card">
        <div class="stat-icon warning">
            <i class="fas fa-user-clock"></i>
        </div>
        <div class="stat-content">
            <div class="stat-value">
                <c:set var="inactiveCount" value="0" />
                <c:forEach var="client" items="${clients}">
                    <c:if test="${client.statutLibelle == 'INACTIF'}">
                        <c:set var="inactiveCount" value="${inactiveCount + 1}" />
                    </c:if>
                </c:forEach>
                ${inactiveCount}
            </div>
            <div class="stat-label">Clients Inactifs</div>
        </div>
    </div>
</div>

<!-- Clients Table Card -->
<div class="card">
    <div class="card-header">
        <h5 class="card-title">
            <i class="fas fa-table me-2"></i>
            Liste Détaillée des Clients
        </h5>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover table-striped">
                <thead class="table-dark">
                    <tr>
                        <th><i class="fas fa-hashtag me-1"></i>ID</th>
                        <th><i class="fas fa-id-card me-1"></i>Numéro</th>
                        <th><i class="fas fa-user me-1"></i>Nom Complet</th>
                        <th><i class="fas fa-birthday-cake me-1"></i>Naissance</th>
                        <th><i class="fas fa-envelope me-1"></i>Contact</th>
                        <th><i class="fas fa-map-marker-alt me-1"></i>Adresse</th>
                        <th><i class="fas fa-briefcase me-1"></i>Profession</th>
                        <th><i class="fas fa-info-circle me-1"></i>Statut</th>
                        <th><i class="fas fa-cogs me-1"></i>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="client" items="${clients}" varStatus="loop">
                        <tr class="align-middle">
                            <td>
                                <span class="badge bg-secondary">${client.id}</span>
                            </td>
                            <td>
                                <code class="text-primary">${client.numeroClient}</code>
                            </td>
                            <td>
                                <div>
                                    <strong>${client.nom} ${client.prenom}</strong>
                                    <br>
                                    <small class="text-muted">
                                        <i class="fas fa-calendar-plus me-1"></i>
                                        Créé: ${client.dateCreation}
                                    </small>
                                </div>
                            </td>
                            <td>
                                <i class="fas fa-calendar-day text-muted me-1"></i>
                                ${client.dateNaissance}
                            </td>
                            <td>
                                <div>
                                    <a href="mailto:${client.email}" class="text-decoration-none d-block">
                                        <i class="fas fa-envelope text-primary me-1"></i>
                                        ${client.email}
                                    </a>
                                    <a href="tel:${client.telephone}" class="text-decoration-none d-block">
                                        <i class="fas fa-phone text-success me-1"></i>
                                        ${client.telephone}
                                    </a>
                                </div>
                            </td>
                            <td>
                                <i class="fas fa-map-marker-alt text-muted me-1"></i>
                                ${client.adresse}
                            </td>
                            <td>
                                <i class="fas fa-briefcase text-info me-1"></i>
                                ${client.profession}
                            </td>
                            <td>
                                <div class="d-flex flex-column align-items-start">
                                    <c:choose>
                                        <c:when test="${client.statutLibelle == 'ACTIF'}">
                                            <span class="badge bg-success mb-1">
                                                <i class="fas fa-check-circle me-1"></i>
                                                ${client.statutLibelle}
                                            </span>
                                        </c:when>
                                        <c:when test="${client.statutLibelle == 'INACTIF'}">
                                            <span class="badge bg-warning text-dark mb-1">
                                                <i class="fas fa-pause-circle me-1"></i>
                                                ${client.statutLibelle}
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary mb-1">
                                                <i class="fas fa-question-circle me-1"></i>
                                                ${client.statutLibelle}
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                    <small class="text-muted">
                                        <i class="fas fa-clock me-1"></i>
                                        ${client.dateDernierStatut}
                                    </small>
                                </div>
                            </td>
                            <td>
                                <div class="btn-group-vertical" role="group">
                                    <a href="${pageContext.request.contextPath}/clients/details?id=${client.id}" 
                                       class="btn btn-sm btn-outline-info mb-1" 
                                       title="Voir les détails">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/clients/situation?id=${client.id}" 
                                       class="btn btn-sm btn-outline-primary mb-1" 
                                       title="Situation Client">
                                        <i class="fas fa-chart-line"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/clients/edit?id=${client.id}" 
                                       class="btn btn-sm btn-outline-warning mb-1" 
                                       title="Modifier">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <!-- Empty State -->
        <c:if test="${empty clients}">
            <div class="empty-state">
                <i class="fas fa-users"></i>
                <h3>Aucun client trouvé</h3>
                <p>Il n'y a actuellement aucun client enregistré dans le système.</p>
                <a href="${pageContext.request.contextPath}/clients/create" class="btn btn-primary">
                    <i class="fas fa-user-plus me-2"></i>
                    Créer le premier client
                </a>
            </div>
        </c:if>
    </div>
</div>

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
