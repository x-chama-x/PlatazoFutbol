// función que se encarga de mostrar y ocultar el menú en dispositivos móviles
function toggleMenu() {
    var menu = document.getElementById('nav-mobile');
    if (menu.style.display === 'block') {
        menu.style.display = 'none';
    } else {
        menu.style.display = 'block';
    }
}