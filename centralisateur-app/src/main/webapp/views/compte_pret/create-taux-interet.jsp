<%@ page contentType="text/html;charsedocument.querySelectorAll('input[name="typeCompte"]').forEach(radio => {
    radio.addEventListener('change', function() {
        const form = document.getElementById('tauxForm');
        if (this.value === 'pret') {
            form.action = '${pageContext.request.contextPath}/compte-pret/taux-interet';
        } else if (this.value === 'depot') {
            form.action = '${pageContext.request.contextPath}/compte-depot/taux-interet';
        }
    });
});anguage="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Créer un nouveau taux d'intérêt</h2>
<form id="tauxForm" action="${pageContext.request.contextPath}/compte-pret/taux-interet" method="post" class="form">
    <div class="mb-3">
        <label class="form-label">Type de compte</label>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="typeCompte" id="pret" value="pret" checked>
            <label class="form-check-label" for="pret">
                Prêt
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="typeCompte" id="depot" value="depot">
            <label class="form-check-label" for="depot">
                Dépôt
            </label>
        </div>
    </div>
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

<script>
document.querySelectorAll('input[name="typeCompte"]').forEach(radio => {
    radio.addEventListener('change', function() {
        const form = document.getElementById('tauxForm');
        if (this.value === 'pret') {
            form.action = '${pageContext.request.contextPath}/compte-pret/taux-interet';
        } else if (this.value === 'depot') {
            form.action = '${pageContext.request.contextPath}/compte-depot/taux-interet'; // Adjust port if needed
        }
    });
});
</script>
