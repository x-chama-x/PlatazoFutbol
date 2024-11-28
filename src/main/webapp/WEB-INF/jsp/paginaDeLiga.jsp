<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<c:import url="/WEB-INF/jsp/includes/head.jsp" />
<body>
<c:import url="/WEB-INF/jsp/includes/header.jsp" />
    <div class="container">
        <nav class="nav-desktop">
            <a href="${pageContext.request.contextPath}/" class="nav-button active">Inicio</a>
            <a href="subpaginaDeLiga.html" class="nav-button">Liga Plato</a>
            <div class="nav-button">Mundial Plato</div>
            <div class="nav-button">Amistosos</div>
        </nav>
        <nav class="nav-mobile" id="nav-mobile">
            <a href="${pageContext.request.contextPath}/" class="nav-button active">Inicio</a>
            <a href="subpaginaDeLiga.html" class="nav-button">Liga Plato</a>
            <div class="nav-button">Mundial Plato</div>
            <div class="nav-button">Amistosos</div>
        </nav>
        <main>
            <div class="content-wrapper">
                <div class="standings-table">
                    <h2 style="color: #ffffff; background-color: #333333; padding: 10px; margin: 0 0 10px 0; text-align: center;">Tabla de Posiciones</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Pos</th>
                                <th>Usuario</th>
                                <th>P</th>
                                <th>W</th>
                                <th>D</th>
                                <th>L</th>
                                <th>GF</th>
                                <th>GC</th>
                                <th>DIF</th>
                                <th>Pts</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="clasificacion" items="${clasificaciones}">
                                <tr>
                                    <td>${clasificacion.clasificacionId}</td>
                                    <td>${usuarioNombres[clasificacion.usuarioId]}</td>
                                    <td>${clasificacion.partidosJugados}</td>
                                    <td>${clasificacion.victorias}</td>
                                    <td>${clasificacion.empates}</td>
                                    <td>${clasificacion.derrotas}</td>
                                    <td>${clasificacion.golesFavor}</td>
                                    <td>${clasificacion.golesContra}</td>
                                    <td>${clasificacion.diferenciaGoles}</td>
                                    <td>${clasificacion.puntos}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="fixtures-panel">
                    <div class="fixture-navigation">
                        <div class="fixture-rounds">
                            <c:forEach var="i" begin="1" end="${maxJornada}">
                                <button class="round-button ${i == jornadaSeleccionada ? 'active' : ''}" onclick="location.href='?jornada=${i}'">${i}</button>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="fixture-header">
                        <button class="arrow-button">&larr;</button>
                        <div class="fixture-title">FECHA 14</div>
                        <button class="arrow-button">&rarr;</button>
                    </div>
                <div class="match-panel">
                    <c:forEach var="partido" items="${partidos}">
                        <div class="match-card">
                            <div style="display: flex; align-items: stretch; gap: 10px;">
                                <div style="background-color: #333; padding: 5px 10px; width: 60px; text-align: center; display: flex; flex-direction: column; justify-content: center;">
                                    <c:choose>
                                        <c:when test="${partido.estado == 'finalizado'}">
                                            <div>Final</div>
                                        </c:when>
                                        <c:when test="${partido.estado == 'suspendido'}">
                                            <div>Suspendido</div>
                                        </c:when>
                                        <c:when test="${partido.estado == 'en_progreso'}">
                                            <div>En progreso</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div>${partido.estado}</div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div style="flex-grow: 1;">
                                    <div class="match-teams">
                                        <span>${usuarioNombres[partido.equipoLocalId]}</span>
                                        <span class="match-score">${partido.resultado}</span>
                                        <span>${usuarioNombres[partido.equipoVisitanteId]}</span>
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
                                    <button style="background: none; border: none; color: #000000; cursor: pointer; font-size: 20px;">+</button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                </div>
            </div>
        </main>
    </div>
    <footer>
        <!-- Contenido del footer -->
    </footer>
</body>
</html>