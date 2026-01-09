<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Réserver un Train</title>

</head>
<body>
    <h2>Réserver une place</h2>
    <form action="reserve-train" method="post">
        <div class="form-group">
            <label for="trainId">Choisir un Train:</label>
            <select id="trainId" name="trainId" required>
                <option value="">-- Sélectionner un train --</option>
                <c:forEach var="train" items="${trains}">
                    <option value="${train.id}">
                        ${train.nom} (${train.villeDepart} -> ${train.villeArrivee} à ${train.heureDepart})
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="passengerName">Nom du Voyageur:</label>
            <input type="text" id="passengerName" name="passengerName" placeholder="Votre nom" required>
        </div>
        <button type="submit">Confirmer la Réservation</button>
        <a href="train-list" class="cancel">Annuler</a>
    </form>
</body>
</html>