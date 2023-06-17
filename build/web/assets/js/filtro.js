// Obtener el elemento de entrada de búsqueda
var inputBusqueda = document.getElementById('input-busqueda');
var inputBusquedaRes = document.getElementById('input-busqueda-res');
// Obtener el elemento de lista de resultados
var listaResultados = document.getElementById('lista-resultados');
inputBusqueda.addEventListener('input', () => {
    inputBusquedaRes.value = inputBusqueda.value;
});
// Añadimos un evento de escucha para el input normal
inputBusquedaRes.addEventListener('input', () => {
    inputBusqueda.value = inputBusquedaRes.value;
});
var paginaSecundaria;
if (listaResultados.classList.contains("doble")) {
    paginaSecundaria = true;
} else {
    paginaSecundaria = false;
}

var dosSaltosBuscarLista = false;
if (listaResultados.classList.contains("doble2")) {
    dosSaltosBuscarLista = true;
}

var tresSaltosBusqueda = false;
if (listaResultados.classList.contains("triple")) {
    tresSaltosBusqueda = true;
}

//Funcion para cuando clique en el boton acceder si le da a volver o logarse vuelva a la pagina que se pulsó
document.getElementById("linkAcceso").addEventListener("click", function () {
    var form = document.createElement('form');
    form.method = 'POST';

    if (paginaSecundaria) {
        form.action = '../login.jsp';
    } else if (tresSaltosBusqueda) {
        form.action = '../../login.jsp';
    } else {
        form.action = 'login.jsp';
    }

    var redirectInput = document.createElement('input');
    redirectInput.type = 'hidden';
    redirectInput.name = 'redirect';
    redirectInput.value = window.location.href;

    form.appendChild(redirectInput);
    document.body.appendChild(form);
    form.submit();
});


// Función para mostrar la lista de resultados
function mostrarResultados(respuesta) {
// Limpiar la lista de resultados
    listaResultados.innerHTML = '';
    // Convertir la respuesta JSON en un objeto JavaScript
    resultados = JSON.parse(respuesta);
    // Verificar si hay resultados
    if (resultados.length > 0) {
// Recorrer los resultados y crear elementos de lista
        resultados.forEach((resultado) => {
            var li = document.createElement('li');
            li.classList.add('resultado-item');
            li.style.cursor = 'pointer';
            // Crear el formulario
            var form = document.createElement('form');
            if (dosSaltosBuscarLista) {
                form.action = "../figura/" + resultado.nombre;

            } else if (tresSaltosBusqueda) {
                form.action = "../../figura/" + resultado.nombre;

            } else if (paginaSecundaria) {
                form.action = resultado.nombre;
            } else {
                form.action = "figura/" + resultado.nombre;
            }

            form.method = 'POST'; // Establecer el método del formulario

            // Agregar el formulario al elemento <li>
            li.appendChild(form);
            // Agregar eventos al elemento <li>
            li.addEventListener('click', () => {
                form.submit(); // Enviar el formulario al hacer clic en el elemento <li>
            });
            // Crear un elemento de imagen y establecer la primera imagen, segun si viene del index o de otra pag pone una url u otra
            var img = document.createElement('img');
            if(paginaSecundaria){
                img.src="../assets/images/figuras/";
            }else if(tresSaltosBusqueda){
                                img.src="../../assets/images/figuras/";

                }else{
                                    img.src="assets/images/figuras/";

                }
            img.src += resultado.primeraImagen;
            img.alt = resultado.nombre;
            // Establecer estilos para la imagen
            img.classList.add('imagen-filtro');
            // Crear un elemento de texto para el nombre de la figura
            var span = document.createElement('span');
            span.textContent = resultado.nombre;
            // Agregar la imagen y el texto al elemento <li>
            li.appendChild(img);
            li.appendChild(span);
            // Agregar el elemento <li> a la lista de resultados
            listaResultados.appendChild(li);
        });
        // Mostrar la lista de resultados
        listaResultados.style.display = 'block';
    } else {
// Mostrar mensaje de no resultados
        var mensajeNoResultados = document.createElement('li');
        mensajeNoResultados.classList.add('resultado-item', 'no-resultados');
        mensajeNoResultados.innerHTML = 'No se encontraron resultados';
        // Crear el botón "Ir al catálogo"
        var botonCatalogo = document.createElement('a');
        botonCatalogo.classList.add('botonCatalogoError');
        botonCatalogo.textContent = 'Ir al catálogo';
        if (paginaSecundaria) {
            botonCatalogo.href = "../catalogo.jsp";
        } else {
            botonCatalogo.href = "catalogo.jsp";
        }
        botonCatalogo.addEventListener('click', () => {

        });
        // Agregar el mensaje de no resultados y el botón al elemento <li>
        mensajeNoResultados.appendChild(botonCatalogo);
        // Agregar el mensaje de no resultados a la lista
        listaResultados.appendChild(mensajeNoResultados);
        // Mostrar la lista de resultados
        listaResultados.style.display = 'block';
    }
}

function valorSeguro(texto) {

  // Strip slashes
  var sinBarras = texto.trim().replace(/\\/g, '');

  // HTML characters
  var especiales = {
    '<': '&lt;',
    '>': '&gt;',
    '&': '&amp;',
    '"': '&quot;',
    '\'': '&#x27;',
    '/': '&#x2F;'
  };

  const sinEspeciales = sinBarras.replace(/[<>&"'/]/g, (letra) => especiales[letra]);

  return sinEspeciales;
}

// Función para realizar la búsqueda
function realizarBusqueda() {
// Obtener el valor actual del campo de búsqueda
    var filtro = inputBusqueda.value.trim();
    var tooltip = document.getElementById("tooltip");
    var tooltipRes = document.getElementById("tooltip-res");
    // Verificar la longitud del filtro
    if (filtro.length >= 3) {
        tooltip.style.display = "none";
        tooltipRes.style.display = "none";
        // Realizar la solicitud AJAX al servlet de búsqueda
        var xhr = new XMLHttpRequest();
        if (paginaSecundaria) {
            xhr.open('GET', `../BuscarFigura?filtro=${filtro}`, true);
        } else if (tresSaltosBusqueda) {
            xhr.open('GET', `../../BuscarFigura?filtro=${filtro}`, true);
        } else {
            xhr.open('GET', `BuscarFigura?filtro=${filtro}`, true);
        }
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
// Obtener la respuesta del servidor
                var respuesta = xhr.responseText;
                // Procesar la respuesta y mostrar los resultados
                mostrarResultados(respuesta);
            }
        };
        xhr.send();
    } else {
// Limpiar la lista de resultados y ocultarla
        listaResultados.innerHTML = '';
        listaResultados.style.display = 'none';
        tooltip.style.display = "block";
        tooltipRes.style.display = "block";
    }
}


inputBusqueda.addEventListener('input', () => {
    realizarBusqueda();
});
inputBusquedaRes.addEventListener('input', () => {
    realizarBusqueda();
});
inputBusqueda.addEventListener('focus', () => {
    if (inputBusqueda.value.trim().length >= 3) {
        realizarBusqueda();
    }
});
inputBusquedaRes.addEventListener('focus', () => {
    if (inputBusquedaRes.value.trim().length >= 3) {
        realizarBusqueda();
    }
});
// Verificar si el clic se realizó fuera de la lista generada y el campo de búsqueda
var body = document.body;
body.addEventListener('click', (event) => {
    if (!listaResultados.contains(event.target) && event.target !== inputBusqueda && event.target !== inputBusquedaRes) {
        listaResultados.style.display = 'none';
    }
});
document.getElementById("btn-buscar").addEventListener("click", buscarTodo);
document.getElementById("btn-buscar-res").addEventListener("click", buscarTodo);



function buscarTodo() {
    if (inputBusqueda.value.trim().length < 3) {
        alert("Para buscar debes introducir al menos 3 caracteres");
    } else {
        var texto;
        if (paginaSecundaria) {
            texto = "../busqueda/" + valorSeguro(inputBusqueda.value.trim());
        } else if (tresSaltosBusqueda) {
            texto = "../../busqueda/" + valorSeguro(inputBusqueda.value.trim());
        } else {
            texto = "busqueda/" + valorSeguro(inputBusqueda.value.trim());
        }
        window.location.href = texto;
    }
}





