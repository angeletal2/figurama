
window.onload = ejecutar;

var hayError = false;

function ejecutar() {
    document.getElementById("enviar").addEventListener('click', validarEmail, false);
    document.getElementById("enviar").addEventListener('click', validarNombre, false);
    document.getElementById("enviar").addEventListener('click', validarApellidos, false);
    document.getElementById("enviar").addEventListener('click', validarTelefono, false);
    document.getElementById("enviar").addEventListener('click', validarContra, false);

    var togglePassword = document.getElementById("togglePassword");
    var passwordInput = document.getElementById("contra");

    togglePassword.addEventListener('click', function () {
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            togglePassword.classList.remove("fa-eye");
            togglePassword.classList.add("fa-eye-slash");
        } else {
            passwordInput.type = "password";
            togglePassword.classList.remove("fa-eye-slash");
            togglePassword.classList.add("fa-eye");
        }
    });

}





function validarNombre(e) {
    var nombre = document.getElementById("nombre");
    var errorNombre = document.getElementById("errorNombre");

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



function validarTelefono(e) {
    var telefono = document.getElementById("telefono");
    var errorTelefono = document.getElementById("errorTelefono");

    if (telefono.value === null || telefono.value.trim().length === 0) {
        errorTelefono.innerHTML = "Este campo no puede estar vacío.";

    } else if (!/^\d{9}$/.test(telefono.value.trim())) {
        errorTelefono.innerHTML = "Este campo debe tener exactamente 9 dígitos.<br>Ejemplo: 644826846";
    } else {
        telefono.classList.remove("error");
        errorTelefono.innerHTML = "";
        return true;
    }
    telefono.classList.add("error");
    if (!hayError) {
        telefono.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}



function validarEmail(e) {
    var email = document.getElementById("email");
    var errorEmail = document.getElementById("errorEmail");

    if (email.value === null || email.value.trim().length === 0) {
        errorEmail.innerHTML = "Este campo no puede estar vacío.";
    } else if (!/^\S+@\S+\.\S+$/.test(email.value.trim())) {
        errorEmail.innerHTML = "Este campo debe tener un formato de email válido";
    } else {
        email.classList.remove("error");
        errorEmail.innerHTML = "";
        return true;
    }
    email.classList.add("error");
    if (!hayError) {
        email.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}






function validarApellidos(e) {
    var apellido = document.getElementById("apellidos");
    var errorApellido = document.getElementById("errorApellidos");

    if (apellido.value === null || apellido.value.trim().length === 0) {
        errorApellido.innerHTML = "Este campo no puede estar vacío.";

    } else if (apellido.value.trim().length > 60) {
        errorApellido.innerHTML = "Este campo no puede tener una longitud superior a 60 caracteres.";
    } else {
        apellido.classList.remove("error");
        errorApellido.innerHTML = "";
        return true;
    }
    apellido.classList.add("error");
    if (!hayError) {
        apellido.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}



function validarContra(e) {
    var contra = document.getElementById("contra");
    var errorContra = document.getElementById("errorContra");

    if (contra.value === null || contra.value.trim().length === 0) {
        errorContra.innerHTML = "Este campo no puede estar vacío.";
    } else if (contra.value.trim().length > 10) {
        errorContra.innerHTML = "Este campo no puede tener una longitud superior a 10 caracteres.";
    } else if (contra.value.trim().length < 5) {
        errorContra.innerHTML = "Este campo debe tener al menos 5 carácteres.";

    } else if (!/^(?=.*\d)(?=.*[a-zñA-ZÑáéíóúÁÉÍÓÚ])(?=.*[A-ZÁÉÍÓÚÜ])[a-zA-ZñÑáéíóúÁÉÍÓÚ\d]*$/.test(contra.value.trim())) {
        errorContra.innerHTML = "<p class='text-center'>La contraseña puede contener letras (mayúsculas y minúsculas, incluyendo letras acentuadas y la Ñ), dígitos y caracteres especiales incluyendo al menos al menos <em>un dígito</u> y <em>una letra mayúscula</u>.";
    } else {
        contra.classList.remove("error");
        errorContra.innerHTML = "";
        return true;
    }
    contra.classList.add("error");
    if (!hayError) {
        contra.focus();
        hayError = true;
    }
    e.preventDefault();
    return false;
}



function valorSeguro(texto) {

  var sinBarras = texto.trim().replace(/\\/g, '');

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