<%-- 
    Document   : login
    Created on : 26 abr 2023, 19:46:26
    Author     : Angel
--%>

<%
    //Inicializamos la página a la que se va a ir 
    String redirect = request.getParameter("redirect");
    if (redirect == null) {
        redirect = "index.jsp";
    }

    request.setAttribute("redirect", redirect);

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="icon" type="image/x-icon" href="assets/images/logomini.jpg">

        <title>Figurama - Registro</title>

        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <link href="assets/css/styles.css" rel="stylesheet">
        <link href="assets/css/login.css" rel="stylesheet">

        <script src="assets/js/register.js"></script>

    </head>
    <body>
        <div class="content">
        <div class="container-fluid" >
            <div class="row mh-100vh">
                <div class="col-10 col-sm-8 col-md-6 col-lg-6 offset-1 offset-sm-2 offset-md-3 offset-lg-0 align-self-center d-lg-flex align-self-lg-stretch p-5 rounded rounded-lg-0 my-5 my-lg-0"  style="background-color: rgb(235, 216, 214)" 
                     id="login-block">
                    <div class="m-auto w-lg-75 w-xl-50">
                        <a href="index.jsp"><img src="assets/images/logo.jpg" alt="alt" class="img-fluid d-flex"/></a>
                        <form class="align-items-center" action="Registrar" method="post">
                            <div class="form-group mb-3"><label class="form-label">Email</label><input class="form-control" id="email" type="email" name="email" value="${email}"></div>
                            <p class="text-center w-100 text-danger" id="errorEmail"></p>
                            <div class="form-group mb-3"><label class="form-label">Nombre</label><input class="form-control" id="nombre" type="text" name="nombre" value="${nombre}"></div>
                            <p class="text-center w-100 text-danger" id="errorNombre"></p>
                            <div class="form-group mb-3"><label class="form-label">Apellidos</label><input class="form-control" id="apellidos" type="text" name="apellidos" value="${apellidos}"></div>
                            <p class="text-center w-100 text-danger" id="errorApellidos"></p>
                            <div class="form-group mb-3"><label class="form-label">Teléfono</label><input class="form-control" id="telefono" type="text" name="telefono" value="${telefono}"></div>
                            <p class="text-center w-100 text-danger" id="errorTelefono"></p>

                            <div class="form-group mb-3 password-container">
                                <label class="form-label">Contraseña</label>
                                <div class="input-group">
                                    <input class="form-control" id="contra" type="password" name="contra" value="${contra}">
                                    <i id="togglePassword" class="fas fa-eye toggle-password-icon"></i>
                                </div>
                            </div>
                            <p class="text-center w-100 text-danger" id="errorContra"></p>


                            <!-- Mensaje de error-->
                            <c:if test="${not empty error}">
                                <p class="mt-3 text-center text-danger">${error}</p>
                            </c:if>

                            <div class="d-flex flex-column">
                                <!--Aquí se enviarán los datos a un servlet que compruebe que los datos son
                                correctos , en caso de que no lo sea vuelve a esta página indicando algún mensaje de error-->
                                <input class="btn btn-primary mt-2 p-2" type="submit" id="enviar" value="Registrarme" onclick=hayError = false />
                                <a class="btn btn-primary mt-2 p-2" href="login.jsp">¿Ya tienes cuenta? Acceder</a>

                    </div>
                                                    </form>

                </div>
            </div>

            <!-- Div que contiene la imagen de la zona derecha, esta imagen se pone fixed al hacerse responsive  -->


            <div class="col-lg-6 d-flex align-items-end" id="bg-block" 
                 <div style='background-image:url("assets/images/bg.jpg");background-size:cover;background-position:center center;background-repeat:no-repeat;'>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</div>
</body>
</html>
