<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Effectuer un prêt</h2>
<form action="${pageContext.request.contextPath}/compte-pret/preter" method="post" class="form">
        <input type="hidden" name="comptePretId" value="${param.compteId}" />
    <div class="mb-3">
        <label for="montant" class="form-label">Montant du prêt</label>
        <input type="number" step="0.01" min="0" name="montant" id="montant" class="form-control" required />
    </div>
    <div class="mb-3">
        <label for="typePaiementId" class="form-label">Type de paiement</label>
        <select name="typePaiementId" id="typePaiementId" class="form-control" required>
            <option value="">-- Sélectionner un type --</option>
            <c:forEach var="type" items="${typesPaiement}">
                <option value="${type.key}">${type.value}</option>
            </c:forEach>
        </select>
    </div>
    <div class="mb-3">
        <label for="duree" class="form-label">Durée (en mois)</label>
        <input type="number" min="1" name="duree" id="duree" class="form-control" required />
    </div>
    <div class="mb-3">
        <label for="datePret" class="form-label">Date du prêt</label>
        <input type="datetime-local" name="datePret" id="datePret" class="form-control" required />
    </div>
    <button type="submit" class="btn btn-success">Valider le prêt</button>
    <a href="${pageContext.request.contextPath}/compte-pret/liste" class="btn btn-secondary">Annuler</a>
</form>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger mt-3">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div class="alert alert-success mt-3">${successMessage}</div>
</c:if>
