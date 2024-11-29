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
            <div class="navigation-blocks">
                <a href="liga" class="nav-block">Fixture y <br>Tablas</br></a>
                <a href="usuarios" class="nav-block">Usuarios<br>compitiendo</br></a>
                <a href="infoLiga.html" class="nav-block">Mas Datos <br>Torneo</br></a>
                <a href="#" class="nav-block">Campeones <br>Liga</br></a>
            </div>
            <div class="left-align-content">
                <div class="content-wrapper">
                    <div class="usuarios-liga-table">
                    <h2 style="color: #ffffff; background-color: #333333; padding: 10px; margin: 0 0 10px 0; text-align: center;">Usuarios (Estadísticas extraidas de PLATO)</h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>Usuario</th>
                                    <th>Nivel</th>
                                    <th>PJ</th>
                                    <th>PG</th>
                                    <th>PP</th>
                                    <th>DIF</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="usuario" items="${usuarios}">
                                    <tr>
                                        <td>${usuario.nombre}</td>
                                        <td>${usuario.nivel}</td>
                                        <td>${usuario.partidosJugados}</td>
                                        <td>${usuario.victorias}</td>
                                        <td>${usuario.partidosJugados - usuario.victorias}</td>
                                        <td>${usuario.diferencia}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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