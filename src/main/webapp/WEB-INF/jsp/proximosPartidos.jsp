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
            <div class="top-buttons">
                <div>
                    <a href="${pageContext.request.contextPath}/" class="button">PARTIDOS JUGADOS</a>
                    <a href="prox" class="button active">PROXIMOS PARTIDOS</a>
                </div>
            </div>
            <c:forEach var="entry" items="${proximosPartidosPorEvento}" varStatus="status">
                <div class="match-panel">
                    <c:choose>
                        <c:when test="${entry.key == 'liga'}">
                            <h2>&#127942 LIGA PLATO &#127942</h2>
                        </c:when>
                        <c:when test="${entry.key == 'mundial'}">
                            <h2>&#127757 MUNDIAL &#127757</h2>
                        </c:when>
                        <c:otherwise>
                            <h2>OTRO EVENTO</h2>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="partido" items="${entry.value}">
                        <div class="match-card">
                            <div style="display: flex; align-items: stretch; gap: 10px;">
                                <div style="background-color: #333; padding: 5px 10px; width: 60px; text-align: center; display: flex; flex-direction: column; justify-content: center;">
                                    <div>${partido.fecha}</div>
                                </div>
                                <div style="flex-grow: 1;">
                                    <div class="match-teams">
                                        <span>${equipoNombres[partido.equipoLocalId]}</span>
                                        <span class="match-score">vs</span>
                                        <span>${equipoNombres[partido.equipoVisitanteId]}</span>
                                    </div>
                                    <c:if test="${not empty partido.nota}">
                                        <div><b style="color: black; font-size: 13px;">${partido.nota}</b></div>
                                    </c:if>
                                    <div class="probability-bar">
                                        <c:set var="probabilidad" value="${probabilidades[partido.partidoId]}" />
                                        <div class="probability-segment home-win" style="width: ${probabilidad.local}%;">${probabilidad.local}%</div>
                                        <div class="probability-segment draw" style="width: ${probabilidad.empate}%;">${probabilidad.empate}%</div>
                                        <div class="probability-segment away-win" style="width: ${probabilidad.visitante}%;">${probabilidad.visitante}%</div>
                                    </div>
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