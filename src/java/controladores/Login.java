package controladores;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.dao.CestaDAO;
import modelo.dao.UsuarioDAO;
import modelo.entidades.CargarDatos;
import modelo.entidades.Usuario;
import modelo.entidades.Cesta;

/**
 *
 * @author Angel
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

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
        String error = "Email o contraseña incorrectos";
        ;
        if (!request.getParameter("email").isBlank() && !request.getParameter("contra").isBlank()) {
            String email = request.getParameter("email");
            String contra = request.getParameter("contra");

            //Se obtienen los usuarios de la lista obtenida por el DAO
            UsuarioDAO udao = new UsuarioDAO();
            List<Usuario> usuarios = udao.getListaUsuarios();
            HttpSession sesion = request.getSession();
            for (Usuario usuario : usuarios) {
                if (usuario.getEmail().equalsIgnoreCase(email)
                        && usuario.getContra().equals(contra)) {
                    System.out.println(usuario.getEsBaja());
                    if (usuario.getEsBaja() == 0) {
                        //Añado a la sesion el usuario que accede para saber en todo momento quien es
                        //Si el usuario es admin que vaya al menu de admin, sino al de árbitros

                        //Si tiene una cesta ya creada y no esta logado ya, que la cesta se vuelque en la del usuario donde se ha logado
                        if (sesion.getAttribute("cesta") != null && sesion.getAttribute("usuario") == null) {
                            CestaDAO cdao = new CestaDAO();
                            cdao.guardarCesta(((Cesta) sesion.getAttribute("cesta")).getArticulos(), usuario.getId());
                            sesion.removeAttribute("cesta");
                            cdao.cerrarConexion();
                        }
                        sesion.setAttribute("usuario", usuario);

                        //Cargamos la cesta y la lista de deseos del usuario en la BD
                        CargarDatos cargar = new CargarDatos(request);
                        cargar.cargarCesta();
                        cargar.cargarListaDeseos();
                        if (udao.getUsuario(email).getRol().equals("Admin") || udao.getUsuario(email).getRol().equals("AdminMaestro")) {
                            sesion.setAttribute("usuario", usuario);
                            response.sendRedirect("admin");
                            return;
                        } else {
                            response.sendRedirect(sesion.getAttribute("redirect").toString());
                            return;
                        }
                    } else {
                        error = "Su cuenta está dada de baja. Si desea darla de alta contacte a nuestro soporte:<br>Email de contacto: figuramasoporte@gmail.com<br>Teléfono de contacto: 644826846";
                    }
                }
            }
            request.setAttribute("email", email);
            udao.cerrarConexion();

        } else {
            if (request.getParameter("email") != null) {
                String email = request.getParameter("email");
                request.setAttribute("email", email);
            }
            error = "Debe rellenar todos los campos";
        }

        request.setAttribute("error", error);

        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
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
