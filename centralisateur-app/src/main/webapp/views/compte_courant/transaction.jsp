<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Nouvelle Transaction</h2>
<form action="${pageContext.request.contextPath}/compte-courant/transaction" method="post" class="form">
    <input type="hidden" name="compteId" value="${param.compteId}" />
    <div class="mb-3">
        <label for="type" class="form-label">Type de transaction</label>
        <select name="typeOperationId" id="type" class="form-select" required>
            <option value="">-- Choisir --</option>
            <c:forEach var="entry" items="${typeOperations}">
                <option value="${entry.key}">${entry.value}</option>
            </c:forEach>
        </select>
    </div>
    <div class="mb-3">
        <label for="montant" class="form-label">Montant</label>
        <input type="number" step="0.01" min="0.01" name="montant" id="montant" class="form-control" required />
    </div>
    <div class="mb-3">
        <label for="description" class="form-label">Description</label>
        <textarea name="description" id="description" class="form-control" rows="2"></textarea>
    </div>
    <button type="submit" class="btn btn-success">Valider</button>
    <a href="${pageContext.request.contextPath}/compte-courant/list" class="btn btn-secondary">Annuler</a>
</form>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger mt-3">${errorMessage}</div>
</c:if>
<c:if test="${not empty successMessage}">
    <div class="alert alert-success mt-3">${successMessage}</div>
</c:if>
