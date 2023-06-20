<%-- 
    Document   : cesta
    Created on : 28 may 2023, 21:11:17
    Author     : Angel
--%>

<%@page import="modelo.dao.CestaDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
    
if(request.getSession().getAttribute("usuario")==null){
response.sendRedirect("index.jsp");
return;
    }
     
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Figurama - Editar Perfil</title>
        <link rel="icon" type="image/x-icon" href="assets/images/logomini.jpg">

        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="assets/css/cesta.css">    
        <link rel="stylesheet" href="assets/css/styles.css">
        <link rel="stylesheet" href="assets/css/filtro.css">


        <style>

            .form-group {
                margin-bottom: 20px;
            }

            .label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }

            .input {
                width: 100%;
                padding: 10px;
                font-size: 16px;
                border-radius: 4px;
                border: 1px solid #ccc;
            }



            #btnEditarUsuario {
                margin-top: 10px;
            }

            form{
                border:2px solid #dee2e6!important ;
                border-radius: 5px;
            }



        </style>




    </head>
    <body>


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
                                <a class="nav-link" href="index.jsp">Inicio</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="catalogo.jsp">Catálogo</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="franquicias">Franquicias</a>
                            </li>

                            <!-- Si la cesta está vacía se muestra 0, si tiene algo se muestra el valor -->
                            <li class="nav-item">
                                <c:choose>

                                    <c:when test="${empty sessionScope.cesta || empty sessionScope.cesta.articulos}">
                                        <a class="nav-link" id="botonAccesoCesta" href="cesta.jsp">Cesta (0)</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="nav-link" href="cesta.jsp" id="botonAccesoCesta">Cesta (<c:out value="${sessionScope.cesta.tamano}"/>)</a>
                                    </c:otherwise>

                                </c:choose>
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
                                        <a id="linkAcceso" class="d-none" href="#">Acceder</a>

                                        <a class="dropdown-item" href="#">Editar perfil</a>
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
        <div class="content">

            <ol class="breadcrumb mt-5 pt-5 me-5">
                <li class="breadcrumb-item">
                    <a itemprop="item" href="index.jsp">
                        <span itemprop="name">Inicio</span>
                    </a>
                </li>
                <li class="breadcrumb-item active">
                    <span itemprop="name">Editar Perfil</span>
                </li>
            </ol>

            <div class="container">
                <div class="empty-cart text-center">
                    <h1 class="mb-3">Perfil de ${sessionScope.usuario.nombre}</h1>
                    <div class="row">
                        <div class="col-md-6 mb-4">

                            <form action="CrudUsuario?accion=modificarDatos" method="POST" id="datosUsuario" class="mb-4  p-3">
                                <h4>Editar datos</h4>
                                <div class="form-group custom-width">
                                    <label for="usuarioNombre">Nombre</label>
                                    <input type="text" id="usuarioNombreAdd" name="usuarioNombre" value="${sessionScope.usuario.nombre}" class="form-control">
                                    <p class="text-danger" id="errorUsuarioNombreAdd"></p>
                                </div>

                                <div class="form-group custom-width">
                                    <label for="usuarioApellidos">Apellidos</label>
                                    <input type="text" id="usuarioApellidosAdd" name="usuarioApellidos"value="${sessionScope.usuario.apellidos}" class="form-control">
                                    <p class="text-danger" id="errorUsuarioApellidosAdd"></p>
                                </div>

                                <div class="form-group custom-width">
                                    <label for="usuarioEmail">Email</label>
                                    <input type="email" id="usuarioEmailAdd" name="usuarioEmail"value="${sessionScope.usuario.email}" class="form-control">
                                    <p class="text-danger" id="errorUsuarioEmailAdd"></p>
                                </div>

                                <div class="form-group custom-width">
                                    <label for="usuarioTelefono">Teléfono</label>
                                    <input type="tel" id="usuarioTelefonoAdd" name="usuarioTelefono" value="${sessionScope.usuario.telefono}"class="form-control">
                                    <p class="text-danger" id="errorUsuarioTelefonoAdd"></p>
                                </div>



                                <input type="submit" id="btnEditarUsuario" class="btn btn-primary" value="Actualizar datos"/>
                            </form>
                        </div>



                        <div class="col-md-6"> 
                            <form action="CrudUsuario?accion=modificarDireccion" method="POST" id="direccionUsuario" class="mb-4  p-3 ">
                                <h4>Editar dirección</h4>

                                <div class="form-group  custom-width">
                                    <label for="pais">País</label>
                                    <input type="text" id="pais" name="pais" value="${sessionScope.usuario.pais}" class="form-control">
                                    <p class="text-danger" id="errorPais"></p>
                                </div>


                                <div class="form-group custom-width">
                                    <label for="provincia">Provincia</label>
                                    <input type="text" id="provincia" name="provincia" value="${sessionScope.usuario.provincia}" class="form-control">
                                    <p class="text-danger" id="errorProvincia"></p>
                                </div>


                                <div class="form-group custom-width">
                                    <label for="pais">Municipio</label>
                                    <input type="text" id="municipio"value="${sessionScope.usuario.municipio}" name="municipio"  class="form-control">
                                    <p class="text-danger" id="errorMunicipio"></p>
                                </div>


                                <div class="form-group custom-width">
                                    <label for="pais">Calle y número</label>
                                    <input type="text" id="calle" value="${sessionScope.usuario.calle}" name="calle" class="form-control">
                                    <p class="text-danger" id="errorCalle"></p>
                                </div>
                                <input type="submit" id="btnEditarUsuario" class="btn btn-primary" value="Actualizar dirección"/>
                            </form>

                        </div>
                        <div class="d-flex justify-content-center align-items-center">

                            <div class="col-md-4 password-container"> 
                                <form action="CrudUsuario?accion=modificarContra" method="POST" id="contraUsuario" class="mb-4  p-3">
                                    <h4>Editar contraseña</h4>
                                    <span id="contrasenaPrev" class="d-none">${sessionScope.usuario.contra}</span>
                                    <div class="form-group">
                                        <label for="usuarioContrasenaActual">Contraseña actual</label>
                                        <div class="input-group align-items-center">
                                            <input type="password" id="contrasenaActual" class="form-control form-control-lg"/>
                                            <i id="togglePassword" class="fas fa-eye toggle-password-icon"></i>
                                        </div>   
                                        <p class="text-danger" id="errorContrasenaActual"></p>

                                    </div>



                                    <div class="form-group">
                                        <label for="usuarioContrasenaNueva">Contraseña nueva</label>
                                        <div class="input-group align-items-center">
                                            <input type="password" id="contrasenaNueva" name="contrasenaNueva" class="form-control form-control-lg"/>
                                            <i id="togglePassword2" class="fas fa-eye toggle-password-icon"></i>
                                        </div>   
                                        <p class="text-danger" id="errorContrasenaNueva"></p>

                                    </div>


                                    <input type="submit" id="btnEditarUsuario" class="btn btn-primary" value="Actualizar contraseña"/>
                                </form>
                            </div></div>
                    </div>
                </div></div>
        </div>
        <%@include file="footer.jsp"%>




        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.4.0/js/bootstrap.bundle.min.js"></script>



        <script src="assets/js/filtro.js"></script>
        <script src="assets/js/editarUsuario.js"></script>



    </body>
</html>
