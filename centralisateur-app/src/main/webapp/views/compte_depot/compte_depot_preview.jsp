<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Prévisualisation du compte dépôt</h2>

<div class="card">
    <div class="card-header">
        <h5>Détails du compte à créer</h5>
    </div>
    <div class="card-body">
        <p><strong>Client :</strong> ${client.nom} ${client.prenom} (ID: ${client.id})</p>
        <p><strong>Type de compte :</strong> Compte Dépôt</p>
        <p><strong>Date de création :</strong> ${dateCreation}</p>
        <p><strong>Solde initial :</strong> <fmt:formatNumber value="${solde}" type="number" minFractionDigits="2" maxFractionDigits="2" /> €</p>
        <p><strong>Taux d'intérêt :</strong> ${tauxInteret != null ? tauxInteret : "À définir ultérieurement"}</p>
    </div>
    <div class="card-footer">
        <form action="${pageContext.request.contextPath}/compte-depot/confirm" method="post" style="display: inline;">
            <input type="hidden" name="clientId" value="${client.id}" />
            <input type="hidden" name="solde" value="${solde}" />
            <input type="hidden" name="dateCreation" value="${dateCreation}" />
            <button type="submit" class="btn btn-primary">Confirmer la création</button>
        </form>
        <a href="${pageContext.request.contextPath}/clients/situation?id=${client.id}" class="btn btn-secondary">Annuler</a>
    </div>
</div>