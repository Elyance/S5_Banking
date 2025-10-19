<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Prévisualisation du compte courant</h2>

<div class="card">
    <div class="card-header">
        <h5>Détails du compte à créer</h5>
    </div>
    <div class="card-body">
        <p><strong>Client :</strong> ${client.nom} ${client.prenom} (ID: ${client.id})</p>
        <p><strong>Type de compte :</strong> Compte Courant</p>
        <p><strong>Date de création :</strong> ${dateCreation}</p>
        <p><strong>Découvert autorisé :</strong> <fmt:formatNumber value="${decouvertAutorise}" type="number" minFractionDigits="2" maxFractionDigits="2" /> Ariary</p>
        <p><strong>Taux d'intérêt :</strong> 0% (pas d'intérêt sur compte courant)</p>
        <p><strong>Solde initial :</strong> 0,00 Ariary</p>
    </div>
    <div class="card-footer">
        <form action="${pageContext.request.contextPath}/compte-courant/confirm" method="post" style="display: inline;">
            <input type="hidden" name="clientId" value="${client.id}" />
            <input type="hidden" name="dateCreation" value="${dateCreation}" />
            <button type="submit" class="btn btn-primary">Confirmer la création</button>
        </form>
        <a href="${pageContext.request.contextPath}/comptes/select-type?clientId=${client.id}" class="btn btn-secondary">Annuler</a>
    </div>
</div>