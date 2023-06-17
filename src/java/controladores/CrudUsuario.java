package controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;

/**
 *
 * @author Angel
 */
@WebServlet(name = "CrudUsuario", urlPatterns = {"/CrudUsuario"})
public class CrudUsuario extends HttpServlet {

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
            Usuario usuario;
            UsuarioDAO udao = new UsuarioDAO();
            if (accion.equals("cambiarEstado")) {
                int idUsuario = Integer.parseInt(request.getParameter("id"));
                usuario = udao.getUsuarioPorId(idUsuario);
                int estado;
                //Si es 1 es que si, 0 es que no
                if (usuario.getEsBaja() == 1) {
                    estado = 0;
                    mensaje = "El usuario '" + usuario.getEmail() + "' ha sido dada de alta correctamente";
                } else {
                    estado = 1;
                    mensaje = "El usuario '" + usuario.getEmail() + "' ha sido dada de baja correctamente";
                }
                udao.cambiarEstado(idUsuario, estado);
            } else {

                String nombre = request.getParameter("usuarioNombre");
                String apellidos = request.getParameter("usuarioApellidos");
                String email = request.getParameter("usuarioEmail").toLowerCase();
                String direccion = null;
                if(!request.getParameter("usuarioDireccion").equals("")){
                    direccion=request.getParameter("usuarioDireccion").toLowerCase();
                }
                String telefono = request.getParameter("usuarioTelefono");
                String rol = request.getParameter("usuarioRol");

                System.out.println(request.getParameter("usuarioEmailOld"));
                System.out.println(email);
                
                if (request.getParameter("usuarioEmailOld").equalsIgnoreCase(email) || !udao.usuarioExiste(email)) {

                    if (accion.equals("modificar")) {
                        int id = Integer.parseInt(request.getParameter("usuarioId"));

                        Usuario nuevo = new Usuario(id, nombre, apellidos, "", email, direccion, telefono, rol, 0);
                        udao.actualizarUsuario(nuevo);
                        mensaje = "Los datos del usuario han sido actualizados satisfactoriamente.";
                    } else {
                        String contra = request.getParameter("usuarioContrasena");

                        usuario = new Usuario(nombre, apellidos, contra, email, direccion, telefono, rol, 0);
                        udao.anadirUsuario(usuario);
                        mensaje = "Se ha añadido al proveedor '" + nombre + "'";
                    }
                } else {
                    mensaje = "Error, ya existe una cuenta con el email " + email + ". Revise las cuentas creadas e intentelo de nuevo.";

                }
            }
                udao.cerrarConexion();

                request.getSession().setAttribute("mensaje", mensaje);
                request.getSession().setAttribute("tablaMostrada", "Usuario");
                response.sendRedirect("admin");
            
        }

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
