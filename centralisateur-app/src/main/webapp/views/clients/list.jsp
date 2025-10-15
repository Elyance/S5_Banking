<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Liste des Clients avec Statut</h2>
<table border="1" cellpadding="5" cellspacing="0">
  <thead>
    <tr>
      <th>ID</th>
      <th>Numéro Client</th>
      <th>Nom</th>
      <th>Prénom</th>
      <th>Date de Naissance</th>
      <th>Email</th>
      <th>Téléphone</th>
      <th>Adresse</th>
      <th>Profession</th>
      <th>Date Création</th>
      <th>Statut</th>
      <th>Date Dernier Statut</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="client" items="${clients}">
      <tr>
        <td>${client.id}</td>
        <td>${client.numeroClient}</td>
        <td>${client.nom}</td>
        <td>${client.prenom}</td>
        <td>${client.dateNaissance}</td>
        <td>${client.email}</td>
        <td>${client.telephone}</td>
        <td>${client.adresse}</td>
        <td>${client.profession}</td>
        <td>${client.dateCreation}</td>
        <td>${client.statutLibelle}</td>
        <td>${client.dateDernierStatut}</td>
        <td>
          <a href="clients/details?id=${client.id}">Détails</a>
          <a href="clients/edit?id=${client.id}">Modifier</a>
          <form action="clients/delete" method="post" style="display:inline;">
            <input type="hidden" name="id" value="${client.id}" />
            <button type="submit" onclick="return confirm('Voulez-vous vraiment supprimer ce client ?');">Supprimer</button>
          </form>
        </td>
      </tr>
  </c:forEach>
  </tbody>
</table>
