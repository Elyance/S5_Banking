<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Creer un compte depot</h2>
<form action="${pageContext.request.contextPath}/compte-depot/create" method="post" class="form">
    <div class="mb-3">
        <label for="clientId" class="form-label">Client</label>
        <select name="clientId" id="clientId" class="form-control" required>
            <option value="">-- Sélectionner un client --</option>
            <c:forEach var="client" items="${clients}">
                <option value="${client.id}">${client.nom} ${client.prenom} (ID: ${client.id})</option>
            </c:forEach>
        </select>
    </div>
    <button type="submit" class="btn btn-success">Créer</button>
    <a href="${pageContext.request.contextPath}/compte-depot/list" class="btn btn-secondary">Annuler</a>
</form>
