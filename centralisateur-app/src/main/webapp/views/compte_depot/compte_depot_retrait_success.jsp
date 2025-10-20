<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container mt-4">
    <h2>Retrait effectué avec succès !</h2>
    <div class="alert alert-success mt-3">
        Le retrait a bien été enregistré sur le compte.
    </div>
    <a href="${pageContext.request.contextPath}/compte-depot/retrait" class="btn btn-warning mt-3">Faire un autre retrait</a>
</div>