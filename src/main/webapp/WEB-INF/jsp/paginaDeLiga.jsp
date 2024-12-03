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
            <c:import url="/WEB-INF/jsp/includes/navBlocks.jsp" />
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
                        <button class="arrow-button" onclick="navigateJornada(-1)">&larr;</button>
                        <div class="fixture-title">FECHA ${jornadaSeleccionada}</div>
                        <button class="arrow-button" onclick="navigateJornada(1)">&rarr;</button>
                    </div>
                    <div class="match-panel">
                        <c:choose>
                            <c:when test="${partidos[0].tipoEvento == 'liga'}">
                                <h2>&#127942 LIGA PLATO &#127942</h2>
                            </c:when>
                            <c:when test="${partidos[0].tipoEvento == 'mundial'}">
                                <h2>&#127757 MUNDIAL &#127757</h2>
                            </c:when>
                            <c:otherwise>
                                <h2>OTRO EVENTO</h2>
                            </c:otherwise>
                        </c:choose>
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
                                                <div>${partido.fecha}</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div style="flex-grow: 1;">
                                    <div class="match-teams">
                                        <span>${usuarioNombres[partido.equipoLocalId]}</span>
                                        <span class="match-score">
                                            <c:choose>
                                                <c:when test="${empty partido.estado || empty partido.resultado}">
                                                    vs
                                                </c:when>
                                                <c:otherwise>
                                                    ${partido.resultado}
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
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
                                        <c:if test="${probabilidades[partido.partidoId] != null}">
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
                </div>
            </div>
        </main>
    </div>
    <footer>
        <!-- Contenido del footer -->
    </footer>
    <script> <!-- lo dejo acá porque en el script.js no me lo toma -->
        function navigateJornada(direction) {
            const currentJornada = ${jornadaSeleccionada};
            const maxJornada = ${maxJornada};
            let newJornada = currentJornada + direction;
            if (newJornada < 1) newJornada = 1;
            if (newJornada > maxJornada) newJornada = maxJornada;
            window.location.href = '?jornada=' + newJornada;
        }
    </script>
</body>
</html>