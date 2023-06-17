var figurasPersonajes = {};
var personajes = [];

// Carga inicial de personajes y figuras
document.addEventListener("DOMContentLoaded", function () {
    cargarPersonajes();
});

// Función para cargar los personajes
function cargarPersonajes() {
    var nombreSerie = document.getElementById("nombreSerie").innerHTML;
    var xhr = new XMLHttpRequest();
    xhr.open('GET', `../franquicias/?nombreSerie=${nombreSerie}`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            personajes = JSON.parse(xhr.responseText);

            generarDivPersonajes();

            for (var i = 0; i < personajes.length; i++) {
                var personaje = personajes[i];
                figurasPersonajes[personaje.nombre] = {
                    figuras: []
                };
                cargarFiguras(personaje.nombre);
            }
        }
    };
    xhr.send();
}

// Función para cargar las figuras
function cargarFiguras(nombrePersonaje) {

    var xhr = new XMLHttpRequest();
    xhr.open('GET', `../franquicias/?nombrePersonaje=${nombrePersonaje}`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var figuras = JSON.parse(xhr.responseText);

            figurasPersonajes[nombrePersonaje].figuras = figuras;
            generarDivFiguras(nombrePersonaje);

        }
    };
    xhr.send();
}
function generarDivPersonajes() {
    var divPersonajes = document.getElementById('contenedorPersonajes');

    divPersonajes.innerHTML = '';

    // Verificar si hay personajes registrados
    if (personajes.length === 0) {
        var divIntermedio = document.createElement('div');
        divPersonajes.appendChild(divIntermedio);

        var noPersonajesText = document.createElement('h3');
        noPersonajesText.classList.add('text-center', 'mt-4');
        noPersonajesText.textContent = 'No hay personajes disponibles para esta franquicia';
        divIntermedio.appendChild(noPersonajesText);
        var br = document.createElement('br');
        divIntermedio.appendChild(br);
// Crear el botón para ir a franquicias
        var botonFranquicias = document.createElement('button');
        botonFranquicias.textContent = 'Ir a Franquicias';
        botonFranquicias.classList.add("btn-primary", 'mb-3');
        botonFranquicias.addEventListener('click', function () {
            window.location.href = '../franquicias';
        });
        divIntermedio.appendChild(botonFranquicias);


    } else {

        // Generar el código HTML para cada personaje
        for (var i = 0; i < personajes.length; i++) {
            var personaje = personajes[i];
            // Crear el contenedor del personaje
            var divPersonaje = document.createElement('div');
            divPersonaje.classList.add('flex-item');

            // Crear el enlace con la imagen
            var enlace = document.createElement('a');
            enlace.href = "#" + personaje.nombre;
            divPersonaje.appendChild(enlace);

            // Crear la imagen del personaje
            var imagen = document.createElement('img');
            imagen.src = '../assets/images/personajes/' + personaje.url;
            imagen.alt = 'Imagen 1';
            enlace.appendChild(imagen);

            // Crear el texto con el nombre del personaje
            var nombrePersonaje = document.createElement('p');
            nombrePersonaje.classList.add('centered-text');
            nombrePersonaje.textContent = personaje.nombre;
            divPersonaje.appendChild(nombrePersonaje);
            nombrePersonaje.addEventListener('click', function () {
                window.location.href = '#' + personaje.nombre;
            });
            // Agregar el contenedor del personaje al contenedor principal
            divPersonajes.appendChild(divPersonaje);

            // Crear el div adicional dentro del div contenedorFiguras
            var divFiguras = document.createElement('div');
            divFiguras.id = personaje.nombre;
            divFiguras.classList.add('l-wrapper');
            divFiguras.style = "padding-top:3.5%";
            divFiguras.innerHTML = `
        <div class="row hueco" >
                                                    <h1 class="h1 text-center">FIGURAS DE ${personaje.nombre}</h1>

          <div id="a" class="left-column col-12 col-lg-3 columnaIzquierda" style="padding-top:30px; background-color:transparent !important">
            <div class="bg-white p-3 pb-5">
            <img src="../assets/images/personajes/${personaje.url}" alt="${personaje.nombre}" title="Todo de ${personaje.nombre}" class="img-fluid" style="width:100%; height:100   %;">
            <button class="btn-primary verTodo mt-3" value="${personaje.nombre}">Ver todas</button>
            
</div>
</div>
          <div class="left-column col-12 col-lg-9">
            
             
            <div class="text-center" id="botonCentrado${personaje.nombre}">

              <div class="w-100">

                <div class="product-list">
                  <div id="figuras${personaje.nombre}" class="product-list-wrapper clearfix grid simple columns-6 mt-5"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      `;

            var contenedorFiguras = document.getElementById('contenedorFiguras');
            contenedorFiguras.appendChild(divFiguras);
        }
    }
    

// Obtener todos los elementos con la clase "verTodo" en minúsculas
var botonesVerTodo = document.querySelectorAll('.verTodo');

// Recorrer todos los botones y agregar el event listener
botonesVerTodo.forEach(function(boton) {
  boton.addEventListener('click', function() {
    var valor = this.value;
    // Redirigir al enlace especificado por el valor del botón
    window.location.href = "personajes/"+valor;
  });
});
}





function generarDivFiguras(nombrePersonaje) {
    var figuraPersonaje = figurasPersonajes[nombrePersonaje];
    var divFiguras = document.getElementById("figuras" + nombrePersonaje);

    // Vaciar el contenedor de figuras antes de cargar las nuevas
    divFiguras.innerHTML = '';

    // Obtener las figuras del personaje
    var figuras = figuraPersonaje.figuras;

    if (figuras.length !== 0) {
        // Mostrar las figuras en el contenedor
        for (var i = 0; i < 4; i++) {
            var figura = figuras[i];

            // Crear elemento de figura
            var figuraElement = document.createElement('div');
            figuraElement.classList.add("product-miniature", "figura" + nombrePersonaje);
            figuraElement.innerHTML = `
    <div class="product-container product-style pg-onp">
      <div class="first-block">
        <div class="product-thumbnail">
          <a href="../figura/${figura.nombre}" class="product-cover-link">
            <img src="../assets/images/figuras/${figura.primeraImagen}" alt="${figura.nombre}" title="${figura.nombre}" class="img-fluid" width="278" height="278">
          </a>
        </div>
      </div>
      <p class="product-name" title="${figura.nombre}">
        <a href="../figura/${figura.nombre}">${figura.nombre}</a>
      </p>
      <div class="product-price-and-shipping d-flex flex-wrap justify-content-center align-items-center">
        <span class="price product-price">${figura.precioConDescuento}€</span>
        ${figura.porcentajeDescuento > 0 ? `<span class="regular-price">${figura.precio}€</span>` : ""}
      </div>
    </div>
  `;

            // Agregar la figura al contenedor
            divFiguras.appendChild(figuraElement);
        }
        // Comprobar si hay más figuras por mostrar
        if (figuras.length > 4) {
            // Mostrar el botón de carga
            var loadMoreButton = document.createElement('button');
            loadMoreButton.classList.add("btn-primary");
            loadMoreButton.textContent = 'Cargar más';
            loadMoreButton.id="botonMas"+nombrePersonaje;
            loadMoreButton.addEventListener('click', function () {
                cargarMasFiguras(nombrePersonaje);
            });

            // Agregar el botón al contenedor
            document.getElementById("botonCentrado"+nombrePersonaje).appendChild(loadMoreButton);
        }
    }
}

// Función para cargar más figuras de forma paginada
function cargarMasFiguras(nombrePersonaje) {
    var figuraPersonaje = figurasPersonajes[nombrePersonaje];
    var divFiguras = document.getElementById("figuras" + nombrePersonaje);

    // Obtener las figuras del personaje
    var figuras = figuraPersonaje.figuras;

    // Calcular la cantidad de figuras ya mostradas
    var displayedFiguresCount = divFiguras.getElementsByClassName("figura" + nombrePersonaje).length;

    // Calcular la cantidad de figuras restantes por mostrar
    var remainingFiguresCount = figuras.length - displayedFiguresCount;

    // Determinar la cantidad de figuras a cargar (máximo 4)
    var loadCount = Math.min(4, remainingFiguresCount);

    // Obtener las figuras adicionales a cargar
    var additionalFigures = figuras.slice(displayedFiguresCount, displayedFiguresCount + loadCount);

    // Mostrar las figuras adicionales en el contenedor
    for (var i = 0; i < additionalFigures.length; i++) {
        var figura = additionalFigures[i];

        // Crear elemento de figura
        var figuraElement = document.createElement('div');
        figuraElement.classList.add("product-miniature", "figura" + nombrePersonaje, "retardo");
        figuraElement.innerHTML = `
    <div class="product-container product-style pg-onp">
      <div class="first-block">
        <div class="product-thumbnail">
          <a href="../figura/${figura.nombre}" class="product-cover-link">
            <img src="../assets/images/figuras/${figura.primeraImagen}" alt="${figura.nombre}" title="${figura.nombre}" class="img-fluid" width="278" height="278">
          </a>
        </div>
      </div>
      <p class="product-name" title="${figura.nombre}">
        <a href="../figura/${figura.nombre}">${figura.nombre}</a>
      </p>
      <div class="product-price-and-shipping d-flex flex-wrap justify-content-center align-items-center">
        <span class="price product-price">${figura.precioConDescuento}€</span>
        ${figura.porcentajeDescuento > 0 ? `<span class="regular-price">${figura.precio}€</span>` : ""}
      </div>
    </div>
  `;
        // Agregar la figura al contenedor
        divFiguras.appendChild(figuraElement, divFiguras.lastChild);
        setTimeout(function (element) {
            element.classList.add('visible');
        }, 10, figuraElement);
    }

    // Actualizar la cantidad de figuras ya mostradas
    displayedFiguresCount += loadCount;

    // Comprobar si quedan más figuras por mostrar
    if (displayedFiguresCount >= figuras.length) {
        // Ocultar el botón de carga
        var loadMoreButton = document.getElementById("botonMas"+nombrePersonaje);
        if (loadMoreButton) {
            loadMoreButton.style.display = 'none';
        }
    }
}