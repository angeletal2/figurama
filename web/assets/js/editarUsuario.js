






function validarDatos() {
    hayError = false;
    validarUsuarioNombre("usuarioNombreAdd", "errorUsuarioNombreAdd");
    validarUsuarioApellidos("usuarioApellidosAdd", "errorUsuarioApellidosAdd");
    validarUsuarioEmail("usuarioEmailAdd", "errorUsuarioEmailAdd");
    validarUsuarioTelefono("usuarioTelefonoAdd", "errorUsuarioTelefonoAdd");
    if (!hayError) {


        var email = document.getElementById("usuarioEmailAdd").value;
        var xhr = new XMLHttpRequest();
        xhr.open('GET', `CrudUsuario?accion=validarEmail&&email=${email}`, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                var respuesta = xhr.responseText;

                if (respuesta === "valido") {
                    alert("¡Sus datos han cambiado! Asegúrese de introducir su nuevo email la próxima vez que entre a Figurama.");
                    parsearFormulario(document.getElementById("datosUsuario"));
                    document.getElementById("datosUsuario").submit();
                } else {
                    document.getElementById("errorUsuarioEmailAdd").innerHTML = "El email que ha introducido ya está registrado, pruebe con otro.";
                }
            }
        };
        xhr.send();




    }
}



function validarDireccion(event) {
    hayError = false;
    validarPais(event);
    validarProvincia(event);
    validarMunicipio(event);
    validarCalle(event);

    if (!hayError) {
        alert("¡Su dirección ha sido modificada con éxito!");
        parsearFormulario(document.getElementById("direccionUsuario"));
        document.getElementById("direccionUsuario").submit();

    }

}







function validarContrasena() {
    hayError = false;
    validarContrasenaActual("contrasenaActual", "errorContrasenaActual");
    validarUsuarioContrasena("contrasenaNueva", "errorContrasenaNueva");

    if (!hayError) {
        alert("¡Su contraseña ha sido modificada! Por seguridad deberá iniciar sesión de nuevo.");
        parsearFormulario(document.getElementById("contraUsuario"));
        document.getElementById("contraUsuario").submit();

    }

}



// Agrega un event listener al evento "submit" del formulario
var modificarFigura = document.getElementById("datosUsuario");
modificarFigura.addEventListener("submit", function (event) {
    event.preventDefault();
    validarDatos(event);
});



// Agrega un event listener al evento "submit" del formulario
var modificarFigura = document.getElementById("direccionUsuario");
modificarFigura.addEventListener("submit", function (event) {
    event.preventDefault();
    validarDireccion(event);
});



// Agrega un event listener al evento "submit" del formulario
var modificarFigura = document.getElementById("contraUsuario");
modificarFigura.addEventListener("submit", function (event) {
    event.preventDefault();
    validarContrasena(event);
});




var togglePassword = document.getElementById("togglePassword");
var contraActu = document.getElementById("contrasenaActual");

var togglePassword2 = document.getElementById("togglePassword2");
var contraNueva = document.getElementById("contrasenaNueva");

togglePassword.addEventListener('click', function () {
    if (contraActu.type === "password") {
        contraActu.type = "text";
        togglePassword.classList.remove("fa-eye");
        togglePassword.classList.add("fa-eye-slash");
    } else {
        contraActu.type = "password";
        togglePassword.classList.remove("fa-eye-slash");
        togglePassword.classList.add("fa-eye");
    }
});

togglePassword2.addEventListener('click', function () {
    if (contraNueva.type === "password") {
        contraNueva.type = "text";
        togglePassword2.classList.remove("fa-eye");
        togglePassword2.classList.add("fa-eye-slash");
    } else {
        contraNueva.type = "password";
        togglePassword2.classList.remove("fa-eye-slash");
        togglePassword2.classList.add("fa-eye");
    }
});






function validarUsuarioNombre(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (valor.length > 30) {
        errorCampoVal.innerHTML = "Este campo no puede tener una longitud superior a 30 caracteres.";
    } else {
        campoVal.classList.remove("error");
        errorCampoVal.innerHTML = "";
        return true;
    }
    campoVal.classList.add("error");
    if (!hayError) {
        campoVal.focus();
        hayError = true;
    }
    return false;
}



function validarUsuarioApellidos(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (valor.length > 60) {
        errorCampoVal.innerHTML = "Este campo no puede tener una longitud superior a 60 caracteres.";
    } else {
        campoVal.classList.remove("error");
        errorCampoVal.innerHTML = "";
        return true;
    }
    campoVal.classList.add("error");
    if (!hayError) {
        campoVal.focus();
        hayError = true;
    }
    return false;
}


function validarUsuarioEmail(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (!/^\S+@\S+\.\S+$/.test(valor)) {
        errorCampoVal.innerHTML = "Este campo debe tener un formato de email válido";
    } else {
        campoVal.classList.remove("error");
        errorCampoVal.innerHTML = "";
        return true;
    }
    campoVal.classList.add("error");
    if (!hayError) {
        campoVal.focus();
        hayError = true;
    }
    return false;
}




function validarUsuarioTelefono(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (!/^\d{9}$/.test(valor)) {
        errorCampoVal.innerHTML = "Este campo debe tener exactamente 9 dígitos. Ejemplo: 644826846";
    } else {
        campoVal.classList.remove("error");
        errorCampoVal.innerHTML = "";
        return true;
    }
    campoVal.classList.add("error");
    if (!hayError) {
        campoVal.focus();
        hayError = true;
    }
    return false;
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

function parsearFormulario(formulario) {
    var campos = formulario.elements;

    for (var i = 0; i < campos.length; i++) {
        if (campos[i].type === 'text' || campos[i].type === 'textarea') {
            campos[i].value = valorSeguro(campos[i].value);
        }
    }


    return formulario;
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






function validarContrasenaActual(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";
    } else if (valor !== document.getElementById("contrasenaPrev").innerHTML) {
        errorCampoVal.innerHTML = "Su contraseña actual es incorrecta.";
    } else {
        campoVal.classList.remove("error");
        errorCampoVal.innerHTML = "";
        return true;
    }

    campoVal.classList.add("error");
    if (!hayError) {
        campoVal.focus();
        hayError = true;
    }
    return false;
}




function validarUsuarioContrasena(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";
    } else if (valor.length > 10) {
        errorCampoVal.innerHTML = "Este campo no puede tener una longitud superior a 10 caracteres.";
    } else if (valor.length < 5) {
        errorCampoVal.innerHTML = "Este campo debe tener al menos 5 carácteres.";
    } else if (!/^(?=.*\d)(?=.*[a-zñA-ZÑáéíóúÁÉÍÓÚ])(?=.*[A-ZÁÉÍÓÚÜ])[a-zA-ZñÑáéíóúÁÉÍÓÚ\d]*$/.test(valor)) {
        errorCampoVal.innerHTML = "La contraseña puede contener letras (mayúsculas y minúsculas, incluyendo letras acentuadas y la Ñ), dígitos y caracteres especiales incluyendo al menos al menos <em>un dígito</u> y <em>una letra mayúscula</u>.";

    } else {
        campoVal.classList.remove("error");
        errorCampoVal.innerHTML = "";
        return true;
    }

    campoVal.classList.add("error");
    if (!hayError) {
        campoVal.focus();
        hayError = true;
    }
    return false;
}



