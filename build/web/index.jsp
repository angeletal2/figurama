<%-- 
    Document   : index
    Created on : 26 abr 2023, 17:18:56
    Author     : Angel
--%>

<%@page import="modelo.dao.figura.ProveedorDAO"%>
<%@page import="modelo.entidades.CargarDatos"%>
<%@page import="modelo.entidades.figura.Figura"%>
<%@page import="modelo.dao.figura.FiguraDAO"%>
<%@page import="java.util.List"%>
<%@page import="modelo.entidades.Serie"%>
<%@page import="modelo.dao.SerieDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Cargar datos -->
<%@include file="datos.jsp"%>
<%    FiguraDAO fdao = new FiguraDAO();
    ProveedorDAO pdao = new ProveedorDAO();
    request.setAttribute("listaNovedades", fdao.getListaFigurasMasRecientes());
    request.setAttribute("listaVentas", fdao.getListaFigurasMasVendidas());
    request.setAttribute("listaOfertas", fdao.getListaFigurasEnOferta());
    request.setAttribute("listaProveedores", pdao.getListaProveedores());
    fdao.cerrarConexion();
    pdao.cerrarConexion();
    

    if(request.getSession().getAttribute("mensaje")!=null){
    request.setAttribute("mensajeCerrar", request.getSession().getAttribute("mensaje"));
    request.getSession().removeAttribute("mensaje");
    }
%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Figurama - Inicio</title>
        <link rel="icon" type="image/x-icon" href="assets/images/logomini.jpg">

        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="assets/css/catalogo.css" rel="stylesheet">

        <link href="assets/css/styles.css" rel="stylesheet">
        <link href="assets/css/filtro.css" rel="stylesheet">

    </head>

    <body>
        
        <c:if test="${!empty mensajeCerrar}">
            <script>
                alert("${mensajeCerrar}");
            </script>
        </c:if>
        <header>
            <nav class="navbar navbar-expand-md navbar-light fixed-top head">
                <div class="container-fluid">
                    <a class="navbar-brand" href="index.jsp">
                        <img src="assets/images/logomini.jpg" alt="alt" class="logo-img" title="Volver a inicio">
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
                                <a class="nav-link active" href="index.jsp">Inicio</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="catalogo.jsp">Catálogo</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="franquicias">Franquicias</a>
                            </li>

                            <!-- Si la cesta está vacía se muestra 0, si tiene algo se muestra el valor -->
                            <li class="nav-item">
                                <c:if test="${empty sessionScope.cesta}">
                                    <a class="nav-link" href="cesta.jsp">Cesta (0)</a>
                                </c:if>
                                <c:if test="${!empty sessionScope.cesta}">
                                    <a class="nav-link" href="cesta.jsp">Cesta (<c:out value="${sessionScope.cesta.tamano}"/>)</a>
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
                                        <a class="dropdown-item" href="deseos.jsp">Ver mis favoritos</a>
                                        <a class="dropdown-item" href="pedidos">Ver mis pedidos</a>
                                        <a class="dropdown-item" href="editarPerfil.jsp">Editar perfil</a>
                                            <c:if test="${sessionScope.usuario.rol eq 'Admin' or sessionScope.usuario.rol eq 'AdminMaestro'}">
                                            <a class="dropdown-item" href="admin">Volver al menú de administración</a>
                                        </c:if>
                                        <a class="dropdown-item" href="CerrarSesion">Cerrar sesión</a>
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
                    <ul class="text-center" id="lista-resultados"></ul>

                </div>


            </nav>

        </header>


        <div id="myCarousel" class="carousel" style="margin-top:5rem" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="assets/images/slider1.jpg" class="d-block w-100" alt="Slider 1">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/slider2.jpg" class="d-block w-100" alt="Slider 2">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/slider3.jpg" class="d-block w-100" alt="Slider 3">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/slider4.jpg" class="d-block w-100" alt="Slider 4">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/slider5.jpg" class="d-block w-100" alt="Slider 5">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </button>
        </div>

        <div class="text-center mt-4">
            <h1 class="jumbotron-heading">Figurama</h1>
            <p class="lead">¡La tienda en línea de figuras más completa! Encuentra la figura que estás buscando,
                desde las más económicas hasta ediciones limitadas de alta calidad.</p>
            <a href="catalogo.jsp" class="btn btn-primary my-2">Ver catálogo</a>
        </div>



        <div class="w-100 p-3 pt-0 mt-4">

            <div class="product-list p-4 text-center" style="border: 3px solid lightslategrey; border-radius: 3px">
                <h1 class="text-center">FIGURAS MÁS RECIENTES</h1>

                <div id="contenedor-figuras" class="flex-container product-list-wrapper clearfix grid simple columns-6 mt-5" >


                    <c:forEach items="${listaNovedades}" var="figura">


                        <article class="product-miniature">

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
                                    <c:if test="${figura.porcentajeDescuento > 0}">
                                        <span class="regular-price">${figura.precio}€</span>
                                    </c:if>
                                </div>
                            </div>  


                        </article>  

                    </c:forEach>



                </div>

                <form action="FiltradoIndex" method="POST">
                    <input type="hidden" name="ordenI" value="recientes"/>
                    <input type="submit" class="btn btn-primary my-2" value="Ver todas las novedades"/>
                </form>

            </div></div>










        <div class="w-100 p-3 pt-0 mt-4">

            <div class="product-list p-4 text-center" style="border: 3px solid lightslategrey; border-radius: 3px">
                <h1 class="text-center">FIGURAS EN OFERTA</h1>

                <div id="contenedor-figuras" class="flex-container product-list-wrapper clearfix grid simple columns-6 mt-5" >


                    <c:forEach items="${listaOfertas}" var="figura">


                        <article class="product-miniature">

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
                                    <c:if test="${figura.porcentajeDescuento > 0}">
                                        <span class="regular-price">${figura.precio}€</span>
                                    </c:if>
                                </div>
                            </div>  


                        </article>  

                    </c:forEach>



                </div>
                <form action="FiltradoIndex" method="POST">
                    <input type="hidden" name="ordenI" value="ofertas"/>
                    <input type="submit" class="btn btn-primary my-2" value="Ver todas las ofertas"/>
                </form>

            </div></div>


        <div class="w-100 p-3 pt-0 mt-4">

            <div class="product-list p-4 text-center" style="border: 3px solid lightslategrey; border-radius: 3px">
                <h1 class="text-center">FIGURAS MÁS VENDIDAS</h1>

                <div id="contenedor-figuras" class="flex-container product-list-wrapper clearfix grid simple columns-6 mt-5" >


                    <c:forEach items="${listaVentas}" var="figura">


                        <article class="product-miniature">

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
                                    <c:if test="${figura.porcentajeDescuento > 0}">
                                        <span class="regular-price">${figura.precio}€</span>
                                    </c:if>
                                </div>
                            </div>  


                        </article>  

                    </c:forEach>



                </div>

                <a href="catalogo.jsp" class="btn btn-primary my-2">Ver todas</a>


            </div></div>


        <div class="text-center mt-3">
            <h1 class="mt-3">Nuestros proveedores</h1>

            <div class="flex-container mb-3 mt-3">

                <c:forEach items="${listaProveedores}" var="proveedor">
                    <div class="flex-item proveedores">
                        <img src="assets/images/proveedores/${proveedor.url}" alt="Imagen 1" />
                    </div>
                </c:forEach>
            </div>
        </div>

        <%@include file="footer.jsp"%>


        <script src="assets/js/filtro.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.4.0/js/bootstrap.bundle.min.js"></script>


    </body>
</html>
