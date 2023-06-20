
var btnDeseo = document.getElementById("btn-deseo");
var btnListaDeseos = document.getElementById("btn-lista-deseos");

function cambiarEstilo() {
    btnDeseo.classList.toggle("text-danger");
    if (btnDeseo.classList.contains("text-danger")) {
        btnDeseo.title = "Eliminar de la lista de deseos";
    } else {
        btnDeseo.title = "Agregar a la lista de deseos";
    }
}

function modificarListaDeseos() {
    var id = btnListaDeseos.value;
    var accion = "anadir";
    if (btnDeseo.classList.contains("text-danger")) {
        accion = "eliminar";
    }
    // Realizar la solicitud AJAX al servlet de búsqueda
    var xhr = new XMLHttpRequest();
    xhr.open('GET', `../ActualizarListaDeseos?id=${id}&accion=${accion}`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            // Obtener la respuesta del servidor
            var respuesta = xhr.responseText;

            alert(respuesta);

        }
    };
    cambiarEstilo();
    xhr.send();
}


function eliminarListaDeseos(id) {
    var confirmacion = confirm("¿Está seguro de que desea eliminar el artículo de su lista de deseos?");
    if (confirmacion) {
        // Realizar la solicitud AJAX al servlet de búsqueda
        var xhr = new XMLHttpRequest();
        xhr.open('GET', `ActualizarListaDeseos?id=${id}&&accion=eliminar`, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Obtener la respuesta del servidor
                var respuesta = xhr.responseText;
                alert("Producto eliminado con éxito de la lista de deseos");
                location.href = "deseos.jsp";

            }
        };
        xhr.send();
    }
}