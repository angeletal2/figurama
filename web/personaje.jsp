<%-- 
    Document   : personaje
    Created on : 8 jun 2023, 20:56:23
    Author     : Angel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Figurama - <c:out value="${nombre}"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="../../assets/images/logomini.jpg">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="../../assets/css/filtro.css" rel="stylesheet">
        <link rel="stylesheet" href="../../assets/css/catalogo.css"/>
        <link href="../../assets/css/styles.css" rel="stylesheet">

    </head>
    <body>

        <header>
            <nav class="navbar navbar-expand-md navbar-light fixed-top head">
                <div class="container-fluid">

                    <a class="navbar-brand" href="../../index.jsp">
                        <img src="../../assets/images/logomini.jpg" alt="alt" class="logo-img" title="Volver a inicio">
                    </a>

                    <!-- Contenedor búsqueda responsive -->
                    <div id="contenedor-busqueda-responsive">
                        <div class="input-container">
                            <div class="input-wrapper">
                                <input type="text" autocomplete="off" id="input-busqueda-res" placeholder="Buscar figura...">
                                <span id="tooltip-res" class="tooltip">Ingrese al menos 3 caracteres</span>
                            </div>
                            <button id="btn-buscar-res" class="buscarMini">
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
                                <a class="nav-link" href="../../index.jsp">Inicio</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="../../catalogo.jsp">Catálogo</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="../../franquicias">Franquicias</a>
                            </li>

                            <!-- Si la cesta está vacía se muestra 0, si tiene algo se muestra el valor -->
                            <li class="nav-item">
                                <c:if test="${empty sessionScope.cesta}">
                                    <a class="nav-link" href="../../cesta.jsp">Cesta (0)</a>
                                </c:if>
                                <c:if test="${!empty sessionScope.cesta}">
                                    <a class="nav-link" href="../../cesta.jsp">Cesta (<c:out value="${sessionScope.cesta.tamano}"/>)</a>
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
                                        <a class="dropdown-item" href="../../deseos.jsp">Ver mis favoritos</a>
                                        <a class="dropdown-item" href="../../pedidos">Ver mis pedidos</a>
                                        <a class="dropdown-item" href="../../editarPerfil.jsp">Editar perfil</a>
                                            <c:if test="${sessionScope.usuario.rol eq 'Admin' or sessionScope.usuario.rol eq 'AdminMaestro'}">
                                            <a class="dropdown-item" href="../../admin">Volver al menú de administración</a>
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
                                <button id="btn-buscar" class="buscarMini">
                                    <span class="fas fa-search"></span>
                                </button>                          
                            </div>
                        </div>

                    </div>
                    <ul class="text-center triple" id="lista-resultados"></ul>

                </div>


            </nav>

        </header>

        <div class="content">
            <ol class="breadcrumb mt-5 pt-5 me-5">
                <li class="breadcrumb-item">
                    <a itemprop="item" href="../../index.jsp">
                        <span itemprop="name">Inicio</span>
                    </a>
                </li>
                <li class="breadcrumb-item">
                    <a itemprop="item" href="../../franquicias">
                        <span itemprop="name">Franquicias</span>
                    </a>
                </li>
                <li class="breadcrumb-item">
                    <a href="../${serieP}">${serieP}</a>     
                </li>
                <li class="breadcrumb-item active">
                    <a>${nombre}</a>     
                </li>
            </ol>

            <h1 class="text-center">Figuras de <c:out value="${nombre}"/></h1>




            <div class="w-100">

                <div class="product-list p-4">
                    <div id="contenedor-figuras" class="flex-container product-list-wrapper clearfix grid simple columns-6 mt-5" >


                        <c:if test="${not empty figurasP}">  <c:forEach items="${figurasP}" var="figura">


                                <article class="product-miniature">

                                    <div class="product-container product-style pg-onp">
                                        <div class="first-block">
                                            <div class="product-thumbnail">
                                                <a href="../../figura/${figura.nombre}" class="product-cover-link">
                                                    <img src="../../assets/images/figuras/${figura.primeraImagen}" alt="${figura.nombre}" title="${figura.nombre}" class="img-fluid" width="278" height="278">
                                                </a>
                                            </div>
                                        </div>
                                        <p class="product-name" title="${figura.nombre}">
                                            <a href="../../figura/${figura.nombre}">${figura.nombre}</a>
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
                            <div class="text-center">
                             <a class="btn btn-primary mt-3" href="../${serieP}">Volver a personajes</a>
                             </div>
                        </c:if>  
                        <c:if test="${empty figurasP}">     
                            <div class="container">
                    <div class="empty-cart text-center">
                       <h3>Actualmente no hay ninguna figura de este personaje</h3>
                            <a class="btn btn-primary mt-3" href="../${serieP}">Volver a personajes</a>
                    </div>
                </div>
                        </div> 
                        </c:if>
                </div></div>


        </div>





        <%@include file="footer.jsp"%>

        <script src="../../assets/js/filtro.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.4.0/js/bootstrap.bundle.min.js"></script>


    </body>
</html>
