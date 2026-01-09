<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Liste des Trains</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
  </style>
</head>
<body>
<h1>Gestion des Trains</h1>

<p>
    <a href="api/trains" target="_blank">Voir l'API JSON</a> |
    <a href="reserve-train">Réserver un train</a> |
    <a href="recherche-train">Rechercher un train</a>
</p>

<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Nom</th>
    <th>Ville Départ</th>
    <th>Ville Arrivée</th>
    <th>Heure Départ</th>
      <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="train" items="${trains}">
    <tr>
      <td>${train.id}</td>
      <td>${train.nom}</td>
      <td>${train.villeDepart}</td>
      <td>${train.villeArrivee}</td>
      <td>${train.heureDepart}</td>
        <td>
            <a href="update-train?id=${train.id}">Modifier</a> |
            <a href="delete-train?id=${train.id}" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce train ?');">Supprimer</a>
        </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
