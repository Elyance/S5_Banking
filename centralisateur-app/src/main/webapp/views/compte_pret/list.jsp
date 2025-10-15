<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Liste des Comptes Prêt</h2>
<table class="table table-striped">
    <thead>
        <tr>
            <th>#</th>
            <th>Numéro de compte</th>
            <th>Client</th>
            <th>Solde restant dû</th>
            <th>Date création</th>
            <th>Statut</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${comptes}">
            <tr>
                <td>${item.comptePret.id}</td>
                <td>${item.comptePret.numeroCompte}</td>
                <td>
                    ${item.client.nom} ${item.client.prenom}<br/>
                    <small>${item.client.email}</small>
                </td>
                <td>${item.comptePret.soldeRestantDu}</td>
                <td>${item.comptePret.dateCreation}</td>
                <td>${item.comptePret.statutLibelle}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/compte-pret/preter?compteId=${item.comptePret.id}" class="btn btn-primary btn-sm">Preter de l'argent</a>
                    <a href="${pageContext.request.contextPath}/compte-pret/rembourser?compteId=${item.comptePret.id}" class="btn btn-primary btn-sm">Rembourser de l'argent</a> 
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
