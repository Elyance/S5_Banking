<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Créer un compte courant</h2>
<form action="create" method="post">
    <div>
        <label for="clientId">Client :</label>
        <select id="clientId" name="clientId" required>
            <c:forEach var="client" items="${clients}">
                <option value="${client.id}">${client.nom} ${client.prenom} (ID: ${client.id})</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="decouvertAutorise">Découvert autorisé :</label>
        <input type="number" step="0.01" id="decouvertAutorise" name="decouvertAutorise" required />
    </div>
    <div>
        <button type="submit">Créer</button>
    </div>
</form>
