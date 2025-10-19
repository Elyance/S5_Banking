<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Liste des Comptes Courants</h2>
<table class="table table-striped">
    <thead>
        <tr>
            <th>#</th>
            <th>Numéro de compte</th>
            <th>Client</th>
            <th>Solde</th>
            <th>Découvert autorisé</th>
            <th>Date création</th>
            <th>Statut</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${comptes}">
            <tr>
                <td>${item.compte.id}</td>
                <td>${item.compte.numeroCompte}</td>
                <td>
                    ${item.client.nom} ${item.client.prenom}<br/>
                    <small>${item.client.email}</small>
                </td>
                <td>${item.compte.solde}</td>
                <td>${item.compte.decouvertAutorise}</td>
                <td>${item.compte.dateCreation}</td>
                <td>${item.compte.statutLibelle}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/compte-courant/transaction?compteId=${item.compte.id}" class="btn btn-primary btn-sm">Faire une transaction</a>
                    <a href="${pageContext.request.contextPath}/compte-courant/transactions/list?compteId=${item.compte.id}" class="btn btn-outline-secondary btn-sm ms-1">Voir transactions</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
