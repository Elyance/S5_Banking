<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container mt-4">
    <div class="alert alert-success" role="alert">
        <h4 class="alert-heading">Succès !</h4>
        <p>Le compte de dépôt a été créé avec succès.</p>
        <hr>
        <p class="mb-0">
            <a href="${pageContext.request.contextPath}/compte-depot/create" class="btn btn-primary">
                Créer un autre compte
            </a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                Retour à l'accueil
            </a>
        </p>
    </div>
</div>