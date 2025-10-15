<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Créer un nouveau compte prêt</h2>
<form action="${pageContext.request.contextPath}/compte-pret/create" method="post" class="form">
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
    <a href="${pageContext.request.contextPath}/compte-pret/list" class="btn btn-secondary">Annuler</a>
</form>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger mt-3">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div class="alert alert-success mt-3">${successMessage}</div>
</c:if>
