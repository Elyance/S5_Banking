<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Créer un nouveau client</h2>
<c:if test="${not empty errorMessage}">
    <div style="color: red; font-weight: bold;">${errorMessage}</div>
</c:if>
<form action="create" method="post">
    <div>
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" required />
    </div>
    <div>
        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenom" required />
    </div>
    <div>
        <label for="dateNaissance">Date de naissance :</label>
        <input type="date" id="dateNaissance" name="dateNaissance" required />
    </div>
    <div>
        <label for="email">Email :</label>
        <input type="email" id="email" name="email" required />
    </div>
    <div>
        <label for="telephone">Téléphone :</label>
        <input type="text" id="telephone" name="telephone" required />
    </div>
    <div>
        <label for="adresse">Adresse :</label>
        <input type="text" id="adresse" name="adresse" required />
    </div>
    <div>
        <label for="profession">Profession :</label>
        <input type="text" id="profession" name="profession" required />
    </div>
    <div>
        <button type="submit">Créer</button>
    </div>
</form>
