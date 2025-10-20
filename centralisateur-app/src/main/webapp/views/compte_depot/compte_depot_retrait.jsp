<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container mt-4">
    <h2>Retrait sur un compte dépôt</h2>
    <form method="post" action="${pageContext.request.contextPath}/compte-depot/doRetrait">
        <div class="mb-3">
            <input hidden type="number" class="form-control" id="compteDepotId" name="compteDepotId" value="${param.compteId}" required>
        </div>
        <div class="mb-3">
            <label for="montant" class="form-label">Montant à retirer</label>
            <input type="number" step="0.01" class="form-control" id="montant" name="montant" required>
        </div>
        <div class="mb-3">
            <label for="dateRetrait" class="form-label">Date du retrait</label>
            <input type="datetime-local" class="form-control" id="dateRetrait" name="dateRetrait" required>
        </div>
        <button type="submit" class="btn btn-warning">Retirer</button>
    </form>
</div>