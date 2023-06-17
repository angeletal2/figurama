

var inputPrecioMinimo = document.getElementById("precio-minimo");
var inputPrecioMaximo = document.getElementById("precio-maximo");

var labelMinimo = document.getElementById("labelMinimo");
var valorMinimo = parseFloat(labelMinimo.innerText);
var labelMaximo = document.getElementById("labelMaximo");
var valorMaximo = parseFloat(labelMaximo.innerText);



// Función para validar que la cantidad no se pase del límite mínimo
function validarPrecioMin() {



    if (inputPrecioMinimo.value < valorMinimo) {
        inputPrecioMinimo.value = valorMinimo; // Establece el valor mínimo si se excede
        alert("El valor ingresado es inferior al límite mínimo");
    }

    if (inputPrecioMinimo.value > inputPrecioMaximo.value) {
        inputPrecioMinimo.value = inputPrecioMaximo.value; // Establece el valor mínimo si se excede
        alert("El valor ingresado es inferior al límite mínimo");
    }


}

// Función para validar que la cantidad no se pase del límite máximo
function validarPrecioMax() {

    if (inputPrecioMaximo.value > valorMaximo) {
        inputPrecioMaximo.value = valorMaximo; // Establece el valor máximo si se excede
        alert("El valor ingresado excede el límite máximo");
    }

    if (inputPrecioMaximo.value < inputPrecioMinimo.value) {
        inputPrecioMaximo.value = inputPrecioMinimo.value; // Establece el valor mínimo si se excede
        alert("El valor ingresado es inferior al límite mínimo");
    }
}

var checkboxes = document.querySelectorAll('input[type="checkbox"]');
var selectOrden = document.getElementById("select-orden");

function buscarCatalogo() {
    var checkboxesSerie = [];
    var checkboxesProveedor = [];

    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
            if (checkbox.classList.contains("serie")) {
                checkboxesSerie.push(checkbox.value);
            } else {
                checkboxesProveedor.push(checkbox.value);
            }
        }
    });
    var data = new URLSearchParams();
    data.append("series", checkboxesSerie);
    data.append("proveedores", checkboxesProveedor);
    data.append("precioMinimo", inputPrecioMinimo.value);
    data.append("precioMaximo", inputPrecioMaximo.value);
    data.append("orden", selectOrden.value);

    var valorFetch;
    if (paginaSecundaria) {
        data.append("filtro", document.getElementById("valorFiltro").innerHTML);
        valorFetch = "../FiltroCatalogo";
         } else if (tresSaltosBusqueda) {
                     valorFetch = "../../FiltroCatalogo";

    } else {
        valorFetch = "FiltroCatalogo";
    }

    fetch(valorFetch, {
        method: "POST",
        body: data
    }

    )
            .then(function (response) {
                return response.json();
            })
            .then(function (json) {
                generarFiguras(json);
            })
            .catch(function (error) {
                console.error(error);
            });
}



checkboxes.forEach(function (checkbox) {
    checkbox.addEventListener("change", buscarCatalogo);
});

inputPrecioMinimo.addEventListener("change", buscarCatalogo);
inputPrecioMaximo.addEventListener("change", buscarCatalogo);
selectOrden.addEventListener("change", buscarCatalogo);

var selectPaginacion = document.getElementById("select-paginacion");
var contenedorPaginacion = document.getElementById("paginacion");
var contenedorFiguras = document.getElementById("contenedor-figuras");
var figuras = []; // Variable para almacenar todas las figuras

selectPaginacion.addEventListener("change", function () {
    var figurasPorPagina = parseInt(this.value);
    var paginaActual = 1;

    // Calcular la cantidad total de páginas según el número de figuras por página y la cantidad total de figuras
    var totalPaginas = Math.ceil(figuras.length / figurasPorPagina);

    // Actualizar la paginación en el DOM
    generarPaginacion(totalPaginas, paginaActual);

    // Mostrar las figuras correspondientes a la página actual
    mostrarFigurasPorPagina(paginaActual, figurasPorPagina);
});

function generarFiguras(json) {
    figuras = json;

    // Obtener el valor seleccionado del select de paginación
    var figurasPorPagina = parseInt(selectPaginacion.value);

    var total = document.getElementById("cantidadResultados");
    var divPaginacion = document.getElementById("paginacion");
    if (figuras.length === 0) {
        total.innerHTML = "0 productos.";
        contenedorFiguras.innerHTML = "";
        var mensajeNoResultados = document.createElement("div");
        mensajeNoResultados.classList.add("mensaje-no-resultados");
        mensajeNoResultados.textContent = "No se encontraron resultados.";
        contenedorFiguras.classList.add("sinResultados");
        // Agregar mensaje al contenedor de figuras
        contenedorFiguras.appendChild(mensajeNoResultados);
        divPaginacion.innerHTML = "";
    } else {
        if (figuras.length === 1) {
            contenedorFiguras.classList.remove("sinResultados");
            total.innerHTML = figuras.length + " producto.";
        } else {
            contenedorFiguras.classList.remove("sinResultados");
            total.innerHTML = figuras.length + " productos.";
        }

        // Calcular la cantidad total de páginas según el número de figuras por página y la cantidad total de figuras
        var totalPaginas = Math.ceil(figuras.length / figurasPorPagina);

        // Actualizar la paginación en el DOM
        generarPaginacion(totalPaginas, 1);

        // Mostrar las figuras correspondientes a la página actual
        mostrarFigurasPorPagina(1, figurasPorPagina);
    }
}

function generarPaginacion(totalPaginas, paginaActual) {

    // Limpiar el contenedor de paginación
    contenedorPaginacion.innerHTML = "";

    // Crear el elemento <ul> para la paginación
    var ul = document.createElement("ul");
    ul.classList.add("pagination");
    // Crear el botón "Anterior"
    var liPrev = document.createElement("li");
    var aPrev = document.createElement("a");
    aPrev.href = "#";
    aPrev.classList.add("prev");
    aPrev.textContent = "Anterior";
    liPrev.appendChild(aPrev);
    ul.appendChild(liPrev);

    // Crear los elementos <li> y <a> para las páginas
    if (totalPaginas <= 7) {
        for (var i = 1; i <= totalPaginas; i++) {
            var li = document.createElement("li");
            var a = document.createElement("a");
            a.href = "#";
            a.classList.add("page");
            a.textContent = i;
            if (i === paginaActual) {
                a.classList.add("active");
            }
            li.appendChild(a);
            ul.appendChild(li);
        }
    } else {
        if (paginaActual <= 3) {
            for (var i = 1; i <= 3; i++) {
                var li = document.createElement("li");
                var a = document.createElement("a");
                a.href = "#";
                a.classList.add("page");
                a.textContent = i;
                if (i === paginaActual) {
                    a.classList.add("active");
                }
                li.appendChild(a);
                ul.appendChild(li);
            }

            var liDots = document.createElement("li");
            var aDots = document.createElement("span");
            aDots.classList.add("page", "disabled");
            aDots.textContent = "...";
            liDots.appendChild(aDots);
            ul.appendChild(liDots);

            var liLast = document.createElement("li");
            var aLast = document.createElement("a");
            aLast.href = "#";
            aLast.classList.add("page");
            aLast.textContent = totalPaginas;
            liLast.appendChild(aLast);
            ul.appendChild(liLast);
        } else if (paginaActual >= totalPaginas - 2) {
            var liFirst = document.createElement("li");
            var aFirst = document.createElement("a");
            aFirst.href = "#";
            aFirst.classList.add("page");
            aFirst.textContent = 1;
            liFirst.appendChild(aFirst);
            ul.appendChild(liFirst);

            var liDots = document.createElement("li");
            var aDots = document.createElement("span");
            aDots.classList.add("page", "disabled");
            aDots.textContent = "...";
            liDots.appendChild(aDots);
            ul.appendChild(liDots);

            for (var i = totalPaginas - 2; i <= totalPaginas; i++) {
                var li = document.createElement("li");
                var a = document.createElement("a");
                a.href = "#";
                a.classList.add("page");
                a.textContent = i;
                if (i === paginaActual) {
                    a.classList.add("active");
                }
                li.appendChild(a);
                ul.appendChild(li);
            }
        } else {
            var liFirst = document.createElement("li");
            var aFirst = document.createElement("a");
            aFirst.href = "#";
            aFirst.classList.add("page");
            aFirst.textContent = 1;
            liFirst.appendChild(aFirst);
            ul.appendChild(liFirst);

            var liDotsStart = document.createElement("li");
            var aDotsStart = document.createElement("span");
            aDotsStart.classList.add("page", "disabled");
            aDotsStart.textContent = "...";
            liDotsStart.appendChild(aDotsStart);
            ul.appendChild(liDotsStart);

            for (var i = paginaActual - 1; i <= paginaActual + 1; i++) {
                var li = document.createElement("li");
                var a = document.createElement("a");
                a.href = "#";
                a.classList.add("page");
                a.textContent = i;
                if (i === paginaActual) {
                    a.classList.add("active");
                }
                li.appendChild(a);
                ul.appendChild(li);
            }

            var liDotsEnd = document.createElement("li");
            var aDotsEnd = document.createElement("span");
            aDotsEnd.classList.add("page", "disabled");
            aDotsEnd.textContent = "...";
            liDotsEnd.appendChild(aDotsEnd);
            ul.appendChild(liDotsEnd);

            var liLast = document.createElement("li");
            var aLast = document.createElement("a");
            aLast.href = "#";
            aLast.classList.add("page");
            aLast.textContent = totalPaginas;
            liLast.appendChild(aLast);
            ul.appendChild(liLast);
        }
    }

    // Crear el botón "Siguiente"
    var liNext = document.createElement("li");
    var aNext = document.createElement("a");
    aNext.href = "#";
    aNext.classList.add("next");
    aNext.textContent = "Siguiente";
    liNext.appendChild(aNext);
    ul.appendChild(liNext);

    // Agregar el <ul> al contenedor de paginación
    contenedorPaginacion.appendChild(ul);

    // Agregar eventos de clic para los botones de paginación
    var links = contenedorPaginacion.querySelectorAll("a.page");
    links.forEach(function (link) {
        link.addEventListener("click", function (event) {
            event.preventDefault();
            var pagina = parseInt(this.textContent);
            if (figuras.length !== 0) {
                cambiarPagina(pagina);

            }
        });
    });

    var linkPrev = contenedorPaginacion.querySelector("a.prev");
    linkPrev.addEventListener("click", function (event) {
        event.preventDefault();
        if (paginaActual > 1) {
            cambiarPagina(paginaActual - 1);
        }
    });

    var linkNext = contenedorPaginacion.querySelector("a.next");
    linkNext.addEventListener("click", function (event) {
        event.preventDefault();
        if (paginaActual < totalPaginas) {
            cambiarPagina(paginaActual + 1);
        }
    });


    if (paginaActual === 1) {
        linkPrev.classList.add("disabled");
    }
    if (paginaActual === totalPaginas) {
        linkNext.classList.add("disabled");

    }

}


function mostrarFigurasPorPagina(pagina, figurasPorPagina) {
    // Limpiar el contenedor de figuras
    contenedorFiguras.innerHTML = "";

    if (figuras.length !== 0) {

        // Calcular el índice de inicio y fin para las figuras a mostrar en la página actual
        var indiceInicio = (pagina - 1) * figurasPorPagina;
        var indiceFin = indiceInicio + figurasPorPagina;

        // Obtener las figuras a mostrar según los índices
        var figurasMostradas = figuras.slice(indiceInicio, indiceFin);

        // Generar las nuevas figuras y agregarlas al contenedor
        
        if(!paginaSecundaria){
        figurasMostradas.forEach(function (figura) {
            var contenedorFigura = document.createElement("article");
            contenedorFigura.classList.add("product-miniature");
            contenedorFigura.innerHTML = `
    <div class="product-container product-style pg-onp">
      <div class="first-block">
        <div class="product-thumbnail">
          <a href="figura/${figura.nombre}" class="product-cover-link">
            <img src="assets/images/figuras/${figura.primeraImagen}" alt="${figura.nombre}" title="${figura.nombre}" class="img-fluid" width="278" height="278">
          </a>
        </div>
      </div>
      <p class="product-name" title="${figura.nombre}">
        <a href="figura/${figura.nombre}">${figura.nombre}</a>
      </p>
      <div class="product-price-and-shipping d-flex flex-wrap justify-content-center align-items-center">
        <span class="price product-price">${figura.precioConDescuento}€</span>
        ${figura.porcentajeDescuento > 0 ? `<span class="regular-price">${figura.precio}€</span>` : ""}
      </div>
    </div>
  `;
            contenedorFiguras.appendChild(contenedorFigura);

            
        });
        }else{
            figurasMostradas.forEach(function (figura) {
            var contenedorFigura = document.createElement("article");
            contenedorFigura.classList.add("product-miniature");
            contenedorFigura.innerHTML = `
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
            contenedorFiguras.appendChild(contenedorFigura);

            
        });
        }
    } else {
        contenedorPaginacion.innerHTML = "";

        var mensajeNoResultados = document.createElement("div");
        mensajeNoResultados.classList.add("mensaje-no-resultados");
        mensajeNoResultados.textContent = "No se encontraron resultados.";
        contenedorFiguras.classList.add("sinResultados");
        // Agregar mensaje al contenedor de figuras
        contenedorFiguras.appendChild(mensajeNoResultados);
    }
}


function cambiarPagina(pagina) {
    // Actualizar la variable global de la página actual

    var figurasPorPagina = parseInt(selectPaginacion.value);
    var totalPaginas = Math.ceil(figuras.length / figurasPorPagina);
    var paginaActual = Math.max(1, Math.min(pagina, totalPaginas));

    // Actualizar la paginación en el DOM
    generarPaginacion(totalPaginas, paginaActual);

    // Mostrar las figuras correspondientes a la página actual
    mostrarFigurasPorPagina(paginaActual, figurasPorPagina);
}

document.addEventListener("DOMContentLoaded", function () {
buscarCatalogo();
});

