<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Modifier le Train</title>
</head>
<body>
<h1>Modifier le Train</h1>
<form action="update-train" method="post">
    <input type="hidden" name="id" value="${train.id}" />
    <div class="form-group">
        <label for="nom">Nom du Train:</label>
        <input type="text" id="nom" name="nom" value="${train.nom}" required />
    </div>
    <div class="form-group">
        <label for="villeDepart">Ville de Départ:</label>
        <input type="text" id="villeDepart" name="villeDepart" value="${train.villeDepart}" required />
    </div>
    <div class="form-group">
        <label for="villeArrivee">Ville d'Arrivée:</label>
        <input type="text" id="villeArrivee" name="villeArrivee" value="${train.villeArrivee}" required />
    </div>
    <div class="form-group">
        <label for="heureDepart">Heure de Départ:</label>
        <input type="text" id="heureDepart" name="heureDepart" value="${train.heureDepart}" required />
    </div>
    <button type="submit">Enregistrer</button>
    <a href="train-list" class="cancel">Annuler</a>
</form>
</body>
</html>
