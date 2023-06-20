<%-- 
    Document   : login
    Created on : 26 abr 2023, 17:46:26
    Author     : Angel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    HttpSession sesion = request.getSession();
    //Inicializamos la página a la que se va a ir 
    String redirect = request.getParameter("redirect");

    if (redirect == null) {
        redirect = "index.jsp";
    }

    sesion.setAttribute("redirect", redirect);
    if (sesion.getAttribute("cuentaCreada") != null) {
        request.setAttribute("cuentaCreada", sesion.getAttribute("cuentaCreada"));
        sesion.removeAttribute("cuentaCreada");
    }

%>


<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="icon" type="image/x-icon" href="assets/images/logomini.jpg">

        <title>Figurama - Login</title>


        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="assets/css/styles.css" rel="stylesheet">
        <link href="assets/css/login.css" rel="stylesheet">
    </head>
    <body>

        <div class="container-fluid" >
            <div class="row mh-100vh">
                <div class="col-10 col-sm-8 col-md-6 col-lg-6 offset-1 offset-sm-2 offset-md-3 offset-lg-0 align-self-center d-lg-flex align-self-lg-stretch p-5 rounded rounded-lg-0 my-5 my-lg-0"  style="background-color: rgb(235, 216, 214)" 
                     id="login-block">
                    <div class="m-auto w-lg-75 w-xl-50">
                        <div><a href="${sessionScope.redirect}">Volver</a></div>

                        <a href="index.jsp"><img src="assets/images/logo.jpg" alt="alt" class="img-fluid d-flex"/></a>
                        <form class="align-items-center" action="login" method="post">
                            <div class="form-group mb-3"><label class="form-label">Email</label><input class="form-control" type="email" name="email" value="${email}"></div>
                            <div class="form-group mb-3 password-container">
                                <label class="form-label">Contraseña</label>
                                <div class="input-group">
                                    <input class="form-control" id="contra" type="password" name="contra">
                                    <i id="togglePassword" class="fas fa-eye toggle-password-icon"></i>
                                </div>
                            </div>
                            <p class="text-center w-100 text-danger" id="errorContra"></p>




                            <!-- Mensaje de error -->
                            <c:if test="${not empty error}">
                                <p class="mt-3 text-center" style="color: red">${error}</p>
                            </c:if> 

                            <div class="d-flex flex-column">
                                <input class="btn btn-primary mt-2 p-2" type="submit" value="Acceder"/>
                        </form>

                        <a class="btn btn-primary mt-2 p-2" href="register.jsp">¿Eres nuevo/a? Regístrate aquí</a>
                        <a class="link-primary text-center mt-2" style="cursor: pointer" onclick="alert('Para recuperar su contraseña, envíe un email desde su correo registrado al siguiente correo explicando su situación o llame/escriba al siguiente teléfono:\n\nEmail de contacto: support@figurama.com\nTeléfono de contacto: 644826846')">¿Ha olvidado su contraseña?</a>


                    </div>
                </div>
            </div>

            <!-- Div que contiene la imagen de la zona derecha, esta imagen se pone fixed al hacerse responsive  -->
            <div class="col-lg-6 d-flex align-items-end" id="bg-block" 
                 <div style='background-image:url("assets/images/bg.jpg");background-size:cover;background-position:center center;background-repeat:no-repeat;'>
            </div>

        </div>

        <script>
            var togglePassword = document.getElementById("togglePassword");
            var passwordInput = document.getElementById("contra");

            togglePassword.addEventListener('click', function () {
                if (passwordInput.type === "password") {
                    passwordInput.type = "text";
                } else {
                    passwordInput.type = "password";
                }
                togglePassword.classList.toggle("fa-eye-slash");
            });

            // Agrega el siguiente código para evitar que el botón desaparezca cuando está enfocado.
            passwordInput.addEventListener('focus', function () {
                togglePassword.style.display = "inline-block";
            });

            passwordInput.addEventListener('blur', function () {
                togglePassword.style.display = "inline-block";
            });</script>

    </div>
    <c:if test="${not empty cuentaCreada}">
        <script>alert("Cuenta creada satisfactoriamente");</script>
    </c:if>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.4.slim.js" integrity="sha256-dWvV84T6BhzO4vG6gWhsWVKVoa4lVmLnpBOZh/CAHU4=" crossorigin="anonymous"></script>

</body>
</html>
