<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container mt-4">
    <h2>Erreur lors de l'ajout du taux</h2>
    <div class="alert alert-danger mt-3">
        Une erreur est survenue: ${error}
    </div>
    <a href="${pageContext.request.contextPath}/compte-depot/taux" class="btn btn-secondary mt-3">Retour</a>
</div>
