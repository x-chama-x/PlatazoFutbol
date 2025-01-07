# Liga de Plato Fútbol - Aplicación Web

## Descripción
Esta aplicación web está diseñada para gestionar y mostrar información sobre la Liga de Plato Fútbol (LPF). Permite a los usuarios registrar los resultados de los partidos, consultar la tabla de posiciones, estadísticas de los equipos y jugadores, y acceder a información sobre la liga y el juego Table Soccer.

## Funcionalidades principales
- **Registro de resultados de partidos**: Los usuarios pueden ver los resultados de los partidos jugados, incluyendo los goles de cada equipo.
- **Tabla de posiciones**: La aplicación genera automáticamente la tabla de posiciones de la liga, basada en los resultados de los partidos registrados. Se calcula la posición de los equipos en base a la puntuación, utilizando criterios de desempate como la diferencia de goles.
- **Información de la liga**: La aplicación provee información general sobre la liga, como el formato de competición, el sistema de puntuación, las fechas de las jornadas y las reglas del juego.
- **Información sobre Table Soccer**: La aplicación ofrece una descripción del juego Table Soccer en la plataforma Plato, incluyendo su objetivo, la jugabilidad y las características sociales.

## Base de datos
La aplicación utiliza una base de datos para almacenar la información de la liga. El diseño de la base de datos incluye las siguientes entidades:
- **Usuario**: Almacena información sobre los equipos/usuarios participantes.
- **Partido**: Registra cada partido jugado, incluyendo la fecha, los equipos participantes, el resultado y otros detalles.
- **Goles por tiempo**: Almacena los goles anotados por cada equipo en cada tiempo de cada partido.
- **Clasificación**: Almacena la clasificación actualizada de los equipos, incluyendo puntos, partidos jugados, victorias, empates, derrotas, goles a favor, goles en contra y diferencia de goles.

## Tecnologías utilizadas
- **Lenguaje de programación**: Java
- **Framework web**: Jakarta EE (antes Java EE)
- **Servidor web**: Apache Tomcat
- **Gestor de dependencias**: Maven
- **Lenguaje de etiquetas**: JSP (Jakarta Server Pages)
- **Librería de etiquetas**: JSTL (Jakarta Standard Tag Library)


