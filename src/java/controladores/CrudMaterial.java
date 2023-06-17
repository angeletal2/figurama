/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.dao.figura.MaterialDAO;
import modelo.entidades.figura.Material;

/**
 *
 * @author Angel
 */
@WebServlet(name = "CrudMaterial", urlPatterns = {"/CrudMaterial"})
@MultipartConfig
public class CrudMaterial extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
          String mensaje = "";
        String accion = request.getParameter("accion");
        if (accion == null) {
            response.sendRedirect("index.jsp");
            return;
        } else {
            Material material;
            MaterialDAO mdao = new MaterialDAO();

            if (accion.equals("cambiarEstado")) {
                int idMaterial = Integer.parseInt(request.getParameter("id"));
               material = mdao.getMaterialPorId(idMaterial);
                int estado;
                //Si es 1 es que si, 0 es que no
                if (material.getEsBaja() == 1) {
                    estado = 0;
                    mensaje = "El material '" + material.getNombre() + "' ha sido dado de alta correctamente";
                } else {
                    estado = 1;
                    mensaje = "El material '" + material.getNombre() + "' ha sido dado de baja correctamente";
                }
                mdao.cambiarEstado(idMaterial, estado);
            } else {

                Part nombrePart = request.getPart("materialNombre");
                String nombre = "";
                if (nombrePart != null) {
                    try ( InputStream inputStream = nombrePart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        nombre = result.toString(StandardCharsets.UTF_8.name());
                    }
                }
               

                       
               if (accion.equals("anadir")) {

                    if (!mdao.materialExiste(nombre)) {

                        material = new Material(nombre, 0);
                        mdao.anadirMaterial(material);
                        mensaje = "Se ha añadido el material '" + nombre + "'";
                    } else {
                        mensaje = "Error, no se ha podido crear el material de nombre '" + nombre + "' debido a que ya existe un material que se llama así. Por favor, revise los materiales existentes y aségurese de ponerle un nombre que no exista.";
                    }
                }

            }
            mdao.cerrarConexion();
        }

        request.getSession().setAttribute("mensaje", mensaje);
        request.getSession().setAttribute("tablaMostrada", "Material");
        response.sendRedirect("admin");
    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                request.setCharacterEncoding("UTF-8");

        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                request.setCharacterEncoding("UTF-8");

        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
