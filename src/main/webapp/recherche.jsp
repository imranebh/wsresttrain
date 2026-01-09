<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Rechercher un Train</title>
</head>
<body>
    <h2>Rechercher un train</h2>
    <form action="recherche-train" method="get">
        <div>
            <label for="villeDepart">Ville de Départ:</label>
            <input type="text" id="villeDepart" name="villeDepart" required>
        </div>
        <div>
            <label for="villeArrivee">Ville d'Arrivée:</label>
            <input type="text" id="villeArrivee" name="villeArrivee" required>
        </div>
        <button type="submit">Rechercher</button>
        <a href="train-list">Retour à la liste</a>
    </form>

    <c:if test="${searched}">
        <h3>Résultats de la recherche</h3>
        <c:choose>
            <c:when test="${not empty trains}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nom</th>
                            <th>Ville Départ</th>
                            <th>Ville Arrivée</th>
                            <th>Heure Départ</th>
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
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>Aucun train trouvé pour cet itinéraire.</p>
            </c:otherwise>
        </c:choose>
    </c:if>
</body>
</html>
