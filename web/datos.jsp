<%@page import="modelo.dao.figura.FiguraDAO"%>
<%@page import="modelo.entidades.CargarDatos"%>
<%
  
    CargarDatos cargar = new CargarDatos(request);
    cargar.cargarFiguras();
    cargar.cargarProveedores();
    cargar.cargarFranquicias();
   

%>