<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container mt-4">
    <div class="alert alert-danger" role="alert">
        <h4 class="alert-heading">Erreur !</h4>
        <p>Une erreur est survenue lors de la création du compte de dépôt.</p>
        <hr>
        <p class="mb-0">
            <a href="${pageContext.request.contextPath}/compte-depot/create" class="btn btn-primary">
                Réessayer
            </a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                Retour à l'accueil
            </a>
        </p>
    </div>
</div>