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
            <div class="left-align-content">
                <div class="content-wrapper">
                    <div class="usuarios-liga-table">
                        <h2 style="color: #ffffff; background-color: #333333; padding: 10px; margin: 0 0 10px 0; text-align: center;">Usuarios Activos</h2>
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
                                <c:forEach var="usuario" items="${usuariosActivos}">
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
                    <div class="usuarios-liga-table">
                        <h2 style="color: #ffffff; background-color: #333333; padding: 10px; margin: 0 0 10px 0; text-align: center;">Usuarios No Activos</h2>
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
                                <c:forEach var="usuario" items="${usuariosNoActivos}">
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