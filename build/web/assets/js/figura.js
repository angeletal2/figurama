

function cambiarImagen(url) {
    var imagenPrincipal = document.getElementById('main-image');

    imagenPrincipal.src = '../assets/images/figuras/' + url;
}

function zoom(event) {
    var zoomContainer = event.currentTarget;
    var image = zoomContainer.querySelector('img');

    // Obtener la posición del ratón dentro del contenedor de zoom
    var {left, top, width, height} = zoomContainer.getBoundingClientRect();
    var x = (event.clientX - left) / width;
    var y = (event.clientY - top) / height;

    // Aplicar el efecto de zoom a la imagen
    image.style.transformOrigin = `${x * 100}% ${y * 100}%`;
    image.style.transform = 'scale(2)';
}

function resetZoom(event) {
    var zoomContainer = event.currentTarget;
    var image = zoomContainer.querySelector('img');

    // Restablecer el tamaño original de la imagen
    image.style.transformOrigin = 'initial';
    image.style.transform = 'initial';
}

function compartirFigura(url) {

  // Crea un elemento de texto temporal
  var elementoTemp = document.createElement("textarea");
  elementoTemp.value = url;

  // Agrega el elemento al documento
  document.body.appendChild(elementoTemp);

  // Selecciona y copia el contenido del elemento de texto temporal
  elementoTemp.select();
  document.execCommand("copy");

  // Elimina el elemento temporal del documento
  document.body.removeChild(elementoTemp);

  // Notifica al usuario que el texto se ha copiado al portapapeles
  alert("El enlace ha sido copiado al portapapeles!");
}