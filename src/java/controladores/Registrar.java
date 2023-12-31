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
@WebServlet(name = "Registrar", urlPatterns = {"/Registrar"})
public class Registrar extends HttpServlet {

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

      
        String nombre = request.getParameter("nombre").trim();
        String apellidos = request.getParameter("apellidos").trim();
        String contra = request.getParameter("contra");
        String email = request.getParameter("email").trim().toLowerCase();
        String direccion = null;
        String telefono = request.getParameter("telefono").trim();
        String rol = "Común";
        UsuarioDAO udao = new UsuarioDAO();
        String redirect = request.getParameter("redirect");

        if (!udao.usuarioExiste(email)) {
            Usuario nuevo = new Usuario(nombre, apellidos, contra, email, direccion, telefono, rol,0);
            udao.anadirUsuario(nuevo);
            request.getSession().setAttribute("cuentaCreada", "La cuenta con email<br>" + email + "<br>ha sido creada satisfactoriamente.");
            response.sendRedirect("login.jsp");
            return;
        } else {

            request.setAttribute("email", email);
            request.setAttribute("nombre", nombre);
            request.setAttribute("apellidos", apellidos);
            request.setAttribute("telefono", telefono);
            request.setAttribute("contra", contra);
            request.setAttribute("error", "Error, ya existe una cuenta con el email introducido");

        }
        udao.cerrarConexion();
        request.setAttribute("redirect", redirect);
        
        getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
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
