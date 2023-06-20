<%-- 
    Document   : admin
    Created on : 7 jun 2023, 0:29:19
    Author     : Angel
--%>

<%@page import="modelo.entidades.Usuario"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (request.getSession().getAttribute("usuario") == null || ((Usuario) request.getSession().getAttribute("usuario")).getRol().equals("Común")) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Figurama - Administración</title>
        <link rel="icon" type="image/x-icon" href="assets/images/logomini.jpg">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/css/bootstrap.css"/>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css"/>
        <link rel="stylesheet" href="assets/css/styles.css">
        <link rel="stylesheet" href="assets/css/admin.css">
    </head>
    <body>



        <nav class="mt-4">
            <ul>
                <li onclick="cambiarTabla('Usuario')" id="Usuario" <c:if test="${tablaMostrada eq 'Usuario'}">class="active"</c:if>>Usuarios</li>
                <li onclick="cambiarTabla('Serie')" id="Serie" <c:if test="${tablaMostrada eq 'Serie'}">class="active"</c:if>>Series</li>
                <li onclick="cambiarTabla('Personaje')" id="Personaje" <c:if test="${tablaMostrada eq 'Personaje'}">class="active"</c:if>>Personajes</li>
                <li onclick="cambiarTabla('Figura')" id="Figura" <c:if test="${tablaMostrada eq 'Figura'}">class="active"</c:if>>Figuras</li>
                <li onclick="cambiarTabla('Pedido')" id="Pedido"<c:if test="${tablaMostrada eq 'Pedido'}">class="active"</c:if>>Pedidos</li>
                <li onclick="cambiarTabla('Proveedor')" id="Proveedor"<c:if test="${tablaMostrada eq 'Proveedor'}">class="active"</c:if>>Proveedores</li>
                <li onclick="cambiarTabla('Material')" id="Material"<c:if test="${tablaMostrada eq 'Material'}">class="active"</c:if>>Materiales</li>
                    <li onclick="location.href = 'index.jsp'" style="float:right">Ir a la tienda</li>

                </ul>
            </nav>



            <span id="tablaMostrada" style="display:none">${tablaMostrada}</span>

        <div class="container mb-5 text-center" style="margin-top:10px; width: auto; max-width:80vw">


            <div class="mt-2 text-center w-100">
                <button class="btn btn-primary text-center mb-3 h1" id="anadir">Añadir ${tablaMostrada}</button>
            </div>


            <table id="tabla" class="table table-striped table-hover table-responsive text-center w-auto" style="margin: 0 auto;">



            </table>
        </div>



        <div class="modal fade" id="modalDescripcionFigura" tabindex="-1" aria-labelledby="modalDescripcionFiguraLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Descripción de la figura:<br> <span id="figuraNombreModal"></span></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <div class="modal-body">
                        <p id="figuraDescripcionModal"></p>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalBajaFigura" tabindex="-1" aria-labelledby="modalBajaFiguraLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Editar Baja Figura</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudFigura?accion=cambiarEstado" method="POST" enctype="multipart/form-data">
                        <input type="hidden" id="figuraIdBaja" name="id">

                        <div class="modal-body">
                            ¿Está seguro/a de que desea <span id="preguntaBajaActual"></span> la figura<br> <span id="figuraNombreBaja"></span>?
                        </div>
                        <div class="modal-footer">

                            <input type="submit" id="submitBaja" class="btn btn-primary" value="Aceptar"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalModificarFigura" tabindex="-1" aria-labelledby="modalModificarFiguraLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modificar Figura</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudFigura?accion=modificar" id="modificarFiguraForm" method="POST" enctype="multipart/form-data">
                        <input type="hidden" id="figuraId" name="figuraId">

                        <div class="modal-body">
                            <div class="form-group">
                                <label for="figuraImagen1Old">Primera Imagen Actual:</label>
                                <div class="text-center">
                                    <img id="figuraImagen1Old" alt="Imagen 1" class="img-responsive img-preview">
                                </div>
                                <br>
                                <label for="figuraImagen1">Nueva Primera Imagen:</label>
                                <input type="file" class="form-control" name="figuraImagen1" accept="image/*">
                            </div>

                            <div class="form-group">
                                <label for="figuraImagen2Old">Segunda Imagen Actual:</label>
                                <div class="text-center">
                                    <img id="figuraImagen2Old"  alt="Imagen 2" class="img-responsive img-preview">
                                </div>
                                <br>
                                <label for="figuraImagen2">Nueva Segunda Imagen:</label>
                                <input type="file" class="form-control" name="figuraImagen2" accept="image/*">
                            </div>
                            <div class="form-group">
                                <label for="figuraImagen3Old">Tercera Imagen Actual:</label>
                                <div class="text-center">
                                    <img id="figuraImagen3Old"  alt="Imagen 2" class="img-responsive img-preview">
                                </div>
                                <br>
                                <label for="figuraImagen3">Nueva Tercera Imagen:</label>
                                <input type="file" class="form-control" name="figuraImagen3" accept="image/*">
                            </div>

                            <div class="form-group">
                                <label for="personaje">Personaje</label>
                                <input type="text" readonly="" class="form-control" id="figuraPersonaje" name="figuraPersonaje">

                            </div>

                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" readonly="" class="form-control" id="figuraNombre" name="figuraNombre">

                            </div>

                            <div class="form-group">
                                <label for="descripcion">Descripción</label>
                                <textarea id="figuraDescripcion"class="form-control" name="figuraDescripcion"></textarea>
                                <p class="text-center w-100 text-danger" id="errorFiguraDescripcion"></p>

                            </div>

                            <div class="form-group">
                                <label for="fecha">Fecha de salida</label>
                                <input type="date"class="form-control" id="figuraFecha" name="figuraFecha">
                                <p class="text-center w-100 text-danger" id="errorFiguraFecha"></p>

                            </div>

                            <div class="form-group">
                                <label for="precio">Precio (€)</label>
                                <input type="number"class="form-control" id="figuraPrecio" name="figuraPrecio" step="0.01">
                                <p class="text-center w-100 text-danger" id="errorFiguraPrecio"></p>

                            </div>

                            <div class="form-group">
                                <label for="stock">Stock</label>
                                <input type="number"class="form-control" id="figuraStock" name="figuraStock">
                                <p class="text-center w-100 text-danger" id="errorFiguraStock"></p>
                            </div>

                            <div class="form-group">
                                <label for="altura">Altura (cm)</label>
                                <input type="number"class="form-control" id="figuraAltura" name="figuraAltura">
                                <p class="text-center w-100 text-danger" id="errorFiguraAltura"></p>
                            </div>

                            <div class="form-group">
                                <label for="descuento">Descuento (%)</label>
                                <input type="number"class="form-control" id="figuraDescuento" name="figuraDescuento">
                                <p class="text-center w-100 text-danger" id="errorFiguraDescuento"></p>
                            </div>

                            <div class="form-group">
                                <label for="proveedor">Proveedor</label>
                                <select id="figuraProveedor"class="form-control" name="figuraProveedor">

                                </select>
                            </div>

                            <div class="form-group">
                                <label for="material">Material</label>
                                <input type="text" readonly="" class="form-control" id="figuraMaterial" name="figuraMaterial">

                            </div>

                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="modificarFigura" class="btn btn-primary" value="Modificar figura"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>



        <div class="modal fade" id="modalAnadirFigura" tabindex="-1" role="dialog" aria-labelledby="modalAnadirFiguraLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Añadir Figura</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudFigura?accion=anadir" method="POST" id="anadirFiguraForm" enctype="multipart/form-data">

                        <div class="modal-body">
                            <div class="form-group">

                                <label for="figuraImagen1Add">Primera Imagen:</label>
                                <input type="file" id="figuraImagen1Add" class="form-control" name="figuraImagen1" accept="image/*">
                                <p class="text-center w-100 text-danger" id="errorFiguraImagen1Add"></p>

                            </div>

                            <div class="form-group">

                                <label for="figuraImagen2">Segunda Imagen:</label>
                                <input type="file" id="figuraImagen2Add" class="form-control" name="figuraImagen2" accept="image/*">
                                <p class="text-center w-100 text-danger" id="errorFiguraImagen2Add"></p>

                            </div>
                            <div class="form-group">

                                <label for="figuraImagen3">Tercera Imagen:</label>
                                <input type="file" id="figuraImagen3Add"  class="form-control" name="figuraImagen3" accept="image/*">
                                <p class="text-center w-100 text-danger" id="errorFiguraImagen3Add"></p>

                            </div>

                            <div class="form-group">
                                <label for="personaje">Personaje</label>
                                <select id="figuraPersonajeAdd"class="form-control" name="figuraPersonaje">

                                </select>
                            </div>

                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" id="figuraNombreAdd" name="figuraNombre">
                                <p class="text-center w-100 text-danger" id="errorFiguraNombreAdd"></p>
                            </div>

                            <div class="form-group">
                                <label for="descripcion">Descripción</label>
                                <textarea id="figuraDescripcionAdd"class="form-control" name="figuraDescripcion"></textarea>
                                <p class="text-center w-100 text-danger" id="errorFiguraDescripcionAdd"></p>

                            </div>

                            <div class="form-group">
                                <label for="fecha">Fecha de salida</label>
                                <input type="date"class="form-control" id="figuraFechaAdd" name="figuraFecha">
                                <p class="text-center w-100 text-danger" id="errorFiguraFechaAdd"></p>

                            </div>

                            <div class="form-group">
                                <label for="precio">Precio (€)</label>
                                <input type="number"class="form-control" id="figuraPrecioAdd" name="figuraPrecio" step="0.1">
                                <p class="text-center w-100 text-danger" id="errorFiguraPrecioAdd"></p>
                            </div>

                            <div class="form-group">
                                <label for="stock">Stock</label>
                                <input type="number"class="form-control" id="figuraStockAdd"  name="figuraStock">
                                <p class="text-center w-100 text-danger" id="errorFiguraStockAdd"></p>

                            </div>

                            <div class="form-group">
                                <label for="altura">Altura (cm)</label>
                                <input type="number"class="form-control" id="figuraAlturaAdd" name="figuraAltura">
                                <p class="text-center w-100 text-danger" id="errorFiguraAlturaAdd"></p>

                            </div>

                            <div class="form-group">
                                <label for="descuento">Descuento (%)</label>
                                <input type="number"class="form-control" id="figuraDescuentoAdd" name="figuraDescuento">
                                <p class="text-center w-100 text-danger" id="errorFiguraDescuentoAdd"></p>

                            </div>

                            <div class="form-group">
                                <label for="proveedor">Proveedor</label>
                                <select id="figuraProveedorAdd"class="form-control" name="figuraProveedor">

                                </select>
                            </div>

                            <div class="form-group">
                                <label for="material">Material</label>
                                <select id="figuraMaterialAdd"class="form-control" name="figuraMaterial">

                                </select>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="btnAnadirFigura" class="btn btn-primary" value="Añadir figura"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>




        <div class="modal fade" id="modalDetallesPedido" tabindex="-1" aria-labelledby="modalDescripcionFiguraLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Detalles del pedido número:<span id="pedidoDetallesNumero"></span></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <div class="modal-body">
                        <p id="pedidoDetallesArticulos"></p>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>






        <div class="modal fade" id="modalBajaPersonaje" tabindex="-1" aria-labelledby="modalBajaPersonajeLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Editar Baja Personaje</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudPersonaje?accion=cambiarEstado" method="POST" enctype="multipart/form-data">
                        <input type="hidden" id="personajeIdBaja" name="id">

                        <div class="modal-body">
                            ¿Está seguro/a de que desea <span id="preguntaPersonajeBajaActual"></span> al personaje<br> <span id="personajeNombreBaja"></span>?
                        </div>
                        <div class="modal-footer">

                            <input type="submit" id="submitBaja" class="btn btn-primary" value="Aceptar"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div class="modal fade" id="modalModificarPersonaje" tabindex="-1" aria-labelledby="modalModificarPersonajeLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modificar Personaje</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudPersonaje?accion=modificar" id="modificarPersonajeForm" method="POST" enctype="multipart/form-data">
                        <input type="hidden" id="personajeId" name="personajeId">

                        <div class="modal-body">
                            <div class="form-group">
                                <label for="personajeImagenOld">Imagen Actual:</label>
                                <div class="text-center">
                                    <img id="personajeImagenOld" alt="Imagen" class="img-responsive img-preview">
                                </div>
                                <br>
                                <label for="personajeImagen">Nueva Imagen:</label>
                                <input type="file" class="form-control" name="personajeImagen" accept="image/*">
                            </div>

                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" readonly="" class="form-control" id="personajeNombre" name="personajeNombre">
                            </div>


                            <div class="form-group">
                                <label for="serie">Serie</label>
                                <input type="text" readonly="" class="form-control" id="personajeSerie" name="personajeSerie">

                            </div>

                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="modificarPersonaje" class="btn btn-primary" value="Modificar personaje"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>



        <div class="modal fade" id="modalAnadirPersonaje" tabindex="-1" role="dialog" aria-labelledby="modalAnadirPersonajeLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Añadir Personaje</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudPersonaje?accion=anadir" method="POST" id="anadirPersonajeForm" enctype="multipart/form-data">

                        <div class="modal-body">
                            <div class="form-group">

                                <label for="figuraImagen1Add">Imagen:</label>
                                <input type="file" id="personajeImagenAdd" class="form-control" name="personajeImagen" accept="image/*">
                                <p class="text-center w-100 text-danger" id="errorPersonajeImagenAdd"></p>

                            </div>


                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" id="personajeNombreAdd" name="personajeNombre">
                                <p class="text-center w-100 text-danger" id="errorPersonajeNombreAdd"></p>
                            </div>

                            <div class="form-group">
                                <label for="serie">Serie</label>
                                <select id="personajeSeriesAdd"class="form-control" name="personajeSerie">

                                </select>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="btnAnadirPersonaje" class="btn btn-primary" value="Añadir personaje"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>


        <div class="modal fade" id="modalBajaSerie" tabindex="-1" aria-labelledby="modalBajaSerieLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Editar Baja Serie</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudSerie?accion=cambiarEstado" method="POST" enctype="multipart/form-data">
                        <input type="hidden" id="serieIdBaja" name="id">

                        <div class="modal-body">
                            ¿Está seguro/a de que desea <span id="preguntaSerieBajaActual"></span> a la serie <br> <span id="serieNombreBaja"></span>?
                        </div>
                        <div class="modal-footer">

                            <input type="submit" id="submitBaja" class="btn btn-primary" value="Aceptar"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div class="modal fade" id="modalModificarSerie" tabindex="-1" aria-labelledby="modalModificarSerieLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modificar Serie</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudSerie?accion=modificar" id="modificarSerieForm" method="POST" enctype="multipart/form-data">
                        <input type="hidden" id="serieId" name="serieId">

                        <div class="modal-body">
                            <div class="form-group">
                                <label for="serieImagenOld">Imagen Actual:</label>
                                <div class="text-center">
                                    <img id="serieImagenOld" alt="Imagen" class="img-responsive img-preview">
                                </div>
                                <br>
                                <label for="serieImagen">Nueva Imagen:</label>
                                <input type="file" class="form-control" name="serieImagen" accept="image/*">
                            </div>

                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" readonly="" class="form-control" id="serieNombre" name="serieNombre">
                            </div>




                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="modificarSerie" class="btn btn-primary" value="Modificar serie"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>



        <div class="modal fade" id="modalAnadirSerie" tabindex="-1" role="dialog" aria-labelledby="modalAnadirSerieLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Añadir Serie</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudSerie?accion=anadir" method="POST" id="anadirSerieForm" enctype="multipart/form-data">

                        <div class="modal-body">
                            <div class="form-group">

                                <label for="figuraImagen1Add">Imagen:</label>
                                <input type="file" id="serieImagenAdd" class="form-control" name="serieImagen" accept="image/*">
                                <p class="text-center w-100 text-danger" id="errorSerieImagenAdd"></p>

                            </div>


                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" id="serieNombreAdd" name="serieNombre">
                                <p class="text-center w-100 text-danger" id="errorSerieNombreAdd"></p>
                            </div>



                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="btnAnadirSerie" class="btn btn-primary" value="Añadir serie"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>




        <div class="modal fade" id="modalBajaMaterial" tabindex="-1" aria-labelledby="modalBajaMaterialLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Editar Baja Material</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudMaterial?accion=cambiarEstado" method="POST" enctype="multipart/form-data">
                        <input type="hidden" id="materialIdBaja" name="id">

                        <div class="modal-body">
                            ¿Está seguro/a de que desea <span id="preguntaMaterialBajaActual"></span> al material <br> <span id="materialNombreBaja"></span>?
                        </div>
                        <div class="modal-footer">

                            <input type="submit" id="submitMaterialBaja" class="btn btn-primary" value="Aceptar"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>




        <div class="modal fade" id="modalAnadirProveedor" tabindex="-1" role="dialog" aria-labelledby="modalAnadirMaterialLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Añadir Proveedor</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudProveedor?accion=anadir" method="POST" id="anadirProveedorForm" enctype="multipart/form-data">

                        <div class="modal-body">
                            <div class="form-group">

                                <label for="proveedorImagenAdd">Imagen:</label>
                                <input type="file" id="proveedorImagenAdd" class="form-control" name="proveedorImagen" accept="image/*">
                                <p class="text-center w-100 text-danger" id="errorProveedorImagenAdd"></p>

                            </div>

                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" id="proveedorNombreAdd" name="proveedorNombre">
                                <p class="text-center w-100 text-danger" id="errorProveedorNombreAdd"></p>
                            </div>



                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="btnAnadirProveedor" class="btn btn-primary" value="Añadir proveedor"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>




        <div class="modal fade" id="modalBajaProveedor" tabindex="-1" aria-labelledby="modalBajaProveedorLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Editar Baja Proveedor</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudMaterial?accion=cambiarEstado" method="POST" enctype="multipart/form-data">
                        <input type="hidden" id="proveedorIdBaja" name="id">

                        <div class="modal-body">
                            ¿Está seguro/a de que desea <span id="preguntaProveedorBajaActual"></span> al proveedor <br> <span id="proveedorNombreBaja"></span>?
                        </div>
                        <div class="modal-footer">

                            <input type="submit" id="submitProveedorBaja" class="btn btn-primary" value="Aceptar"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div class="modal fade" id="modalModificarProveedor" tabindex="-1" aria-labelledby="modalModificarProveedorLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modificar Proveedor</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudProveedor?accion=modificar" id="modificarProveedorForm" method="POST" enctype="multipart/form-data">
                        <input type="hidden" id="proveedorId" name="proveedorId">

                        <div class="modal-body">
                            <div class="form-group">
                                <label for="proveedorImagenOld">Imagen Actual:</label>
                                <div class="text-center">
                                    <img id="proveedorImagenOld" alt="Imagen" class="img-responsive img-preview">
                                </div>
                                <br>
                                <label for="proveedorImagen">Nueva Imagen:</label>
                                <input type="file" class="form-control" name="proveedorImagen" accept="image/*">
                            </div>

                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" readonly="" class="form-control" id="proveedorNombre" name="proveedorNombre">
                            </div>




                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="modificarProveedor" class="btn btn-primary" value="Modificar proveedor"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>



        <div class="modal fade" id="modalAnadirMaterial" tabindex="-1" role="dialog" aria-labelledby="modalAnadirMaterialLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Añadir Material</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudMaterial?accion=anadir" method="POST" id="anadirMaterialForm" enctype="multipart/form-data">

                        <div class="modal-body">



                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" id="materialNombreAdd" name="materialNombre">
                                <p class="text-center w-100 text-danger" id="errorMaterialNombreAdd"></p>
                            </div>



                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="btnAnadirMaterial" class="btn btn-primary" value="Añadir proveedor"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>










        <div class="modal fade" id="modalBajaUsuario" tabindex="-1" aria-labelledby="modalBajaUsuarioLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Editar Baja Usuario</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudUsuario?accion=cambiarEstado" method="POST">
                        <input type="hidden" id="usuarioIdBaja" name="id">

                        <div class="modal-body">
                            ¿Está seguro/a de que desea <span id="preguntaUsuarioBajaActual"></span> al usuario <br> <span id="usuarioNombreBaja"></span>?
                        </div>
                        <div class="modal-footer">

                            <input type="submit" id="submitUsuarioBaja" class="btn btn-primary" value="Aceptar"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div class="modal fade" id="modalModificarUsuario" tabindex="-1" aria-labelledby="modalModificarUsuarioLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modificar Usuario</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudUsuario?accion=modificar" id="modificarUsuarioForm" method="POST">
                        <input type="hidden" id="usuarioId" name="usuarioId">

                        <div class="modal-body">


                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" id="usuarioNombre" name="usuarioNombre">
                                <p class="text-center w-100 text-danger" id="errorUsuarioNombre"></p>

                            </div>

                            <div class="form-group">
                                <label for="nombre">Apellidos</label>
                                <input type="text" class="form-control" id="usuarioApellidos" name="usuarioApellidos">
                                <p class="text-center w-100 text-danger" id="errorUsuarioApellidos"></p>

                            </div>
                            <input type="hidden" id="usuarioEmailOld" name="usuarioEmailOld">

                            <div class="form-group">
                                <label for="nombre">Email</label>
                                <input type="text" class="form-control" id="usuarioEmail" name="usuarioEmail">
                                <p class="text-center w-100 text-danger" id="errorUsuarioEmail"></p>

                            </div>

                            <div class="form-group">
                                <label for="nombre">Dirección</label>
                                <input type="text" class="form-control" id="usuarioDireccion" name="usuarioDireccion">
                                <p class="text-center w-100 text-danger" id="errorUsuarioDireccion"></p>

                            </div>

                            <div class="form-group">
                                <label for="nombre">Teléfono</label>
                                <input type="text" class="form-control" id="usuarioTelefono" name="usuarioTelefono">
                                <p class="text-center w-100 text-danger" id="errorUsuarioTelefono"></p>

                            </div>
                            <c:if test="${sessionScope.usuario.rol eq 'AdminMaestro'}">  

                                <div class="form-group">
                                    <label for="nombre">Contraseña</label>
                                    <input type="password" class="form-control" id="usuarioContrasena" name="usuarioContrasena">
                                    <p class="text-center w-100 text-danger" id="errorUsuarioContrasena"></p>
                                </div>
                            </c:if>
                                                            <c:if test="${sessionScope.usuario.rol eq 'Admin'}">  

                                <input type="hidden" class="form-control" id="usuarioContrasena" name="usuarioContrasena">
                            </c:if>

                            <div class="form-group">
                                <label for="serie">Rol</label>
                                <select id="roles"class="form-control" name="usuarioRol">

                                </select>
                            </div>



                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="modificarUsuario" class="btn btn-primary" value="Modificar usuario"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>



        <div class="modal fade" id="modalAnadirUsuario" tabindex="-1" role="dialog" aria-labelledby="modalAnadirUsuarioLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Añadir Usuario</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">&times;</button>
                    </div>
                    <form action="CrudUsuario?accion=anadir" method="POST" id="anadirUsuarioForm">

                        <div class="modal-body">


                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" id="usuarioNombreAdd" name="usuarioNombre">
                                <p class="text-center w-100 text-danger" id="errorUsuarioNombreAdd"></p>

                            </div>

                            <div class="form-group">
                                <label for="nombre">Apellidos</label>
                                <input type="text" class="form-control" id="usuarioApellidosAdd" name="usuarioApellidos">
                                <p class="text-center w-100 text-danger" id="errorUsuarioApellidosAdd"></p>

                            </div>
                            <div class="form-group">
                                <label for="nombre">Email</label>
                                <input type="text" class="form-control" id="usuarioEmailAdd" name="usuarioEmail">
                                <p class="text-center w-100 text-danger" id="errorUsuarioEmailAdd"></p>

                            </div>

                            <div class="form-group">
                                <label for="nombre">Dirección</label>
                                <input type="text" class="form-control" id="usuarioDireccionAdd" name="usuarioDireccion">
                                <p class="text-center w-100 text-danger" id="errorUsuarioDireccionAdd"></p>

                            </div>

                            <div class="form-group">
                                <label for="nombre">Teléfono</label>
                                <input type="text" class="form-control" id="usuarioTelefonoAdd" name="usuarioTelefono">
                                <p class="text-center w-100 text-danger" id="errorUsuarioTelefonoAdd"></p>

                            </div>


                            <div class="form-group">
                                <label for="nombre">Contraseña</label>
                                <input type="text" class="form-control" id="usuarioContrasenaAdd" name="usuarioContrasena">
                                <p class="text-center w-100 text-danger" id="errorUsuarioContrasenaAdd"></p>

                            </div>



                            <div class="form-group">
                                <label for="serie">Rol</label>
                                <select id="rolAdd"class="form-control" name="usuarioRol">

                                </select>
                            </div>







                        </div>
                        <div class="modal-footer">
                            <input type="submit" id="btnAnadirUsuario" class="btn btn-primary" value="Añadir usuario"/>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>















        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.10.2/umd/popper.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/js/bootstrap.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>

        <script src="assets/js/admin.js"></script>
        <c:if test="${not empty mensaje}">

            <script>
                        alert("${mensaje}");
            </script>
        </c:if>


    </body>

</html>