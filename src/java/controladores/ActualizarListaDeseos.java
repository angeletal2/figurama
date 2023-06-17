package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.dao.figura.FiguraDAO;
import modelo.dao.ListaDeseosDAO;
import modelo.entidades.figura.Figura;
import modelo.entidades.ArticuloListaDeseos;
import modelo.entidades.Usuario;

/**
 *
 * @author Angel
 */
@WebServlet(name = "ActualizarListaDeseos", urlPatterns = {"/ActualizarListaDeseos"})
public class ActualizarListaDeseos extends HttpServlet {

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
    response.setContentType("application/json;charset=UTF-8");
    int id = Integer.parseInt(request.getParameter("id")); 
    String accion = request.getParameter("accion");

    String mensaje;
        
    HttpSession sesion = request.getSession();
    ListaDeseosDAO lddao = new ListaDeseosDAO();
    
    List<ArticuloListaDeseos> listaDeseos = (List<ArticuloListaDeseos>)sesion.getAttribute("listaDeseos");
    FiguraDAO fdao = new FiguraDAO();
    if (accion.equals("eliminar")) {
        //Eliminar el articulo de la sesion de listaDeseos y lo eliminar de la BD
        listaDeseos.remove(new ArticuloListaDeseos(fdao.getFiguraPorId(id)));
        lddao.eliminarListaDeseos(id, ((Usuario)sesion.getAttribute("usuario")).getId());
        mensaje = "Artículo eliminado de la lista de deseos";
    } else {
        listaDeseos.add(new ArticuloListaDeseos(new Figura(id)));
        //Añade el articulo a la sesion de listaDeseos y lo añade a la BD
        lddao.anadirListaDeseos(id, ((Usuario)sesion.getAttribute("usuario")).getId());
        mensaje = "Artículo añadido a la lista de deseos";
    }
    lddao.cerrarConexion();
    fdao.cerrarConexion();

    // Envía una respuesta al cliente
    PrintWriter out = response.getWriter();
    out.println(mensaje);
    out.flush();
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
