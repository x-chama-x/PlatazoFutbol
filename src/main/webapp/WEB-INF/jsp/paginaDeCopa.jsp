<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<c:import url="/WEB-INF/jsp/includes/head.jsp" />
<body>
<c:import url="/WEB-INF/jsp/includes/header.jsp" />
    <div class="container">
        <c:import url="/WEB-INF/jsp/includes/nav.jsp" />
        <main>
            <c:forEach var="entry" items="${partidosPorFase}" varStatus="status">
                <div class="match-panel">
                    <c:choose>
                        <c:when test="${entry.key == 'final'}">
                            <h2>FINAL</h2>
                        </c:when>
                        <c:when test="${entry.key == 'semifinal'}">
                            <h2>SEMIFINALES</h2>
                        </c:when>
                        <c:when test="${entry.key == 'cuartos'}">
                            <h2>CUARTOS DE FINAL</h2>
                        </c:when>
                        <c:when test="${entry.key == 'octavos'}">
                            <h2>OCTAVOS DE FINAL</h2>
                        </c:when>
                        <c:otherwise>
                            <h2>FASE DE GRUPOS</h2>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="partido" items="${entry.value}">
<div class="match-card">
    <div style="display: flex; align-items: stretch; gap: 10px;">
        <div style="background-color: #333; padding: 5px 10px; width: 60px; text-align: center; display: flex; flex-direction: column; justify-content: center;">
            <c:choose>
                <c:when test="${partido.estado == 'finalizado'}">
                    <div>Final</div>
                </c:when>
                <c:otherwise>
                    <div>${partido.fecha}</div>
                </c:otherwise>
            </c:choose>
        </div>
        <div style="flex-grow: 1;">
            <div class="match-teams">
                <span>${equipoNombres[partido.equipoLocalId]}</span>
                <span class="match-score">
                    <c:choose>
                        <c:when test="${empty partido.resultado}">
                            vs
                        </c:when>
                        <c:otherwise>
                            ${partido.resultado}
                        </c:otherwise>
                    </c:choose>
                </span>
                <span>${equipoNombres[partido.equipoVisitanteId]}</span>
            </div>
            <div class="match-details">
                <c:choose>
                    <c:when test="${partido.estado == 'suspendido' && not empty partido.nota}">
                        <div><b style="color: black;">${partido.nota}</b></div>
                    </c:when>
                    <c:otherwise>
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
                    </c:otherwise>
                </c:choose>
            </div>
            <c:if test="${probabilidades[partido.partidoId] != null && partido.estado != 'finalizado'}">
                <div class="probability-bar">
                    <c:set var="probabilidad" value="${probabilidades[partido.partidoId]}" />
                    <div class="probability-segment home-win" style="width: ${probabilidad.local}%;">${probabilidad.local}%</div>
                    <div class="probability-segment draw" style="width: ${probabilidad.empate}%;">${probabilidad.empate}%</div>
                    <div class="probability-segment away-win" style="width: ${probabilidad.visitante}%;">${probabilidad.visitante}%</div>
                </div>
            </c:if>
        </div>
        <div style="padding: 5px; display: flex; align-items: center;">
            <button style="background: none; border: none; color: #000000; cursor: pointer; font-size: 20px;">+</button>
        </div>
    </div>
</div>
                    </c:forEach>
                </div>
                <c:if test="${!status.last}">
                    <br>
                </c:if>
            </c:forEach>
        </main>
    </div>
    <footer>
        <!-- Contenido del footer -->
    </footer>
</body>
</html>