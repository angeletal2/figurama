package controladores;

import java.io.IOException;
import java.io.PrintWriter;
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
        UsuarioDAO udao = new UsuarioDAO();

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect("index.jsp");
            return;
        } else if (accion.equals("validarEmail")) {

            String email = request.getParameter("email").toLowerCase();
            String respuesta;
            if (((Usuario) request.getSession().getAttribute("usuario")).getEmail().equalsIgnoreCase(email)) {
                respuesta = "valido";
            } else {
                if (udao.usuarioExiste(email)) {
                    respuesta = "noValido";
                    System.out.println("b");
                } else {
                    respuesta = "valido";
                    System.out.println("c");
                }
            }
            // Configurar la respuesta HTTP como JSON
            response.setCharacterEncoding("UTF-8");

            udao.cerrarConexion();
            // Enviar la respuesta JSON al cliente
            PrintWriter out = response.getWriter();
            out.print(respuesta);
            out.flush();
            return;

        } else if (accion.equals("modificarContra")) {
            String contra = request.getParameter("contrasenaNueva");
            udao.modificarContrasena(((Usuario) request.getSession().getAttribute("usuario")).getId(), contra);
            udao.cerrarConexion();
            request.getSession().invalidate();
            response.sendRedirect("index.jsp");

            return;

        } else if (accion.equals("modificarDireccion")) {
            String pais = request.getParameter("pais").trim();
            String provincia = request.getParameter("provincia").trim();
            String municipio = request.getParameter("municipio").trim();
            String calle = request.getParameter("calle").trim();
            String direccion = pais + " - " + provincia + " - " + municipio + " - " + calle;
            udao.modificarDireccion(((Usuario) request.getSession().getAttribute("usuario")).getId(), direccion);
            udao.cerrarConexion();
            ((Usuario) request.getSession().getAttribute("usuario")).setDireccion(direccion);
            response.sendRedirect("editarPerfil.jsp");
            return;

        } else if (accion.equals("modificarDatos")) {
            String nombre = request.getParameter("usuarioNombre");
            String apellidos = request.getParameter("usuarioApellidos");
            String email = request.getParameter("usuarioEmail");
            String telefono = request.getParameter("usuarioTelefono");
            udao.actualizarUsuario(new Usuario(((Usuario) request.getSession().getAttribute("usuario")).getId(), nombre, apellidos, ((Usuario) request.getSession().getAttribute("usuario")).getContra(), email, ((Usuario) request.getSession().getAttribute("usuario")).getDireccion(), telefono, ((Usuario) request.getSession().getAttribute("usuario")).getRol(), ((Usuario) request.getSession().getAttribute("usuario")).getEsBaja()));
            udao.cerrarConexion();
            request.getSession().setAttribute("usuario", new Usuario(((Usuario) request.getSession().getAttribute("usuario")).getId(), nombre, apellidos, ((Usuario) request.getSession().getAttribute("usuario")).getContra(), email, ((Usuario) request.getSession().getAttribute("usuario")).getDireccion(), telefono, ((Usuario) request.getSession().getAttribute("usuario")).getRol(), ((Usuario) request.getSession().getAttribute("usuario")).getEsBaja()));
            System.out.println(((Usuario) request.getSession().getAttribute("usuario")).getEmail());
            response.sendRedirect("editarPerfil.jsp");
            return;
        } else {
            Usuario usuario;
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
                if (!request.getParameter("usuarioDireccion").equals("")) {
                    direccion = request.getParameter("usuarioDireccion");
                }
                String telefono = request.getParameter("usuarioTelefono");
                String rol = request.getParameter("usuarioRol");
                String contra = request.getParameter("usuarioContrasena");

                if (request.getParameter("usuarioEmailOld").equalsIgnoreCase(email) || !udao.usuarioExiste(email)) {

                    if (accion.equals("modificar")) {
                        int id = Integer.parseInt(request.getParameter("usuarioId"));

                        Usuario nuevo = new Usuario(id, nombre, apellidos, contra, email, direccion, telefono, rol, 0);
                        udao.actualizarUsuario(nuevo);
                        mensaje = "Los datos del usuario han sido actualizados satisfactoriamente.";
                    } else {
                        contra = request.getParameter("usuarioContrasena");

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
