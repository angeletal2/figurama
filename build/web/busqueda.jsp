<%-- 
    Document   : catalogo
    Created on : 4 jun 2023, 9:57:25
    Author     : Angel
--%>

<%@page import="modelo.dao.figura.FiguraDAO"%>
<%@page import="modelo.entidades.CargarDatos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Cargar datos -->
<%@include file="datos.jsp"%>

<%    FiguraDAO fdao = new FiguraDAO();
    String filtro = (String) request.getAttribute("filtro");
    request.setAttribute("precioMinimo", fdao.getPrecioMinimo(filtro));
    request.setAttribute("precioMaximo", fdao.getPrecioMaximo(filtro));
    request.setAttribute("filtro", filtro);
    fdao.cerrarConexion();
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Figurama - Búsqueda</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="../assets/images/logomini.jpg">

        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <link rel="stylesheet" href="../assets/css/styles.css"/>
        <link rel="stylesheet" href="../assets/css/filtro.css"/>
        <link rel="stylesheet" href="../assets/css/catalogo.css"/>

    </head>
    <body>

        <header>
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
                                    <a class="nav-link" href="../cesta.jsp">Cesta (0)</a>
                                </c:if>
                                <c:if test="${!empty sessionScope.cesta}">
                                    <a class="nav-link" href="../cesta.jsp">Cesta (<c:out value="${sessionScope.cesta.tamano}"/>)</a>
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
                    <ul class="text-center doble doble2" id="lista-resultados"></ul>

                </div>


            </nav>

        </header>

        <div class="content">

            <section id="wrapper" class="l-wrapper mt-5 pt-5" >
                <div class="row">
                    <div id="left-column" class="left-column col-12 col-lg-3">
                        <div class="block-categories visible--desktop">
                            <h5>BÚSQUEDA</h5>
                        </div>
                        <div id="_desktop_search_filters_wrapper" class="visible--desktop">
                            <div id="search_filter_controls" class="visible--mobile">
                                <span id="_mobile_search_filters_clear_all"></span>
                            </div>
                            <div id="search_filters" class="search_filters">
                                <p class="text-uppercase h6 visible--desktop">Filtrar por:</p><br>
                                <section class="facet__block">
                                    <div class="facet__header">
                                        <p class="facet__title">Franquicia</p>
                                    </div>

                                    <div id="franquicias" class="collpase--facet collapse facet_list">
                                        <c:forEach items="${sessionScope.franquicias}" var="franquicia">


                                            <div class="custom-control custom-checkbox">
                                                <input id="franquicia${franquicia.id}" value="${franquicia.nombre}"  type="checkbox" class="custom-control-input serie">
                                                <label class="custom-control-label" for="franquicia${franquicia.id}">
                                                    <span class="color__label">${franquicia.nombre}
                                                    </span>
                                                </label>
                                            </div>


                                        </c:forEach>

                                    </div>
                                </section>

                                <section class="facet__block">
                                    <div class="facet__header">
                                        <p class="facet__title">Precio</p>
                                    </div>
                                    <div class="price-range-container ms-3">
                                        <p style="color:#ff6133" class="mb-1">
                                            De 
                                            <input type="number" style="margin-left:4px"  id="precio-minimo" value="${precioMinimo}" min="${precioMinimo}" step="0.1" onchange="validarPrecioMin()"> €
                                            <br><span class="ms-2">(Mínimo: <span id="labelMinimo">${precioMinimo}</span>€)</span>
                                        </p>
                                        <p style="color:#ff6133">
                                            &nbsp;a&nbsp;
                                            <input style="margin-left:6px"value="${precioMaximo}" type="number" id="precio-maximo" max="${precioMaximo}" step="0.1" onchange="validarPrecioMax()"> €
                                            <br><span class="ms-2">(Máximo: <span id="labelMaximo">${precioMaximo}</span>€)</span>

                                        </p>

                                    </div>
                                </section>

                                <section class="facet__block">
                                    <div class="facet__header">
                                        <p class="facet__title">Proveedor</p>
                                    </div>
                                    <div id="proveedores" class="collpase--facet collapse facet_list">


                                        <c:forEach items="${sessionScope.proveedores}" var="proveedor">
                                            <div class="custom-control custom-checkbox">
                                                <input id="proveedor${proveedor.id}" value="${proveedor.nombre}" type="checkbox" class="custom-control-input proveedor">
                                                <label class="custom-control-label" for="proveedor${proveedor.id}">
                                                    <span class="color__label">${proveedor.nombre}
                                                    </span>
                                                </label>
                                            </div>
                                        </c:forEach> 

                                    </div>
                                </section>

                            </div>
                        </div>
                    </div>

                    <div id="content-wrapper" class="left-column col-12 col-lg-9">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <a itemprop="item" href="../index.jsp">
                                    <span itemprop="name">Inicio</span>
                                </a>
                            </li>
                            <li class="breadcrumb-item active">
                                <span itemprop="name">Búsqueda</span>
                            </li>
                        </ol>
                        <div class="block-category card-block">
                            <div id="_desktop_category_header">
                                <h1 class="h1">RESULTADOS DE LA BÚSQUEDA DE: "<span id="valorFiltro">${filtro}</span>"</h1>
                            </div>
                        </div>
                        <div class="_mobile_category_header"></div>
                        <div>
                            <div id="js-product-list-top" class="row products-selection my-3">
                                <div class="sort_container">
                                    <div class="sort-by-row">
                                        <div class="total-products">
                                            <p class="product__quantity">Hay <span id="cantidadResultados">${sessionScope.figuras.size()} productos.</span></p>
                                        </div>
                                        <div class="form-inline">
                                            <label class="form-control-label sort-label">Resultados por página:</label>
                                            <div class="sort-select dropdown js-dropdown">

                                                <select class="custom-select ml-sm-2 ms-2" id="select-paginacion">
                                                    <option value=1" selected="selected">1</option>
                                                    <option value="5">5</option>
                                                    <option value="15">15</option>
                                                    <option value="30">30</option>
                                                    <option value="50">50</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-inline">
                                            <label for="select-orden" class="">Ordenar por:</label>
                                            <select class="custom-select ml-sm-2 order ms-2" id="select-orden">
                                                <option value="relevancias" selected="selected">Relevancia</option>
                                                <option value="ofertas">Ofertas primero</option>
                                                <option value="recientes">Más recientes</option>
                                                <option value="antiguo">Más antigüas</option>
                                                <option value="az">Nombre, A a Z</option>
                                                <option value="za">Nombre, Z a A</option>
                                                <option value="preciomenosamas">Precio: de más bajo a más alto</option>
                                                <option value="preciomasamenos">Precio: de más alto a más bajo</option>
                                            </select>

                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <div class="w-100">

                                <div class="product-list">
                                    <div id="contenedor-figuras" class="product-list-wrapper clearfix grid simple columns-6 mt-5" >




                                    </div>
                                </div></div>

                            <div class="pagination-container" id="paginacion">

                            </div>


                        </div>
                    </div>
                </div>
                <div id="js-product-list-bottom" class="pb-3">
                    <div id="js-product-list-bottom"></div>
                </div>
            </section>
        </div>
        <%@include file="footer.jsp"%>

        <script src="../assets/js/filtro.js"></script>
        <script src="../assets/js/catalogo.js"></script>


        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.4.0/js/bootstrap.bundle.min.js"></script>


    </body>
</html>
