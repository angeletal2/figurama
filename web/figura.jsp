<%-- 
    Document   : figura
    Created on : 13 may 2023, 19:09:32
    Author     : Angel
--%>

<%@page import="java.util.Date"%>
<%@page import="modelo.entidades.figura.Figura"%>
<%@page import="java.util.List"%>
<%@page import="modelo.dao.figura.FiguraDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    if (request.getAttribute("figura") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>${figura.nombre}</title>
        <link rel="icon" type="image/x-icon" href="../assets/images/logomini.jpg">

        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">


        <link href="../assets/css/styles.css" rel="stylesheet">
        <link href="../assets/css/filtro.css" rel="stylesheet">
        <link href="../assets/css/figura.css" rel="stylesheet">
        <link href="../assets/css/catalogo.css" rel="stylesheet">


        <script src="../assets/js/figura.js"></script>
    </head>

    <body>
        <div class="content">
            <header class="mb-5 pb-5">
                <nav class="navbar navbar-expand-md navbar-light fixed-top head">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="../index.jsp">
                            <img src="../assets/images/logomini.jpg" alt="alt" class="logo-img" title="Volver a inicio">
                        </a>

                        <!-- Contenedor búsqueda responsive -->
                        <div id="contenedor-busqueda-responsive">
                            <div class="input-container">
                                <div class="input-wrapper">
                                    <input type="text" autocomplete="off" id="input-busqueda-res" placeholder="Buscar figura...">
                                    <span id="tooltip-res" class="tooltip">Ingrese al menos 3 caracteres</span>
                                </div>
                                <button id="btn-buscar-res">
                                    <span class="fas fa-search"></span>
                                </button>
                            </div>

                        </div>
                        <button class="navbar-toggler collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
                                aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse text-center" id="navbarCollapse">
                            <ul class="navbar-nav ml-auto w-100">
                                <li class="nav-item active">
                                    <a class="nav-link" href="../index.jsp">Inicio</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="../catalogo.jsp">Catálogo</a>
                                </li>

                                <li class="nav-item">
                                    <a class="nav-link" href="../franquicias">Franquicias</a>
                                </li>

                                <!-- Si la cesta está vacía se muestra 0, si tiene algo se muestra el valor -->
                                <li class="nav-item">
                                    <c:if test="${empty sessionScope.cesta}">
                                        <a class="nav-link" id="botonAccesoCesta" href="../cesta.jsp">Cesta (0)</a>
                                    </c:if>
                                    <c:if test="${!empty sessionScope.cesta}">
                                        <a class="nav-link" id="botonAccesoCesta" href="../cesta.jsp">Cesta (<c:out value="${sessionScope.cesta.tamano}"/>)</a>
                                    </c:if>
                                </li>

                                <!-- Si no hay sesión iniciada que de pie a hacerlo, si la hay que muestre sus opciones -->
                                <li class="nav-item dropdown">
                                    <c:if test="${not empty sessionScope.usuario}">
                                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"
                                           aria-haspopup="true" aria-expanded="false">
                                            Cuenta
                                        </a>
                                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                            <a class="dropdown-item" href="#">Ver mis favoritos</a>
                                            <a class="dropdown-item" href="../pedidos">Ver mis pedidos</a>
                                            <a class="dropdown-item" href="#">Editar perfil</a>
                                            <c:if test="${sessionScope.usuario.rol eq 'Admin' or sessionScope.usuario.rol eq 'AdminMaestro'}">
                                                <a class="dropdown-item" href="../admin">Volver al menú de administración</a>
                                            </c:if>
                                            <a class="dropdown-item" href="../CerrarSesion">Cerrar sesión</a>
                                        </div>
                                    </c:if>

                                    <!-- Al clicar en acceder guardamos la URL donde se pulsa para que cuando se loguee vuelva a donde estaba -->
                                    <c:if test="${empty sessionScope.usuario}">

                                        <a id="linkAcceso" class="nav-link" href="#">Acceder</a>
                                    </c:if>

                                </li>
                            </ul>

                            <!-- Contenedor búsqueda NO responsive -->
                            <div id="contenedor-busqueda">
                                <div class="input-container">
                                    <div class="input-wrapper">
                                        <input type="text" autocomplete="off" id="input-busqueda" placeholder="Buscar figura...">
                                        <span id="tooltip" class="tooltip">Ingrese al menos 3 caracteres</span>
                                    </div>
                                    <button id="btn-buscar">
                                        <span class="fas fa-search"></span>
                                    </button>                          
                                </div>
                            </div>
                        </div>
                        <ul class="text-center doble" id="lista-resultados"></ul>

                    </div>
                </nav>
            </header>
            <ol class="breadcrumb me-5">
                <li class="breadcrumb-item"><a href="../index.jsp">Inicio</a></li>
                <li class="breadcrumb-item"><a href="../franquicias">Franquicias</a></li>
                <li class="breadcrumb-item"><a href="../franquicias/${figura.serie.nombre}">${figura.serie.nombre}</a></li>
                <li class="breadcrumb-item"><a href="../franquicias/personajes/${figura.personaje.nombre}">${figura.personaje.nombre}</a></li>
                <li class="breadcrumb-item active">
                    <span itemprop="name">${figura.nombre}</span>
                </li>
            </ol>
            <!-- Inicio producto -->
            <div class="container mb-5 text-center pt-3">
                <div class="row justify-content-center">
                    <div class="col-lg-10 col-md-10">
                        <div class="card">
                            <div class="row p-4">
                                <div class="col-md-5">
                                    <div>
                                        <div class="text-center p-4 zoom-img" onmousemove="zoom(event)" onmouseout="resetZoom(event)">
                                            <img id="main-image" src="../assets/images/figuras/${figura.primeraImagen}" width="100%" style="max-width: 450px;">
                                        </div>
                                        <div class="thumbnail text-center">
                                            <c:forEach items="${figura.imagenes}" var="imagen">
                                                <img style="cursor:pointer" src="../assets/images/figuras/${imagen.url}" width="80px" onclick="cambiarImagen('${imagen.url}')">
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-7">
                                    <div class="product p-2">
                                        <div class="mb-3 d-flex justify-content-between align-items-center">
                                            <h5 class="w-100"><strong><c:out value="${figura.nombre}"/></strong></h5>
                                        </div>
                                        <p class="about"><c:out value="${figura.descripcion}"/></p>


                                        <div class="price-quantity">
                                            <div class="product-price-and-shipping d-flex flex-wrap justify-content-center align-items-center">
                                                <h1 class="price product-price" style="font-size: 1.7rem"><c:out value="${figura.precioConDescuento}"/>€</h1>
                                                <c:if test="${figura.porcentajeDescuento > 0}">  <h1 class="regular-price ms-5"style="font-size: 1.4rem"><c:out value="${figura.precio}"/>€</h1>
                                                </c:if>  </div>
                                            <!-- Si no hay stock, que lo indique -->
                                            <c:if test="${figura.stock eq 0}">     
                                                <span class="text-danger"><strong>No quedan unidades disponibles :(</strong></span>

                                            </div>
                                            <div class="mt-4 align-items-center">
                                                <button class="btn btn-primary text-uppercase mr-2 px-4 disabled">
                                                    <i class="fa fa-shopping-cart"></i> Añadir a la cesta
                                                </button>
                                            </c:if>

                                            <c:if test="${figura.stock gt 0 }">
                                                <div class="quantity text-center">
                                                    <input type="number" id="cantidad" autocomplete="off" class="form-control" min="1" value="1" max="${figura.stock}" oninput="validarCantidad()">
                                                    <span>Cantidad disponible: <c:out value="${figura.stock}"/></span>
                                                    <c:if test="${figura.numeroVentas gt 0 }">
                                                        <span class="ms-3">(<c:out value="${figura.numeroVentas}"/> vendidas)</span></c:if>
                                                    </div>
                                                </div>

                                                <div class="mt-4 align-items-center justify-content-center">

                                                    <button class="btn btn-primary text-uppercase mr-2 px-4" id="btn-cesta" value="${figura.id}"onclick="anadirArticulo()" >
                                                    <i class="fa fa-shopping-cart"></i> Añadir a la cesta
                                                </button>

                                            </c:if>

                                            <!-- Choose para comprobar si esta registrado o no, en caso de no estarlo no permite añadir ni eliminar-->
                                            <c:choose>
                                                <c:when test="${!empty sessionScope.usuario && figura.estaEnListaDeseos(sessionScope.listaDeseos)}">
                                                    <button type="button" id="btn-lista-deseos" value="${figura.id}" onclick="modificarListaDeseos()">
                                                        <i id="btn-deseo" class="fa fa-heart text-danger"></i>
                                                    </button>
                                                </c:when>
                                                <c:when test="${!empty sessionScope.usuario && !figura.estaEnListaDeseos(sessionScope.listaDeseos)}">
                                                    <button type="button" id="btn-lista-deseos" title="Agregar a la lista de deseos"  value="${figura.id}" onclick="modificarListaDeseos()">
                                                        <i id="btn-deseo" class="fa fa-heart"></i>
                                                    </button>
                                                </c:when> 
                                                <c:otherwise>
                                                    <button type="button" id="btn-lista-deseos" title="Agregar a la lista de deseos" value="${figura.id}" onclick="alert('Para añadir a la lista debes tener una cuenta')">
                                                        <i id="btn-deseo" class="fa fa-heart"></i>
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                            <button type="button" id="btn-compartir" title="Compartir" onclick="compartirFigura(location.href)">
                                                <i class="fa fa-clipboard"></i>
                                            </button>

                                        </div>
                                        <div class="mt-4">
                                            <div class="row">
                                                <div class="col-md-5 mb-2">
                                                    <span>Proveedor: <br><c:out value="${figura.proveedor.nombre}"/></span>
                                                </div>
                                                <div class="col-md-5 mb-2">
                                                    <span>Material: <br><c:out value="${figura.material.nombre}"/></span>

                                                </div>
                                            </div>
                                        </div>
                                        <%@ page import="java.util.Date" %>

                                        <c:set var="fechaActual" value="<%= new Date()%>"></c:set>

                                        <c:if test="${figura.fechaSalida > fechaActual}">
                                            <h5 class="mt-3">La figura aún no ha salido a la venta, si la compra en preventa será enviada a partir de la siguiente fecha:<br> <c:out value="${figura.fechaSalida}"/></h5>
                                        </c:if>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Fin producto -->

            </div>
        </div>
        <%@include file="footer.jsp"%>

        <script src="../assets/js/filtro.js"></script>
        <script src="../assets/js/listaDeseos.js"></script>
        <script src="../assets/js/cesta.js"></script>


        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.4.0/js/bootstrap.bundle.min.js"></script>

    </body>

</html>