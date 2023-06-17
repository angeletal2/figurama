
var tablaMostrada = document.getElementById("tablaMostrada").innerHTML;
rellenarTabla();
var botonAnadir = document.getElementById("anadir");

// Función para cambiar de tabla y obtener la lista con sus artículos, la quito de momento
function cambiarTabla(nombreTabla) {
    document.getElementById(tablaMostrada).classList.remove("active");
    if (nombreTabla === "Pedido") {
        botonAnadir.style.visibility = "hidden";

    } else {
        botonAnadir.style.visibility = "visible";
        botonAnadir.innerHTML = "Añadir " + nombreTabla;

    }


    if (nombreTabla === "Material") {
        document.getElementById("tabla").classList.add("materiales");
        document.getElementById("tabla").classList.remove("proveedores");

    } else if (nombreTabla === "Proveedor") {
        document.getElementById("tabla").classList.add("proveedores");
        document.getElementById("tabla").classList.remove("materiales");

    } else {
        document.getElementById("tabla").classList.remove("materiales");
        document.getElementById("tabla").classList.remove("proveedores");

    }
    tablaMostrada = nombreTabla;
    document.getElementById(nombreTabla).classList.add("active");
    rellenarTabla();
}


function rellenarTabla() {
    var datos;
    var xhr = new XMLHttpRequest();
    xhr.open('GET', `RellenarTabla?tabla=${tablaMostrada}`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var respuesta = xhr.responseText;
            datos = JSON.parse(respuesta);
            mostrarTabla(datos);
        }
    };
    xhr.send();
}
var rolUsuario;
function mostrarTabla(datos) {
    if (document.getElementById("tabla").classList.contains("dataTable")) {
        $("#tabla").DataTable().destroy();
    }
    $("#tabla").empty();
    switch (tablaMostrada) {

        case "Serie":

            var claves = [
                "Id",
                "Imagen",
                "Nombre"
            ];
            var contador = 0;
            var cabecera = "<thead><tr>";
            claves.forEach(clave => (cabecera += "<th>" + clave + "</th>"));
            cabecera += "<th>Acciones</th></tr></thead><tbody>";
            $("#tabla").append(cabecera);
            datos.forEach(function (fila) {
                var filaHtml = "<tr>";
                claves.forEach(function (clave) {
                    if (clave === "Imagen") {
                        // Obtener la URL de la imagen
                        var imagenUrl = fila[clave];
                        // Generar la etiqueta de imagen con la URL correspondiente
                        var imagenHtml =
                                "<img src='assets/images/series/" +
                                imagenUrl +
                                "' alt='Imagen' width='140px'>";
                        // Agregar la etiqueta de imagen a la celda de la tabla
                        filaHtml += "<td>" + imagenHtml + "</td>";
                    } else {
                        filaHtml += "<td>" + fila[clave] + "</td>";
                    }
                });
                // Agregar botones de modificar y eliminar
                filaHtml +=
                        '<td><button type="button" class="modificarSerie mb-3 p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#modalModificarSerie">Modificar</button>';

                if (fila.Baja === 0) {
                    filaHtml +=
                            '<button type="button" class="modificarBajaSerie p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaSerie">Dar de Baja</button></td>';
                } else {
                    filaHtml +=
                            '<button type="button" class="modificarBajaSerie p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaSerie">Dar de Alta</button></td>';
                }
                filaHtml += "</tr></tbody>";
                $("#tabla").append(filaHtml);
                contador++;
            });
            // Inicializar el DataTable después de agregar los datos a la tabla
            if (!$('#tabla').hasClass('dataTable')) {
                $('#tabla').DataTable({
                    "language": {
                        "search": "Buscar:",
                        "searchPlaceholder": "Ingrese término a buscar",
                        "lengthMenu": "Mostrar _MENU_ series",
                        "info": "Mostrando de la serie _START_ a la serie _END_ de un total de _TOTAL_ series",
                        "infoEmpty": "No se encontraron registros",
                        "infoFiltered": "(filtrado de _MAX_ registros en total)",
                        "zeroRecords": "No se encontraron registros coincidentes",
                        "paginate": {
                            "first": "Primero",
                            "last": "Último",
                            "next": "Siguiente",
                            "previous": "Anterior"
                        },
                        "aria": {
                            "sortAscending": ": activar para ordenar la columna en orden ascendente",
                            "sortDescending":
                                    ": activar para ordenar la columna en orden descendente"
                        }
                    }
                });
            }






            //Modal de dar de baja o alta una serie
            $('#tabla').on('click', '.modificarBajaSerie', function () {
                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var bajaActual = datos[contador].Baja;
                mostrarModalBajaSerie(id, nombre, bajaActual);
            });



            //Modal de modificar personaje
            $('#tabla').on('click', '.modificarSerie', function () {
// Obtener los datos de la fila seleccionada

                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var imagen = datos[contador].Imagen;
                mostrarModalModificarSerie(id, nombre, imagen);
            });


            break;
        case "Figura":
            var claves = [
                "Id",
                "Primera Imagen",
                "Segunda Imagen",
                "Tercera Imagen",
                "Personaje",
                "Nombre",
                "Descripción",
                "Fecha de salida",
                "Precio",
                "Stock",
                "Altura",
                "Descuento",
                "Proveedor",
                "Material"
            ];
            var contador = 0;
            var cabecera = "<thead><tr>";
            claves.forEach(clave => (cabecera += "<th>" + clave + "</th>"));
            cabecera += "<th>Acciones</th></tr></thead><tbody>";
            $("#tabla").append(cabecera);
            datos.forEach(function (fila) {
                var filaHtml = "<tr>";
                claves.forEach(function (clave) {
                    if (clave === "Descripción") {
                        // Agregar botón para abrir el modal con la descripción
                        filaHtml +=
                                '<td><button type="button" class="descripcionFigura" data-contador="' + contador + '"data-toggle="modal" data-target="#modalDescripcionFigura">Ver descripción</button>';
                    } else if (clave === "Descuento") {
                        // Agregar el símbolo de porcentaje al valor de descuento
                        filaHtml += "<td>" + fila[clave] + "%</td>";
                    } else if (clave === "Primera Imagen" || clave === "Segunda Imagen" || clave === "Tercera Imagen") {
                        // Obtener la URL de la imagen
                        var imagenUrl = fila[clave];
                        // Generar la etiqueta de imagen con la URL correspondiente
                        var imagenHtml =
                                "<img src='assets/images/figuras/" +
                                imagenUrl +
                                "' alt='Imagen' width='130px'>";
                        // Agregar la etiqueta de imagen a la celda de la tabla
                        filaHtml += "<td>" + imagenHtml + "</td>";
                    } else if (clave === "Precio") {
                        filaHtml += "<td>" + fila[clave] + " €</td>";
                    } else if (clave === "Altura") {
                        filaHtml += "<td>" + fila[clave] + " cm</td>";
                    } else {
                        filaHtml += "<td>" + fila[clave] + "</td>";
                    }
                });
                // Agregar botones de modificar y eliminar
                filaHtml +=
                        '<td><button type="button" class="modificarFigura mb-3 p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#modalModificarFigura">Modificar</button>';

                if (fila.Baja === 0) {
                    filaHtml +=
                            '<button type="button" class="modificarBaja p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaFigura">Dar de Baja</button></td>';
                } else {
                    filaHtml +=
                            '<button type="button" class="modificarBaja p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaFigura">Dar de Alta</button></td>';
                }
                filaHtml += "</tr></tbody>";
                $("#tabla").append(filaHtml);
                contador++;
            });
            // Inicializar el DataTable después de agregar los datos a la tabla
            if (!$('#tabla').hasClass('dataTable')) {
                $('#tabla').DataTable({
                    "language": {
                        "search": "Buscar:",
                        "searchPlaceholder": "Ingrese término a buscar",
                        "lengthMenu": "Mostrar _MENU_ figuras",
                        "info": "Mostrando de la figura _START_ a la figura _END_ de un total de _TOTAL_ figuras",
                        "infoEmpty": "No se encontraron registros",
                        "infoFiltered": "(filtrado de _MAX_ registros en total)",
                        "zeroRecords": "No se encontraron registros coincidentes",
                        "paginate": {
                            "first": "Primero",
                            "last": "Último",
                            "next": "Siguiente",
                            "previous": "Anterior"
                        },
                        "aria": {
                            "sortAscending": ": activar para ordenar la columna en orden ascendente",
                            "sortDescending":
                                    ": activar para ordenar la columna en orden descendente"
                        }
                    }
                });
            }



            //Modal de la descripción las figuras
            $('#tabla').on('click', '.descripcionFigura', function () {

                var contador = $(this).data("contador");
                var descripcion = datos[contador].Descripción;
                var nombre = datos[contador].Nombre;
                mostrarModalDescripcionFigura(descripcion, nombre);
            });



            //Modal de dar de baja o alta una figura
            $('#tabla').on('click', '.modificarBaja', function () {
                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var bajaActual = datos[contador].Baja;
                mostrarModalBajaFigura(id, nombre, bajaActual);
            });



            //Modal de modificar figura
            $('#tabla').on('click', '.modificarFigura', function () {

                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var descripcion = datos[contador].Descripción;
                var personaje = datos[contador].Personaje;
                var proveedor = datos[contador].Proveedor;
                var material = datos[contador].Material;
                var precio = datos[contador].Precio;
                var altura = datos[contador].Altura;
                var descuento = datos[contador].Descuento;
                var fechaSalida = datos[contador]["Fecha de salida"];
                var stock = datos[contador].Stock;
                var primeraImagen = datos[contador]["Primera Imagen"];
                var segundaImagen = datos[contador]["Segunda Imagen"];
                var terceraImagen = datos[contador]["Tercera Imagen"];
                var personajes = datos[contador].Personajes;
                var materiales = datos[contador].Materiales;
                var proveedores = datos[contador].Proveedores;
                mostrarModalModificarFigura(id, nombre, descripcion, personaje, proveedor, material, precio, altura,
                        descuento, fechaSalida, stock, primeraImagen, segundaImagen, terceraImagen, proveedores);
            });
            personajesGlobal = datos[0].Personajes;
            proveedoresGlobal = datos[0].Proveedores;
            materialesGlobal = datos[0].Materiales;
            break;
        case "Pedido":
            var claves = [
                "Id",
                "Fecha",
                "Comprador",
                "Dirección",
                "Total",
                "Estado",
                "Detalles"
            ];
            var contador = 0;
            var cabecera = "<thead><tr>";
            claves.forEach(clave => (cabecera += "<th>" + clave + "</th>"));
            cabecera += "</tr></thead><tbody>";
            $("#tabla").append(cabecera);
            datos.forEach(function (fila) {
                var filaHtml = "<tr>";
                claves.forEach(function (clave) {
                    if (clave === "Detalles") {
                        // Agregar botón para abrir el modal con la descripción
                        filaHtml +=
                                '<td><button type="button" class="detallesPedido" data-contador="' + contador + '"data-toggle="modal" data-target="#modalDetallesPedido">Ver detalles</button></td>';
                    } else if (clave === "Estado") {
                        var selectHtml = '<select id="Pedido' + fila.Id + '" name="envio" data-contador="' + contador + '" class="selectEnvio">';
                        if (fila[clave] === "Pendiente de envío") {
                            selectHtml += '<option value="Pendiente de envío" selected>Pendiente de envío</option>';
                        } else {
                            selectHtml += '<option value="Pendiente de envío">Pendiente de envío</option>';
                        }

                        if (fila[clave] === "Enviado") {
                            selectHtml += '<option value="Enviado" selected>Enviado</option>';
                        } else {
                            selectHtml += '<option value="Enviado">Enviado</option>';
                        }

                        if (fila[clave] === "Entregado") {
                            selectHtml += '<option value="Entregado" selected>Entregado</option>';
                        } else {
                            selectHtml += '<option value="Entregado">Entregado</option>';
                        }

                        if (fila[clave] === "Cancelado") {
                            selectHtml += '<option value="Cancelado" selected>Cancelado</option>';
                        } else {
                            selectHtml += '<option value="Cancelado">Cancelado</option>';
                        }

                        selectHtml += '</select>';
                        filaHtml += '<td>' + selectHtml + '</td>';
                    } else if (clave === "Total") {
                        filaHtml += "<td>" + fila[clave].toFixed(2) + "€</td>";
                    } else {
                        filaHtml += "<td>" + fila[clave] + "</td>";
                    }
                });
                filaHtml += "</tr></tbody>";
                $("#tabla").append(filaHtml);
                contador++;
            });
            // Inicializar el DataTable después de agregar los datos a la tabla
            if (!$('#tabla').hasClass('dataTable')) {
                $('#tabla').DataTable({
                    "language": {
                        "search": "Buscar:",
                        "searchPlaceholder": "Ingrese término a buscar",
                        "lengthMenu": "Mostrar _MENU_ pedidos",
                        "info": "Mostrando del pedido _START_ al pedido _END_ de un total de _TOTAL_ pedidos",
                        "infoEmpty": "No se encontraron registros",
                        "infoFiltered": "(filtrado de _MAX_ registros en total)",
                        "zeroRecords": "No se encontraron registros coincidentes",
                        "paginate": {
                            "first": "Primero",
                            "last": "Último",
                            "next": "Siguiente",
                            "previous": "Anterior"
                        },
                        "aria": {
                            "sortAscending": ": activar para ordenar la columna en orden ascendente",
                            "sortDescending":
                                    ": activar para ordenar la columna en orden descendente"
                        }
                    }
                });
            }



            //Modal de la descripción las figuras
            $('#tabla').on('click', '.detallesPedido', function () {
                // Obtener los datos de la fila seleccionada

                var contador = $(this).data("contador");
                var descripcion = datos[contador].Detalles;
                var id = datos[contador].Id;
                mostrarModalDetallesPedido(descripcion, id);
            });
            //Actualizar estado
            $('#tabla').on('change', '.selectEnvio', function () {
                // Obtener los datos de la fila seleccionada

                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var estado = document.getElementById("Pedido" + id).value;

                var xhr = new XMLHttpRequest();
                xhr.open('GET', `EditarEstado?estado=${estado}&id=${id}`, true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                    }
                };
                xhr.send();
            });
            break;

        case "Personaje":
            var claves = [
                "Id",
                "Imagen",
                "Nombre",
                "Serie"
            ];
            var contador = 0;
            var cabecera = "<thead><tr>";
            claves.forEach(clave => (cabecera += "<th>" + clave + "</th>"));
            cabecera += "<th>Acciones</th></tr></thead><tbody>";
            $("#tabla").append(cabecera);
            datos.forEach(function (fila) {
                var filaHtml = "<tr>";
                claves.forEach(function (clave) {
                    if (clave === "Imagen") {
                        // Obtener la URL de la imagen
                        var imagenUrl = fila[clave];
                        // Generar la etiqueta de imagen con la URL correspondiente
                        var imagenHtml =
                                "<img src='assets/images/personajes/" +
                                imagenUrl +
                                "' alt='Imagen' width='130px'>";
                        // Agregar la etiqueta de imagen a la celda de la tabla
                        filaHtml += "<td>" + imagenHtml + "</td>";
                    } else {
                        filaHtml += "<td>" + fila[clave] + "</td>";
                    }
                });
                // Agregar botones de modificar y eliminar
                filaHtml +=
                        '<td><button type="button" class="modificarPersonaje mb-3 p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#modalModificarPersonaje">Modificar</button>';

                if (fila.Baja === 0) {
                    filaHtml +=
                            '<button type="button" class="modificarBajaPersonaje p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaPersonaje">Dar de Baja</button></td>';
                } else {
                    filaHtml +=
                            '<button type="button" class="modificarBajaPersonaje p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaPersonaje">Dar de Alta</button></td>';
                }
                filaHtml += "</tr></tbody>";
                $("#tabla").append(filaHtml);
                contador++;
            });
            // Inicializar el DataTable después de agregar los datos a la tabla
            if (!$('#tabla').hasClass('dataTable')) {
                $('#tabla').DataTable({
                    "language": {
                        "search": "Buscar:",
                        "searchPlaceholder": "Ingrese término a buscar",
                        "lengthMenu": "Mostrar _MENU_ personajes",
                        "info": "Mostrando del personaje _START_ al personaje _END_ de un total de _TOTAL_ personajes",
                        "infoEmpty": "No se encontraron registros",
                        "infoFiltered": "(filtrado de _MAX_ registros en total)",
                        "zeroRecords": "No se encontraron registros coincidentes",
                        "paginate": {
                            "first": "Primero",
                            "last": "Último",
                            "next": "Siguiente",
                            "previous": "Anterior"
                        },
                        "aria": {
                            "sortAscending": ": activar para ordenar la columna en orden ascendente",
                            "sortDescending":
                                    ": activar para ordenar la columna en orden descendente"
                        }
                    }
                });
            }






            //Modal de dar de baja o alta un personaje
            $('#tabla').on('click', '.modificarBajaPersonaje', function () {
                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var bajaActual = datos[contador].Baja;
                mostrarModalBajaPersonaje(id, nombre, bajaActual);
            });



            //Modal de modificar personaje
            $('#tabla').on('click', '.modificarPersonaje', function () {
// Obtener los datos de la fila seleccionada

                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var imagen = datos[contador].Imagen;
                var serie = datos[contador].Serie;
                var series = datos[contador].Series;
                mostrarModalModificarPersonaje(id, nombre, imagen, serie, series);
            });
            seriesGlobal = datos[0].Series;
            break;

        case "Proveedor":
            var claves = [
                "Id",
                "Imagen",
                "Nombre"
            ];
            var contador = 0;
            var cabecera = "<thead><tr>";
            claves.forEach(clave => (cabecera += "<th>" + clave + "</th>"));
            cabecera += "<th>Acciones</th></tr></thead><tbody>";
            $("#tabla").append(cabecera);
            datos.forEach(function (fila) {
                var filaHtml = "<tr>";
                claves.forEach(function (clave) {
                    if (clave === "Imagen") {
                        // Obtener la URL de la imagen
                        var imagenUrl = fila[clave];
                        // Generar la etiqueta de imagen con la URL correspondiente
                        var imagenHtml =
                                "<img src='assets/images/proveedores/" +
                                imagenUrl +
                                "' alt='Imagen' width='140px'>";
                        // Agregar la etiqueta de imagen a la celda de la tabla
                        filaHtml += "<td>" + imagenHtml + "</td>";
                    } else {
                        filaHtml += "<td>" + fila[clave] + "</td>";
                    }
                });
                // Agregar botones de modificar y eliminar
                filaHtml +=
                        '<td><button type="button" class="modificarProveedor mb-3 p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#modalModificarProveedor">Modificar</button>';

                if (fila.Baja === 0) {
                    filaHtml +=
                            '<button type="button" class="modificarBajaProveedor p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaProveedor">Dar de Baja</button></td>';
                } else {
                    filaHtml +=
                            '<button type="button" class="modificarBajaProveedor p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaProveedor">Dar de Alta</button></td>';
                }
                filaHtml += "</tr></tbody>";
                $("#tabla").append(filaHtml);
                contador++;
            });
            // Inicializar el DataTable después de agregar los datos a la tabla
            if (!$('#tabla').hasClass('dataTable')) {
                $('#tabla').DataTable({
                    "language": {
                        "search": "Buscar:",
                        "searchPlaceholder": "Ingrese término a buscar",
                        "lengthMenu": "Mostrar _MENU_ proveedores",
                        "info": "Mostrando del proveedor _START_ al proveedor _END_ de un total de _TOTAL_ proveedores",
                        "infoEmpty": "No se encontraron registros",
                        "infoFiltered": "(filtrado de _MAX_ proveedores en total)",
                        "zeroRecords": "No se encontraron registros coincidentes",
                        "paginate": {
                            "first": "Primero",
                            "last": "Último",
                            "next": "Siguiente",
                            "previous": "Anterior"
                        },
                        "aria": {
                            "sortAscending": ": activar para ordenar la columna en orden ascendente",
                            "sortDescending":
                                    ": activar para ordenar la columna en orden descendente"
                        }
                    }
                });
            }


            //Modal de dar de baja o alta un proveedor
            $('#tabla').on('click', '.modificarBajaProveedor', function () {
                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var bajaActual = datos[contador].Baja;
                mostrarModalBajaProveedor(id, nombre, bajaActual);
            });

            //Modal de modificar personaje
            $('#tabla').on('click', '.modificarProveedor', function () {
// Obtener los datos de la fila seleccionada

                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var imagen = datos[contador].Imagen;
                mostrarModalModificarProveedor(id, nombre, imagen);
            });
            break;

        case "Material":
            var claves = [
                "Id",
                "Nombre"
            ];
            var contador = 0;
            var cabecera = "<thead><tr>";
            claves.forEach(clave => (cabecera += "<th>" + clave + "</th>"));
            cabecera += "<th>Acciones</th></tr></thead><tbody>";
            $("#tabla").append(cabecera);
            datos.forEach(function (fila) {
                var filaHtml = "<tr>";
                claves.forEach(function (clave) {

                    filaHtml += "<td>" + fila[clave] + "</td>";

                });
                // Agregar botones de modificar y eliminarP

                if (fila.Baja === 0) {
                    filaHtml +=
                            '<td><button type="button" class="modificarBajaMaterial p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaMaterial">Dar de Baja</button></td>';
                } else {
                    filaHtml +=
                            '<td><button type="button" class="modificarBajaMaterial p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaMaterial">Dar de Alta</button></td>';
                }
                filaHtml += "</tr></tbody>";
                $("#tabla").append(filaHtml);
                contador++;
            });
            // Inicializar el DataTable después de agregar los datos a la tabla
            if (!$('#tabla').hasClass('dataTable')) {
                $('#tabla').DataTable({
                    "language": {
                        "search": "Buscar:",
                        "searchPlaceholder": "Ingrese término a buscar",
                        "lengthMenu": "Mostrar _MENU_ materiales",
                        "info": "Mostrando del material _START_ al material _END_ de un total de _TOTAL_ materiales",
                        "infoEmpty": "No se encontraron registros",
                        "infoFiltered": "(filtrado de _MAX_ materiales en total)",
                        "zeroRecords": "No se encontraron registros coincidentes",
                        "paginate": {
                            "first": "Primero",
                            "last": "Último",
                            "next": "Siguiente",
                            "previous": "Anterior"
                        },
                        "aria": {
                            "sortAscending": ": activar para ordenar la columna en orden ascendente",
                            "sortDescending":
                                    ": activar para ordenar la columna en orden descendente"
                        }
                    }
                });
            }






            //Modal de dar de baja o alta un material
            $('#tabla').on('click', '.modificarBajaMaterial', function () {
                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var bajaActual = datos[contador].Baja;
                mostrarModalBajaMaterial(id, nombre, bajaActual);
            });



            //Modal de modificar material
            $('#tabla').on('click', '.modificarMaterial', function () {
// Obtener los datos de la fila seleccionada

                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                mostrarModalModificarMaterial(id, nombre);
            });
            break;

        case "Usuario":
            var claves = [
                "Id",
                "Nombre",
                "Apellidos",
                "Email",
                "Dirección",
                "Teléfono",
                "Rol"
            ];
            var contador = 0;
            var cabecera = "<thead><tr>";
            claves.forEach(clave => (cabecera += "<th>" + clave + "</th>"));
            cabecera += "<th>Acciones</th></tr></thead><tbody>";
            $("#tabla").append(cabecera);
            datos.forEach(function (fila) {
                var filaHtml = "<tr>";
                claves.forEach(function (clave) {
                    if (clave === "Dirección") {
                        if (fila.Dirección === null || typeof fila.Dirección === "undefined") {
                            filaHtml += "<td>Sin dirección registrada</td>";
                        } else {
                            filaHtml += "<td>" + fila.Dirección + "</td>";
                        }
                    } else {
                        filaHtml += "<td>" + fila[clave] + "</td>";
                    }
                });
                // Agregar botones de modificar y eliminarP
                filaHtml +=
                        '<td><button type="button" class="modificarUsuario mb-3 p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#modalModificarUsuario">Modificar</button>';


                if (fila.Baja === 0) {
                    filaHtml +=
                            '<button type="button" class="modificarBajaUsuario p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaUsuario">Dar de Baja</button></td>';
                } else {
                    filaHtml +=
                            '<button type="button" class="modificarBajaUsuario p-2" data-contador="' + contador + '"data-toggle="modal" data-target="#mostrarModalBajaUsuario">Dar de Alta</button></td>';
                }
                filaHtml += "</tr></tbody>";
                $("#tabla").append(filaHtml);
                contador++;
                rolUsuario = fila.Admin;

            }
            );
            // Inicializar el DataTable después de agregar los datos a la tabla
            if (!$('#tabla').hasClass('dataTable')) {
                $('#tabla').DataTable({
                    "language": {
                        "search": "Buscar:",
                        "searchPlaceholder": "Ingrese término a buscar",
                        "lengthMenu": "Mostrar _MENU_ usuarios",
                        "info": "Mostrando del usuario _START_ al usuario _END_ de un total de _TOTAL_ usuarios",
                        "infoEmpty": "No se encontraron registros",
                        "infoFiltered": "(filtrado de _MAX_ usuarios en total)",
                        "zeroRecords": "No se encontraron registros coincidentes",
                        "paginate": {
                            "first": "Primero",
                            "last": "Último",
                            "next": "Siguiente",
                            "previous": "Anterior"
                        },
                        "aria": {
                            "sortAscending": ": activar para ordenar la columna en orden ascendente",
                            "sortDescending":
                                    ": activar para ordenar la columna en orden descendente"
                        }
                    }
                });
            }






            //Modal de dar de baja o alta un usuario
            $('#tabla').on('click', '.modificarBajaUsuario', function () {
                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var bajaActual = datos[contador].Baja;
                mostrarModalBajaUsuario(id, nombre, bajaActual);
            });



            //Modal de modificar material
            $('#tabla').on('click', '.modificarUsuario', function () {
// Obtener los datos de la fila seleccionada

                var contador = $(this).data("contador");
                var id = datos[contador].Id;
                var nombre = datos[contador].Nombre;
                var apellidos = datos[contador].Apellidos;
                var email = datos[contador].Email;
                var direccion = datos[contador].Dirección;
                var telefono = datos[contador].Teléfono;
                var rol = datos[contador].rol;
                var admin = datos[contador].Admin;
                mostrarModalModificarUsuario(id, nombre, apellidos, email, direccion, telefono, rol, admin);
            });
            break;
    }
}


//Listener para que cuando se clicke en añadir abra el modal correspondiente con sus datos cargados
$('#anadir').on('click', function () {
    if (tablaMostrada === "Figura") {
        var selectPersonajes = $('#figuraPersonajeAdd');
        var selectProveedores = $('#figuraProveedorAdd');
        var selectMateriales = $('#figuraMaterialAdd');
        selectPersonajes.empty();
        selectProveedores.empty();
        selectMateriales.empty();
        for (var i = 0; i < personajesGlobal.length; i++) {
            var option = $("<option></option>").text(personajesGlobal[i].nombre).val(personajesGlobal[i].nombre);
            selectPersonajes.append(option);
        }

        for (var i = 0; i < proveedoresGlobal.length; i++) {
            var option = $("<option></option>").text(proveedoresGlobal[i].nombre).val(proveedoresGlobal[i].nombre);
            selectProveedores.append(option);
        }

        for (var i = 0; i < materialesGlobal.length; i++) {
            var option = $("<option></option>").text(materialesGlobal[i].nombre).val(materialesGlobal[i].nombre);
            selectMateriales.append(option);
        }
        $('#modalAnadirFigura').modal('show');

    } else if (tablaMostrada === "Personaje") {
        var selectSeries = $('#personajeSeriesAdd');

        selectSeries.empty();
        for (var i = 0; i < seriesGlobal.length; i++) {
            var option = $("<option></option>").text(seriesGlobal[i].nombre).val(seriesGlobal[i].nombre);
            selectSeries.append(option);
        }
        $('#modalAnadirPersonaje').modal('show');
    } else if (tablaMostrada === "Usuario") {

        var selectRol = $('#rolAdd');
        selectRol.empty();
        var option = $("<option></option>").text("Común").val("Común");


        selectRol.append(option);

        if (rolUsuario === "AdminMaestro") {
            var option2 = $("<option></option>").text("Admin").val("Admin");
            selectRol.append(option2);

            var option3 = $("<option></option>").text("AdminMaestro").val("AdminMaestro");
            selectRol.append(option3);
        }
        $('#modalAnadirUsuario').modal('show');


    } else if (tablaMostrada === "Serie") {
        $('#modalAnadirSerie').modal('show');

    } else if (tablaMostrada === "Proveedor") {
        $('#modalAnadirProveedor').modal('show');

    } else if (tablaMostrada === "Material") {
        $('#modalAnadirMaterial').modal('show');

    }

});

function mostrarModalDescripcionFigura(descripcion, nombre) {
    $('#figuraDescripcionModal').text(descripcion);
    $('#figuraNombreModal').text(nombre);
    $('#modalDescripcionFigura').modal('show');
}


function mostrarModalBajaFigura(id, nombre, bajaActual) {
    $('#figuraIdBaja').val(id);
    $('#figuraNombreBaja').text(nombre);
    if (bajaActual === 0) {
        $('#preguntaBajaActual').text("dar de baja");
        $('#submitBaja').val("Dar de baja");
    } else {
        $('#preguntaBajaActual').text("dar de alta");
        $('#submitBaja').val("Dar de alta");
    }
    $('#figuraBajaActual').val(bajaActual);
    $('#modalBajaFigura').modal('show');
}

function mostrarModalModificarFigura(id, nombre, descripcion, personaje, proveedor, material, precio, altura,
        descuento, fechaSalida, stock, primeraImagen, segundaImagen, terceraImagen, proveedores) {


    $('#figuraId').val(id);
    $('#figuraNombre').val(nombre);
    $('#figuraDescripcion').val(descripcion);
    $('#figuraPrecio').val(precio);
    $('#figuraAltura').val(altura);
    $('#figuraDescuento').val(descuento);
    var fecha = new Date(fechaSalida);
    var anio = fecha.getFullYear();
    var mes = ("0" + (fecha.getMonth() + 1)).slice(-2);
    var dia = ("0" + fecha.getDate()).slice(-2);
    var fechaFormateada = anio + "-" + mes + "-" + dia;
    $("#figuraFecha").val(fechaFormateada);
    $('#figuraStock').val(stock);
    $("#figuraImagen1Old").attr("src", "assets/images/figuras/" + primeraImagen);
    $("#figuraImagen2Old").attr("src", "assets/images/figuras/" + segundaImagen);
    $("#figuraImagen3Old").attr("src", "assets/images/figuras/" + terceraImagen);
    $('#figuraPersonaje').val(personaje);
    var selectProveedores = $('#figuraProveedor');
    $('#figuraMaterial').val(material);
    selectProveedores.empty();



    for (var i = 0; i < proveedores.length; i++) {
        var option = $("<option></option>").text(proveedores[i].nombre).val(proveedores[i].nombre);
        if (proveedores[i].nombre === proveedor) {
            option.attr("selected", "selected");
        }

        selectProveedores.append(option);
    }


// Abrir el modal para editar
    $('#modalModificarFigura').modal('show');
}

function mostrarModalDetallesPedido(descripcion, numero)
{
    var detalles = "";
    descripcion.forEach(function (articulo) {
        detalles += "<br>- Nombre Figura: <br>" + articulo.figura.nombre +
                "<br>- Cantidad: " + articulo.cantidad +
                "<br>- Precio: " + (articulo.precio * articulo.cantidad).toFixed(2) +
                "€<br>"; // Agregamos dos saltos de línea después de cada artículo
    });
    $('#pedidoDetallesArticulos').html(detalles);
    $('#pedidoDetallesNumero').text(" " + numero);
    $('#modalDetallesPedido').modal('show');
}




function mostrarModalBajaPersonaje(id, nombre, bajaActual) {
    $('#personajeIdBaja').val(id);
    $('#personajeNombreBaja').text(nombre);
    if (bajaActual === 0) {
        $('#preguntaPersonajeBajaActual').text("dar de baja");
        $('#submitPersonajeBaja').val("Dar de baja");
    } else {
        $('#preguntaPersonajeBajaActual').text("dar de alta");
        $('#submitPersonajeBaja').val("Dar de alta");
    }
    $('#personajeBajaActual').val(bajaActual);
    $('#modalBajaPersonaje').modal('show');
}

function mostrarModalModificarPersonaje(id, nombre, imagen, serie) {


    $('#personajeId').val(id);
    $('#personajeNombre').val(nombre);
    $("#personajeImagenOld").attr("src", "assets/images/personajes/" + imagen);

    $('#personajeSerie').val(serie);


// Abrir el modal para editar
    $('#modalModificarPersonaje').modal('show');
}



function mostrarModalBajaSerie(id, nombre, bajaActual) {
    $('#serieIdBaja').val(id);
    $('#serieNombreBaja').text(nombre);
    if (bajaActual === 0) {
        $('#preguntaSerieBajaActual').text("dar de baja");
        $('#submitSerieBaja').val("Dar de baja");

    } else {
        $('#preguntaSerieBajaActual').text("dar de alta");
        $('#submitSerieBaja').val("Dar de alta");


    }
    $('#serieBajaActual').val(bajaActual);
    $('#modalBajaSerie').modal('show');
}

function mostrarModalModificarSerie(id, nombre, imagen) {


    $('#serieId').val(id);
    $('#serieNombre').val(nombre);
    $("#serieImagenOld").attr("src", "assets/images/series/" + imagen);



// Abrir el modal para editar
    $('#modalModificarSerie').modal('show');
}


function mostrarModalBajaProveedor(id, nombre, bajaActual) {
    $('#proveedorIdBaja').val(id);
    $('#proveedorNombreBaja').text(nombre);
    if (bajaActual === 0) {
        $('#preguntaProveedorBajaActual').text("dar de baja");
        $('#submitProveedorBaja').val("Dar de baja");

    } else {
        $('#preguntaProveedorBajaActual').text("dar de alta");
        $('#submitProveedorBaja').val("Dar de alta");


    }
    $('#proveedorBajaActual').val(bajaActual);
    $('#modalBajaProveedor').modal('show');
}

function mostrarModalModificarProveedor(id, nombre, imagen) {


    $('#proveedorId').val(id);
    $('#proveedorNombre').val(nombre);
    $("#proveedorImagenOld").attr("src", "assets/images/proveedores/" + imagen);



// Abrir el modal para editar
    $('#modalModificarProveedor').modal('show');
}




function mostrarModalBajaMaterial(id, nombre, bajaActual) {
    $('#materialIdBaja').val(id);
    $('#materialNombreBaja').text(nombre);
    if (bajaActual === 0) {
        $('#preguntaMaterialBajaActual').text("dar de baja");
        $('#submitMaterialBaja').val("Dar de baja");
    } else {
        $('#preguntaMaterialBajaActual').text("dar de alta");
        $('#submitMaterialBaja').val("Dar de alta");






    }
    $('#materialBajaActual').val(bajaActual);
    $('#modalBajaMaterial').modal('show');
}

function mostrarModalModificarMaterial(id, nombre) {


    $('#materialId').val(id);
    $('#materialNombre').val(nombre);



// Abrir el modal para editar
    $('#modalModificarMateria').modal('show');
}






function mostrarModalBajaUsuario(id, nombre, bajaActual) {
    $('#usuarioIdBaja').val(id);
    $('#usuarioNombreBaja').text(nombre);
    if (bajaActual === 0) {
        $('#preguntaUsuarioBajaActual').text("dar de baja");
        $('#submitUsuarioBaja').val("Dar de baja");

    } else {
        $('#preguntaUsuarioBajaActual').text("dar de alta");
        $('#submitUsuarioBaja').val("Dar de alta");


    }
    $('#usuarioBajaActual').val(bajaActual);
    $('#modalBajaUsuario').modal('show');
}



function mostrarModalModificarUsuario(id, nombre, apellidos, email, direccion, telefono, rol, admin) {


    $('#usuarioId').val(id);
    $('#usuarioNombre').val(nombre);
    $('#usuarioApellidos').val(apellidos);
    $('#usuarioEmail').val(email);
        $('#usuarioEmailOld').val(email);

    $('#usuarioDireccion').val(direccion);
    $('#usuarioTelefono').val(telefono);
    $('#usuarioRol').val(rol);


    var selectRol = $('#roles');
    selectRol.empty();
    var option = $("<option></option>").text("Común").val("Común");


    if (rol === "Común") {
        option.attr("selected", "selected");
    }
    selectRol.append(option);

    var option2 = $("<option></option>").text("Admin").val("Admin");



    if (admin === "AdminMaestro") {
        if (rol === "Admin") {
            option.attr("selected", "selected");
        }
        selectRol.append(option2);

        var option3 = $("<option></option>").text("AdminMaestro").val("AdminMaestro");

        if (rol === "AdminMaestro") {
            option.attr("selected", "selected");
        }
        selectRol.append(option3);
    }

// Abrir el modal para editar
    $('#modalModificarUsuario').modal('show');
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



var hayError;
function validarFigura(accion) {

    hayError = false;

    if (accion === "mod") {
        validarFiguraDescripcion("figuraDescripcion", "errorFiguraDescripcion");
        validarFiguraFecha("figuraFecha", "errorFiguraFecha");
        validarFiguraPrecio("figuraPrecio", "errorFiguraPrecio");
        validarFiguraStock("figuraStock", "errorFiguraStock");
        validarFiguraAltura("figuraAltura", "errorFiguraAltura");
        validarFiguraDescuento("figuraDescuento", "errorFiguraDescuento");
    } else {
        validarFiguraNombre("figuraNombreAdd", "errorFiguraNombreAdd");
        validarFiguraDescripcion("figuraDescripcionAdd", "errorFiguraDescripcionAdd");
        validarFiguraFecha("figuraFechaAdd", "errorFiguraFechaAdd");
        validarFiguraPrecio("figuraPrecioAdd", "errorFiguraPrecioAdd");
        validarFiguraStock("figuraStockAdd", "errorFiguraStockAdd");
        validarFiguraAltura("figuraAlturaAdd", "errorFiguraAlturaAdd");
        validarFiguraDescuento("figuraDescuentoAdd", "errorFiguraDescuentoAdd");
        validarImagen1("figuraImagen1Add", "errorFiguraImagen1Add");
        validarImagen2("figuraImagen2Add", "errorFiguraImagen2Add");
        validarImagen3("figuraImagen3Add", "errorFiguraImagen3Add");
    }



    if (!hayError) {

        if (accion === "mod") {
            parsearFormulario(document.getElementById("modificarFiguraForm"));
            document.getElementById("modificarFiguraForm").submit();
        } else {
            parsearFormulario(document.getElementById("anadirFiguraForm"));
            document.getElementById("anadirFiguraForm").submit();
        }
    }
}


function validarPersonaje(accion) {

    hayError = false;

    if (accion !== "mod") {
        validarPersonajeNombre("personajeNombreAdd", "errorPersonajeNombreAdd");
        validarImagenPersonaje("personajeImagenAdd", "errorPersonajeImagenAdd");
    }



    if (!hayError) {

        if (accion === "mod") {
            parsearFormulario(document.getElementById("modificarPersonajeForm"));
            document.getElementById("modificarPersonajeForm").submit();
        } else {
            parsearFormulario(document.getElementById("anadirPersonajeForm"));
            document.getElementById("anadirPersonajeForm").submit();
        }
    }
}

function validarSerie(accion) {

    hayError = false;

    if (accion !== "mod") {
        validarSerieNombre("serieNombreAdd", "errorSerieNombreAdd");
        validarImagenSerie("serieImagenAdd", "errorSerieImagenAdd");
    }



    if (!hayError) {

        if (accion === "mod") {
            parsearFormulario(document.getElementById("modificarSerieForm"));
            document.getElementById("modificarSerieForm").submit();
        } else {
            parsearFormulario(document.getElementById("anadirSerieForm"));
            document.getElementById("anadirSerieForm").submit();
        }
    }
}


function validarProveedor(accion) {

    hayError = false;

    if (accion !== "mod") {
        validarProveedorNombre("proveedorNombreAdd", "errorProveedorNombreAdd");
        validarImagenProveedor("proveedorImagenAdd", "errorProveedorImagenAdd");
    }



    if (!hayError) {

        if (accion === "mod") {
            parsearFormulario(document.getElementById("modificarProveedorForm"));
            document.getElementById("modificarProveedorForm").submit();
        } else {
            parsearFormulario(document.getElementById("anadirProveedorForm"));
            document.getElementById("anadirProveedorForm").submit();
        }
    }
}

function validarMaterial(accion) {

    hayError = false;

    if (accion !== "mod") {
        validarMaterialNombre("materialNombreAdd", "errorMaterialNombreAdd");
    }



    if (!hayError) {


        parsearFormulario(document.getElementById("anadirMaterialForm"));
        document.getElementById("anadirMaterialForm").submit();

    }
}






function validarUsuario(accion) {

    hayError = false;

    if (accion === "mod") {
        validarUsuarioNombre("usuarioNombre", "errorUsuarioNombre");
        validarUsuarioApellidos("usuarioApellidos", "errorUsuarioApellidos");
        validarUsuarioEmail("usuarioEmail", "errorUsuarioEmail");
        validarUsuarioDireccion("usuarioDireccion", "errorUsuarioDireccion");
        validarUsuarioTelefono("usuarioTelefono", "errorUsuarioTelefono");

    } else {
        validarUsuarioNombre("usuarioNombreAdd", "errorUsuarioNombreAdd");
        validarUsuarioApellidos("usuarioApellidosAdd", "errorUsuarioApellidosAdd");
        validarUsuarioEmail("usuarioEmailAdd", "errorUsuarioEmailAdd");
        validarUsuarioDireccion("usuarioDireccionAdd", "errorUsuarioDireccionAdd");
        validarUsuarioTelefono("usuarioTelefonoAdd", "errorUsuarioTelefonoAdd");
        validarUsuarioContrasena("usuarioContrasenaAdd", "errorUsuarioContrasenaAdd");

    }



    if (!hayError) {

        if (accion === "mod") {
            parsearFormulario(document.getElementById("modificarUsuarioForm"));
            document.getElementById("modificarUsuarioForm").submit();
        } else {
            parsearFormulario(document.getElementById("anadirUsuarioForm"));
            document.getElementById("anadirUsuarioForm").submit();
        }
    }
}










// Agrega un event listener al evento "submit" del formulario
var modificarFigura = document.getElementById("modificarFiguraForm");
modificarFigura.addEventListener("submit", function (event) {
    event.preventDefault();
    validarFigura("mod");
});

// Agrega un event listener al evento "submit" del formulario
var modificarFigura = document.getElementById("anadirFiguraForm");
modificarFigura.addEventListener("submit", function (event) {
    event.preventDefault();
    validarFigura("anadir");
});



// Agrega un event listener al evento "submit" del formulario
var modificarPersonaje = document.getElementById("modificarPersonajeForm");
modificarPersonaje.addEventListener("submit", function (event) {
    event.preventDefault();
    validarPersonaje("mod");
});

// Agrega un event listener al evento "submit" del formulario
var modificarPersonaje = document.getElementById("anadirPersonajeForm");
modificarPersonaje.addEventListener("submit", function (event) {
    event.preventDefault();
    validarPersonaje("anadir");
});


// Agrega un event listener al evento "submit" del formulario
var modificarSerie = document.getElementById("modificarSerieForm");
modificarSerie.addEventListener("submit", function (event) {
    event.preventDefault();
    validarSerie("mod");
});

// Agrega un event listener al evento "submit" del formulario
var anadirSerie = document.getElementById("anadirSerieForm");
anadirSerie.addEventListener("submit", function (event) {
    event.preventDefault();
    validarSerie("anadir");
});


// Agrega un event listener al evento "submit" del formulario
var modificarProveedor = document.getElementById("modificarProveedorForm");
modificarProveedor.addEventListener("submit", function (event) {
    event.preventDefault();
    validarProveedor("mod");
});

// Agrega un event listener al evento "submit" del formulario
var anadirProveedor = document.getElementById("anadirProveedorForm");
anadirProveedor.addEventListener("submit", function (event) {
    event.preventDefault();
    validarProveedor("anadir");
});

// Agrega un event listener al evento "submit" del formulario
var anadirMaterial = document.getElementById("anadirMaterialForm");
anadirMaterial.addEventListener("submit", function (event) {
    event.preventDefault();
    validarMaterial("anadir");
});


// Agrega un event listener al evento "submit" del formulario
var modificarUsuario = document.getElementById("modificarUsuarioForm");
modificarUsuario.addEventListener("submit", function (event) {
    event.preventDefault();
    validarUsuario("mod");
});

// Agrega un event listener al evento "submit" del formulario
var anadirUsuario = document.getElementById("anadirUsuarioForm");
anadirUsuario.addEventListener("submit", function (event) {
    event.preventDefault();
    validarUsuario("anadir");
});



function validarFiguraNombre(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (valor.length > 100) {
        errorCampoVal.innerHTML = "El nombre de la figura no puede tener más de 100 caracteres.";
    } else if (valor.length < 8) {
        errorCampoVal.innerHTML = "El nombre de la figura debe tener al menos 8 caracteres.";
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


function validarFiguraStock(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";
    } else if (isNaN(valor)) {
        errorCampoVal.innerHTML = "Debe ingresar un valor numérico.";
    } else if (Number(valor) < 0) {
        errorCampoVal.innerHTML = "No puede ingresar un stock menor que 0 unidades";
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


function validarFiguraPrecio(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";
    } else if (isNaN(valor)) {
        errorCampoVal.innerHTML = "Debe ingresar un valor numérico.";
    } else if (Number(valor) <= 0) {
        errorCampoVal.innerHTML = "El precio de la figura debe ser mayor que 0€";
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


function validarFiguraFecha(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);

    if (campoVal.value === null || campoVal.value.trim().length === 0) {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

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


function validarFiguraDescripcion(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);

    if (campoVal.value === null || campoVal.value.trim().length === 0) {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (campoVal.value.trim().length > 900) {
        errorCampoVal.innerHTML = "La descrición de la figura no puede tener más de 900 caracteres.";
    } else if (campoVal.value.trim().length < 20) {
        errorCampoVal.innerHTML = "La descripción de la figura debe tener al menos 20 caracteres.";
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


function validarFiguraDescuento(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";
    } else if (isNaN(valor)) {
        errorCampoVal.innerHTML = "Debe ingresar un valor numérico.";
    } else if (Number(valor) < 0) {
        errorCampoVal.innerHTML = "El descuento de la figura no puede ser inferior a 0%";
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


function validarFiguraAltura(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";
    } else if (isNaN(valor)) {
        errorCampoVal.innerHTML = "Debe ingresar un valor numérico.";
    } else if (Number(valor) <= 0) {
        errorCampoVal.innerHTML = "La figura debe tener más de 0cm";
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

function validarImagen1(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {

        errorCampoVal.innerHTML = "La imagen 1 es obligatoria.";
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


function validarImagen2(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);

    if (campoVal.files.length === 0) {
        errorCampoVal.innerHTML = "La imagen 2 es obligatoria.";
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


function validarImagen3(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);

    if (campoVal.files.length === 0) {
        errorCampoVal.innerHTML = "La imagen 3 es obligatoria.";
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


function validarPersonajeNombre(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (valor.length > 100) {
        errorCampoVal.innerHTML = "El nombre del personaje no puede tener más de 100 caracteres.";
    } else if (valor.length < 2) {
        errorCampoVal.innerHTML = "El nombre del personaje debe tener al menos 2 caracteres.";
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


function validarImagenPersonaje(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);

    if (campoVal.files.length === 0) {
        errorCampoVal.innerHTML = "La imagen del personaje es obligatoria.";
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


function validarSerieNombre(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (valor.length > 100) {
        errorCampoVal.innerHTML = "El nombre de la serie no puede tener más de 100 caracteres.";
    } else if (valor.length < 2) {
        errorCampoVal.innerHTML = "El nombre de la serie debe tener al menos 2 caracteres.";
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


function validarImagenSerie(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);

    if (campoVal.files.length === 0) {
        errorCampoVal.innerHTML = "La imagen de la serie es obligatoria.";
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




function validarProveedorNombre(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (valor.length > 50) {
        errorCampoVal.innerHTML = "El nombre del proveedor no puede tener más de 50 caracteres.";
    } else if (valor.length < 2) {
        errorCampoVal.innerHTML = "El nombre del proveedor debe al menos 2 caracteres.";
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



function validarImagenProveedor(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);

    if (campoVal.files.length === 0) {
        errorCampoVal.innerHTML = "La imagen del proveedor es obligatoria.";
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



function validarMaterialNombre(campo, errorCampo) {

    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        errorCampoVal.innerHTML = "Este campo no puede estar vacío.";

    } else if (valor.length > 50) {
        errorCampoVal.innerHTML = "El nombre del material no puede tener más de 50 caracteres.";
    } else if (valor.length < 3) {
        errorCampoVal.innerHTML = "El nombre del material debe tener al menos 3 caracteres.";
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

function validarUsuarioDireccion(campo, errorCampo) {
    var campoVal = document.getElementById(campo);
    var errorCampoVal = document.getElementById(errorCampo);
    var valor = campoVal.value.trim();

    if (valor === "") {
        campoVal.classList.remove("error");
        errorCampoVal.innerHTML = "";
        return true;
    }

    if (valor.length > 80) {
        errorCampoVal.innerHTML = "Este campo no puede tener una longitud superior a 80 caracteres.";
    } else {
        var partes = valor.split("-");
        if (partes.length !== 4) {
            errorCampoVal.innerHTML = "Este campo debe contener exactamente tres guiones.";
        } else {
            var ultimoValor = partes[3].trim();
            if (!/^\D*\d/.test(ultimoValor)) {
        errorCampoVal.innerHTML = "El último valor debe contener al menos un número después del guion.";
      } else {
                campoVal.classList.remove("error");
                errorCampoVal.innerHTML = "";
                return true;
            }
        }
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
