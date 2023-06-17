
//Métodos de la cesta

var btnCesta = document.getElementById("btn-cesta");
var inputCantidad = document.getElementById("cantidad");

function anadirArticulo() {
    var id = btnCesta.value;
    var cantidad = inputCantidad.value;

    if (cantidad === "") {
        alert("Debe seleccionar al menos una unidad para añadir el artículo a la cesta");
    } else {
        // Realizar la solicitud AJAX al servlet de búsqueda
        var xhr = new XMLHttpRequest();
        xhr.open('GET', `../ActualizarCesta?id=${id}&cantidad=${cantidad}&accion=anadir`, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Obtener la respuesta del servidor
                var respuesta = xhr.responseText;


                if (respuesta.trim() === '-1') { //-1 es si no hay stock
                    alert("No se ha podido añadir a la cesta porque entre lo que ya tiene en la cesta y lo que desea añadir supera el stock máximo");
                } else if (respuesta.trim() === '-2') {
                    alert("No se ha podido añadir porque el stock mínimo es de 1");
                } else if (respuesta.trim() === '-3') {
                    alert("No se ha podido añadir el artículo porque este ya no está disponible");
                } else {
                    alert("Artículo añadido correctamente a la cesta");
                    document.getElementById("botonAccesoCesta").innerHTML = "Cesta (" + respuesta.trim() + ")";
                }
            }
        };
        xhr.send();
    }
}

function eliminarArticulo(idFigura) {
    var res = confirm("¿Está seguro/a de que desea el artículo seleccionado?");
    if (res) {

        var xhr = new XMLHttpRequest();
        xhr.open('GET', `ActualizarCesta?id=${idFigura}&accion=eliminar`, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Obtener la respuesta del servidor
                var respuesta = xhr.responseText;
                if (respuesta.trim() !== '0') {

                    $("#articulo" + idFigura).fadeOut("slow", function () {
                        $(this).remove();
                    });

                    var totalArticulo = document.getElementById("totalArticulo" + idFigura);

                    var precioTotal = document.getElementById("precioTotal");
                    var iva = document.getElementById("iva");
                    var precioFinal = document.getElementById("precioFinal");
                    precioTotal.innerHTML = (parseFloat(precioTotal.innerHTML) - parseFloat(totalArticulo.innerHTML)).toFixed(2);

                    iva.innerHTML = (parseFloat(precioTotal.innerHTML) * 0.21).toFixed(2);
                    precioFinal.innerHTML = (parseFloat(precioTotal.innerHTML) + (parseFloat(iva.innerHTML))).toFixed(2);

                    document.getElementById("botonAccesoCesta").innerHTML = "Cesta (" + respuesta.trim() + ")";

                    alert("Articulo eliminado con exito");

                } else {
                    alert("Articulo eliminado con exito");

                    window.location.href = "index.jsp";
                }
            }


        }
        ;
        xhr.send();
    }
    ;
}

function vaciarCesta() {
    var res = confirm("Está seguro/a de que desea vaciar su cesta?");
    if (res) {
        alert("La cesta se ha vaciado correctamente");
        vaciarCestaSin();



    }
}


function vaciarCestaSin() {


    var xhr = new XMLHttpRequest();
    xhr.open('GET', `ActualizarCesta?id=1&accion=vaciar`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            var textoRespuesta = xhr.responseText;
            if (textoRespuesta === "") {
                alert("Compra realizada con éxito");
            } else {
                alert(xhr.responseText);
            }

            window.location.href = "index.jsp";

        }
    };
    xhr.send();
}



function ajustarStockFiguras(figuras) {
    // Mostrar las figuras en el contenedor
    for (var i = 0; i < figuras.length; i++) {
        var figura = figuras[i];
        actualizarStock(figura.id);
        document.getElementById("cantidad" + figura.id).value = figura.stock;
        document.getElementById("stock" + figura.id).innerHTML = figura.stock;

    }
    alert("Ha ocurrido un fallo en su compra, el stock de los productos será actualizado al máximo disponible");

}


//Función para validar que la cantidad no se pase del límite cargado previamente
function validarCantidad() {
    var cantidad = document.getElementById("cantidad");

    var valor = parseInt(cantidad.value);
    var max = parseInt(cantidad.max);

    if (valor > max) {
        cantidad.value = max; // Establece el valor máximo si se excede 
        alert("No puede exceder el límite máximo del artículo");
    }
    if (valor <= 0) {
        cantidad.value = 1; // Establece el valor mínimo si se excede
        alert("No puede exceder el límite mínimo del artículo");

    }
}


//Función para validar que la cantidad no se pase del límite cargado previamente
function actualizarStock(idFigura) {
    var cantidad = document.getElementById("cantidad" + idFigura);
    var stock = document.getElementById("stock" + idFigura);

    var valor = parseInt(cantidad.value);
    var max = parseInt(cantidad.max);
    if (stock === 0) {
        alert(1);
    } else if (isNaN(valor) || valor < 1) {
        cantidad.value = 1; // Establece el valor mínimo si se excede
        alert("No puede exceder el límite mínimo del artículo");
    } else if (valor > max) {
        cantidad.value = max; // Establece el valor máximo si se excede
        alert("No puede exceder el límite máximo del artículo");
    } else {
        //Si el valor es válido, que actualice el stock en la BD y en sesión mediante AJAX

        // Realizar la solicitud AJAX al servlet de búsqueda
        var xhr = new XMLHttpRequest();
        xhr.open('GET', `ActualizarCesta?id=${idFigura}&cantidad=${cantidad.value}&accion=modificar&&stock=${stock.innerHTML}`, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Obtener la respuesta del servidor
                var respuesta = xhr.responseText;

                var divFigura = cantidad.parentNode;
                var nombreFigura = divFigura.id;
                if (respuesta.trim() === '0') {
                    alert("Vaya... la figura " + nombreFigura + "  se ha quedado sin stock, por favor eliminela si desea proceder a su compra");
                    cantidad.value = parseInt(respuesta.trim());
                    stock.innerHTML = respuesta.trim();
                    cantidad.min = 0;
                    cantidad.value = 0;

                    actualizarPrecio(idFigura, parseInt(respuesta.trim()));
                }

                //Modificar valores si no ha devuelto menos stock del que ha solicitado comprar el usuario
                else if (parseInt(respuesta.trim()) >= cantidad.value) {
                    cantidad.max = parseInt(respuesta.trim());
                    actualizarPrecio(idFigura, parseInt(respuesta.trim()));
                } else if (respuesta.trim().length > 3) {
                    alert(respuesta.trim());
                    window.location.href = "cesta.jsp";
                    window.location.href = "cesta.jsp";
                } else {
                    alert("Vaya... el stock máximo de la figura " + nombreFigura + "  ha cambiado, ahora es de " + respuesta.trim() + " unidades");
                    cantidad.value = parseInt(respuesta.trim());
                    stock.innerHTML = respuesta.trim();

                    actualizarPrecio(idFigura, parseInt(respuesta.trim()));

                }
            }
        };
        xhr.send();
    }
}


function actualizarPrecio(idFigura, cantidad2) {
    var cantidad = document.getElementById("cantidad" + idFigura);
    cantidad.innerHTML = cantidad2;
    cantidad.max = parseInt(cantidad2);


    document.getElementById("stock" + idFigura).innerHTML = cantidad2;


    var precioUd = document.getElementById("precio" + idFigura);
    var totalArticulo = document.getElementById("totalArticulo" + idFigura);
    var cantActual = document.getElementById("numeroUd" + idFigura);




    var precioTotal = document.getElementById("precioTotal");
    var iva = document.getElementById("iva");
    var precioFinal = document.getElementById("precioFinal");

    cantActual.innerHTML = cantidad.value;
    var totalArticuloFinal = (parseInt(cantActual.innerHTML) * parseFloat(precioUd.innerHTML)).toFixed(2);

    precioTotal.innerHTML = (parseFloat(precioTotal.innerHTML) - parseFloat(totalArticulo.innerHTML) + parseFloat(totalArticuloFinal)).toFixed(2);
    if (parseFloat(precioTotal.innerHTML) < 0) {
        precioTotal.innerHTML = 0;
    }
    iva.innerHTML = (parseFloat(precioTotal.innerHTML) * 0.21).toFixed(2);
    precioFinal.innerHTML = (parseFloat(precioTotal.innerHTML) + (parseFloat(iva.innerHTML))).toFixed(2);
    totalArticulo.innerHTML = totalArticuloFinal;
}

function aumentarCantidad(idFigura) {
    var cantidadInput = document.getElementById("cantidad" + idFigura);
    cantidadInput.value = parseInt(cantidadInput.value) + 1;
    actualizarStock(idFigura);
}

function disminuirCantidad(idFigura) {
    var cantidadInput = document.getElementById("cantidad" + idFigura);
    var cantidad = parseInt(cantidadInput.value);
    cantidadInput.value = cantidad - 1;
    actualizarStock(idFigura);
}


function comprar() {
    var pais = document.getElementById("pais").value;
    var provincia = document.getElementById("provincia").value;
    var municipio = document.getElementById("municipio").value;
    var calle = document.getElementById("calle").value;

    var direccion = pais + " - " + provincia + " - " + municipio + " - " + calle;

    var xhr = new XMLHttpRequest();

    xhr.open('GET', `Comprar?direccion=${valorSeguro(direccion)}`, true);


    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            // Obtener la respuesta del servidor



            var figuras = JSON.parse(xhr.responseText);
            if (figuras.length === 0) {
                vaciarCestaSin();
                window.location.href = "index.jsp";
            } else {
                ajustarStockFiguras(figuras);
            }
        }
    };
    xhr.send();
}

var hayError;
function enviarFormulario(event) {

    hayError = false;

    var cantidades = document.getElementsByClassName("cantidadArticulo");
    var articulos = "";
    for (var i = 0; i < cantidades.length; i++) {
        var cantidad = cantidades[i];

        if (cantidad.value === '0') {
            hayError = true;
            var padre = cantidad.parentNode;

            articulos += padre.id + "\n";

        }
    }

    if (hayError) {
        alert("No puede comprar ningún artículo con 0 de stock, por favor revise los siguientes artículos y eliminelos:\n\n" + articulos);
        event.preventDefault();
    } else {
        validarNumeroCuenta(event);
        validarNombre(event);
        validarFechaCaducidad(event);
        validarCVV(event);
        validarPais(event);
        validarProvincia(event);
        validarMunicipio(event);
        validarCalle(event);

        if (!hayError) {
            event.preventDefault();

            comprar();
        }
    }
}


// Agrega un event listener al evento "submit" del formulario
var formularioTarjeta = document.getElementById("tarjeta");
formularioTarjeta.addEventListener("submit", enviarFormulario);


var togglePassword = document.getElementById("togglePassword");
var cvv = document.getElementById("cvv");

togglePassword.addEventListener('click', function () {
    if (cvv.type === "password") {
        cvv.type = "text";
        togglePassword.classList.remove("fa-eye");
        togglePassword.classList.add("fa-eye-slash");
    } else {
        cvv.type = "password";
        togglePassword.classList.remove("fa-eye-slash");
        togglePassword.classList.add("fa-eye");
    }
});

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

function parsearFormulario(formulario) {
    var campos = formulario.elements;

    for (var i = 0; i < campos.length; i++) {
        if (campos[i].type !== 'submit') {
            campos[i].value = valorSeguro(campos[i].value);
        }
    }


    return formulario;
}


function validarNombre(e) {
    var nombre = document.getElementById("titular");
    var errorNombre = document.getElementById("errorTitular");

    if (nombre.value === null || nombre.value.trim().length === 0) {
        errorNombre.innerHTML = "Este campo no puede estar vacío.";

    } else if (nombre.value.trim().length > 30) {
        errorNombre.innerHTML = "Este campo no puede tener una longitud superior a 30 caracteres.";
    } else {
        nombre.classList.remove("error");
        errorNombre.innerHTML = "";
        return true;
    }
    nombre.classList.add("error");
    if (!hayError) {
        nombre.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}


function validarPais(e) {
    var nombre = document.getElementById("pais");
    var errorNombre = document.getElementById("errorPais");

    if (nombre.value === null || nombre.value.trim().length === 0) {
        errorNombre.innerHTML = "Este campo no puede estar vacío.";

    } else if (nombre.value.trim().length > 35) {
        errorNombre.innerHTML = "Este campo no puede tener una longitud superior a 35 caracteres.";
    } else {
        nombre.classList.remove("error");
        errorNombre.innerHTML = "";
        return true;
    }
    nombre.classList.add("error");
    if (!hayError) {
        nombre.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}

function validarProvincia(e) {
    var nombre = document.getElementById("provincia");
    var errorNombre = document.getElementById("errorProvincia");

    if (nombre.value === null || nombre.value.trim().length === 0) {
        errorNombre.innerHTML = "Este campo no puede estar vacío.";

    } else if (nombre.value.trim().length > 40) {
        errorNombre.innerHTML = "Este campo no puede tener una longitud superior a 40 caracteres.";
    } else {
        nombre.classList.remove("error");
        errorNombre.innerHTML = "";
        return true;
    }
    nombre.classList.add("error");
    if (!hayError) {
        nombre.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}

function validarMunicipio(e) {
    var nombre = document.getElementById("municipio");
    var errorNombre = document.getElementById("errorMunicipio");

    if (nombre.value === null || nombre.value.trim().length === 0) {
        errorNombre.innerHTML = "Este campo no puede estar vacío.";

    } else if (nombre.value.trim().length > 40) {
        errorNombre.innerHTML = "Este campo no puede tener una longitud superior a 40 caracteres.";
    } else {
        nombre.classList.remove("error");
        errorNombre.innerHTML = "";
        return true;
    }
    nombre.classList.add("error");
    if (!hayError) {
        nombre.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}


function validarCalle(e) {
    var nombre = document.getElementById("calle");
    var errorNombre = document.getElementById("errorCalle");


    var regex = /^(?=.*\d)(?=.*[a-zA-ZáéíóúÁÉÍÓÚñÑ])[a-zA-ZáéíóúÁÉÍÓÚñÑ\d\s]*$/;

    if (nombre.value === null || nombre.value.trim().length === 0) {
        errorNombre.innerHTML = "Este campo no puede estar vacío.";

    } else if (nombre.value.trim().length > 40) {
        errorNombre.innerHTML = "Este campo no puede tener una longitud superior a 40 caracteres.";
    } else if (!regex.test(nombre.value.trim())) {
        errorNombre.innerHTML = "La dirección debe contener al menos un número y una letra, y puede incluir mayúsculas, minúsculas y tildes.";

    } else {
        nombre.classList.remove("error");
        errorNombre.innerHTML = "";
        return true;
    }
    nombre.classList.add("error");
    if (!hayError) {
        nombre.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}











function validarFechaCaducidad(e) {
    var fechaCaducidad = document.getElementById("caducidad");
    var errorFechaCaducidad = document.getElementById("errorCaducidad");

    var currentDate = new Date();
    var inputMonthYear = fechaCaducidad.value.split('/');
    var inputMonth = parseInt(inputMonthYear[0], 10);
    var inputYear = parseInt(inputMonthYear[1], 10) + 2000; // Agregar 2000 para obtener el año completo

    var currentMonth = currentDate.getMonth() + 1; // Obtener el mes actual
    var currentYear = currentDate.getFullYear(); // Obtener el año actual

    // Verificar el formato "MM/YY" con una expresión regular
    var formatoValido = /^(0[1-9]|1[0-2])\/\d{2}$/.test(fechaCaducidad.value);

    if (fechaCaducidad.value === null || fechaCaducidad.value.trim().length === 0) {
        errorFechaCaducidad.innerHTML = "Este campo no puede estar vacío.";
    } else if (!formatoValido) {
        errorFechaCaducidad.innerHTML = "El formato de fecha debe ser MM/YY (mes y año).";
    } else if (inputYear < currentYear || (inputYear === currentYear && inputMonth < currentMonth)) {
        errorFechaCaducidad.innerHTML = "La fecha de caducidad debe ser posterior a la fecha actual.";
    } else {
        fechaCaducidad.classList.remove("error");
        errorFechaCaducidad.innerHTML = "";
        return true;
    }

    fechaCaducidad.classList.add("error");
    if (!hayError) {
        fechaCaducidad.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}

function validarCVV(e) {
    var cvv = document.getElementById("cvv");
    var errorCVV = document.getElementById("errorCvv");

    if (cvv.value === null || cvv.value.trim().length === 0) {
        errorCVV.innerHTML = "Este campo no puede estar vacío.";
    } else if (!/^\d{3}$/.test(cvv.value.trim())) {
        errorCVV.innerHTML = "El CVV debe contener exactamente 3 números.";
    } else {
        cvv.classList.remove("error");
        errorCVV.innerHTML = "";
        return true;
    }

    cvv.classList.add("error");
    if (!hayError) {
        cvv.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}

function validarNumeroCuenta(e) {
    var numeroCuenta = document.getElementById("cuenta");
    var errorNumeroCuenta = document.getElementById("errorCuenta");

    if (numeroCuenta.value === null || numeroCuenta.value.trim().length === 0) {
        errorNumeroCuenta.innerHTML = "Este campo no puede estar vacío.";
    } else if (!/^\d{16}$/.test(numeroCuenta.value.trim())) {
        errorNumeroCuenta.innerHTML = "El número de cuenta debe contener exactamente 16 números.";
    } else {
        numeroCuenta.classList.remove("error");
        errorNumeroCuenta.innerHTML = "";
        return true;
    }

    numeroCuenta.classList.add("error");
    if (!hayError) {
        numeroCuenta.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}