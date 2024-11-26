<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Promiedos Clone</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
    <header>
        <div class="logo">PROMIEDOS PLATO<span class="logo-com">.com.ar</span></div>
        <button class="menu-button" onclick="toggleMenu()">☰</button>
    </header>
    <div class="container">
        <nav class="nav-desktop menu-index">
            <a href="index.jsp" class="nav-button active">Inicio</a>
            <a href="subpaginaDeLiga.html" class="nav-button">Liga Plato</a>
            <div class="nav-button">Mundial Plato</div>
            <div class="nav-button">Amistosos</div>
        </nav>
        <nav class="nav-mobile menu-index" id="nav-mobile">
            <a href="index.jsp" class="nav-button active">Inicio</a>
            <a href="subpaginaDeLiga.html" class="nav-button">Liga Plato</a>
            <div class="nav-button">Mundial Plato</div>
            <div class="nav-button">Amistosos</div>
        </nav>
        <main>
            <div class="top-buttons">
                <div>
                    <a href="index.jsp" class="button">PARTIDOS JUGADOS</a>
                    <a href="proximosPartidos.html" class="button">PROXIMOS PARTIDOS</a>
                </div>
            </div>
            <div class="match-panel">
                <h2>LIGA PLATO</h2>
                <c:forEach var="partido" items="${partidos}">
                    <div class="match-card">
                        <div style="display: flex; align-items: stretch; gap: 10px;">
                            <div style="background-color: #333; padding: 5px 10px; width: 60px; text-align: center; display: flex; flex-direction: column; justify-content: center;">
                                <div>${partido.estado}</div>
                            </div>
                            <div style="flex-grow: 1;">
                                <div class="match-teams">
                                    <span>${equipoNombres[partido.equipoLocalId]}</span>
                                    <span class="match-score">${partido.resultado}</span>
                                    <span>${equipoNombres[partido.equipoVisitanteId]}</span>
                                </div>
                                <div class="match-details">
                                    <div class="match-scorers">
                                        <c:forEach var="gol" items="${golesPorPartido[partido.partidoId]}">
                                            <c:if test="${gol.golesLocal > 0}">
                                                <div>${gol.golesLocalFormatted}</div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="match-status"></div>
                                    <div class="match-scorers" style="text-align: right;">
                                        <c:forEach var="gol" items="${golesPorPartido[partido.partidoId]}">
                                            <c:if test="${gol.golesVisitante > 0}">
                                                <div>${gol.golesVisitanteFormatted}</div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <div style="padding: 5px; display: flex; align-items: center;">
                                <button style="background: none; border: none; color: #B4D335; cursor: pointer; font-size: 20px;">+</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
    </div>
    <footer>
        <!-- Contenido del footer -->
    </footer>
</body>
</html>