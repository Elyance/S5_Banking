<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Détails du client</h2>
<c:if test="${not empty client}">
    <ul>
        <li><b>ID :</b> ${client.id}</li>
        <li><b>Numéro Client :</b> ${client.numeroClient}</li>
        <li><b>Nom :</b> ${client.nom}</li>
        <li><b>Prénom :</b> ${client.prenom}</li>
        <li><b>Date de Naissance :</b> ${client.dateNaissance}</li>
        <li><b>Email :</b> ${client.email}</li>
        <li><b>Téléphone :</b> ${client.telephone}</li>
        <li><b>Adresse :</b> ${client.adresse}</li>
        <li><b>Profession :</b> ${client.profession}</li>
        <li><b>Date Création :</b> ${client.dateCreation}</li>
        <li><b>Statut :</b> ${client.statutLibelle}</li>
        <li><b>Date Dernier Statut :</b> ${client.dateDernierStatut}</li>
    </ul>
    <a href="../clients">Retour à la liste</a>
</c:if>
