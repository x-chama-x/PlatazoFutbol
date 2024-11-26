<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<c:import url="/WEB-INF/jsp/includes/head.jsp" />
<body>
<c:import url="/WEB-INF/jsp/includes/header.jsp" />
    <div class="container">
        <nav class="nav-desktop menu-index">
            <a href="${pageContext.request.contextPath}/" class="nav-button active">Inicio</a>
            <a href="subpaginaDeLiga.html" class="nav-button">Liga Plato</a>
            <div class="nav-button">Mundial Plato</div>
            <div class="nav-button">Amistosos</div>
        </nav>
        <nav class="nav-mobile menu-index" id="nav-mobile">
            <a href="${pageContext.request.contextPath}/" class="nav-button active">Inicio</a>
            <a href="subpaginaDeLiga.html" class="nav-button">Liga Plato</a>
            <div class="nav-button">Mundial Plato</div>
            <div class="nav-button">Amistosos</div>
        </nav>
        <main>
            <div class="top-buttons">
                <div>
                    <a href="${pageContext.request.contextPath}/" class="button">PARTIDOS JUGADOS</a>
                    <a href="prox" class="button">PROXIMOS PARTIDOS</a>
                </div>
            </div>
            <div class="match-panel">
                <c:set var="currentEvent" value="" />
                <c:forEach var="partido" items="${partidos}">
                    <c:if test="${currentEvent != partido.tipoEvento}">
                        <c:set var="currentEvent" value="${partido.tipoEvento}" />
                        <c:choose>
                            <c:when test="${partido.tipoEvento == 'liga'}">
                                <h2>LIGA PLATO</h2>
                            </c:when>
                            <c:when test="${partido.tipoEvento == 'mundial'}">
                                <h2>MUNDIAL</h2>
                            </c:when>
                            <c:otherwise>
                                <h2>OTRO EVENTO</h2>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
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