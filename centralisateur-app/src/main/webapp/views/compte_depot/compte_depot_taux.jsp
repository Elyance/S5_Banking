<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container mt-4">
    <h2>Insertion d'un nouveau taux d'intérêt</h2>
    <form method="post" action="${pageContext.request.contextPath}/compte-depot/taux">
        <div class="mb-3">
            <label for="valeur" class="form-label">Valeur (en pourcentage, ex: 3 pour 3%)</label>
            <input type="number" step="0.01" min="0" class="form-control" id="valeur" name="valeur" required>
        </div>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
    </form>
</div>
