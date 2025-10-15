<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Modifier le client</h2>
<c:if test="${not empty client}">
<form action="edit" method="post">
    <input type="hidden" name="id" value="${client.id}" />
    <div>
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" value="${client.nom}" required />
    </div>
    <div>
        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenom" value="${client.prenom}" required />
    </div>
    <div>
        <label for="dateNaissance">Date de naissance :</label>
        <input type="date" id="dateNaissance" name="dateNaissance" value="${client.dateNaissance}" required />
    </div>
    <div>
        <label for="email">Email :</label>
        <input type="email" id="email" name="email" value="${client.email}" required />
    </div>
    <div>
        <label for="telephone">Téléphone :</label>
        <input type="text" id="telephone" name="telephone" value="${client.telephone}" required />
    </div>
    <div>
        <label for="adresse">Adresse :</label>
        <input type="text" id="adresse" name="adresse" value="${client.adresse}" required />
    </div>
    <div>
        <label for="profession">Profession :</label>
        <input type="text" id="profession" name="profession" value="${client.profession}" required />
    </div>
    <div>
        <button type="submit">Enregistrer</button>
    </div>
</form>
<a href="../clients">Annuler</a>
</c:if>
