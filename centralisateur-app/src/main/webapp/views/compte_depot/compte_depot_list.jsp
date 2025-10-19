<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Liste des Comptes Dépôt</h2>
<table class="table table-striped">
    <thead>
        <tr>
            <th>#</th>
            <th>Numéro de compte</th>
            <th>Client</th>
            <th>Solde</th>
            <th>Date création</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${comptesAvecClients}">
            <tr>
                <td>${item.id}</td>
                <td>${item.numeroCompte}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty item.client}">
                            ${item.client.nom} ${item.client.prenom}<br/>
                            <small>${item.client.email}</small>
                        </c:when>
                        <c:otherwise>
                            <small>N/A</small>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${item.solde}</td>
                <td>${item.dateCreation}</td>
                <td>
                    <!-- Actions possibles: afficher détail, déposer -->
                    <a href="${pageContext.request.contextPath}/compte-depot/depot?compteDepotId=${item.id}" class="btn btn-primary btn-sm">Déposer</a>
                    <a href="${pageContext.request.contextPath}/compte-depot/retrait?compteDepotId=${item.id}" class="btn btn-primary btn-sm">Retirer</a>
                    
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
