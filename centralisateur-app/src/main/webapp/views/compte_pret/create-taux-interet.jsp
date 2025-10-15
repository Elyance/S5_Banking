<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Créer un nouveau taux d'intérêt</h2>
<form action="${pageContext.request.contextPath}/compte-pret/taux-interet" method="post" class="form">
    <div class="mb-3">
        <label for="valeur" class="form-label">Valeur du taux (%)</label>
        <input type="number" step="0.01" min="0" max="100" name="valeur" id="valeur" class="form-control" required />
    </div>
    <div class="mb-3">
        <label for="dateDebut" class="form-label">Date de début</label>
        <input type="datetime-local" name="dateDebut" id="dateDebut" class="form-control" required />
    </div>
    <button type="submit" class="btn btn-success">Créer</button>
    <a href="${pageContext.request.contextPath}/compte-pret/taux-interet/list" class="btn btn-secondary">Annuler</a>
</form>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger mt-3">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div class="alert alert-success mt-3">${successMessage}</div>
</c:if>
