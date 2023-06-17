package controladores;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.PersonajeDAO;
import modelo.dao.SerieDAO;
import modelo.dao.figura.FiguraDAO;
import modelo.entidades.Personaje;
import modelo.entidades.Serie;
import modelo.entidades.figura.Figura;

/**
 *
 * @author Angel
 */
@WebServlet(name = "VerFranquicia", urlPatterns = {"/franquicias/*"})
public class VerFranquicia extends HttpServlet {

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

        // Si se ha llamado desde JS para buscar los personajes o las figuras, devuelve JSON; sino, muestra el JSP
        String nombreSerie = request.getParameter("nombreSerie");
        String nombrePersonaje = request.getParameter("nombrePersonaje");
        if (nombreSerie != null) {
            PersonajeDAO pjdao = new PersonajeDAO();

            List<Personaje> personajes = pjdao.getPersonajesPorSerie(nombreSerie);
            // Convierte la lista de personajes a formato JSON
            Gson gson = new Gson();
            String personajesJson = gson.toJson(personajes);

            pjdao.cerrarConexion();

            // Configura la respuesta como JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Envía la respuesta JSON al cliente
            PrintWriter out = response.getWriter();
            out.print(personajesJson);
            out.flush();
            return;
        } else if (nombrePersonaje != null) {
            FiguraDAO fdao = new FiguraDAO();

            List<Figura> figuras = fdao.getListaFiguras(nombrePersonaje, "tipo2");
            fdao.cerrarConexion();
            // Convierte la lista de figuras a formato JSON
            Gson gson = new Gson();
            String figurasJson = gson.toJson(figuras);

            // Configura la respuesta como JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Envía la respuesta JSON al cliente
            PrintWriter out = response.getWriter();
            out.print(figurasJson);
            out.flush();
            return;
        } else {
            response.setContentType("text/html;charset=UTF-8");

            String figuraParam = request.getPathInfo();
            if (figuraParam == null || figuraParam.equals("/")) {
                response.sendRedirect("../franquicias");
                return;
            }

            String nombre = figuraParam.substring(1).replace("+", " ");
            SerieDAO sdao = new SerieDAO();

            Serie serie = sdao.getSeriePorNombre(nombre);
            sdao.cerrarConexion();
            if (serie != null) {
                //cosas  List<Figura> masVendidas = fdao.getListaFigurasMasVendidas(serie.getId());
                request.setAttribute("serie", nombre);
                //     request.setAttribute("masVendidas", masVendidas);

                getServletContext().getRequestDispatcher("/franquicia.jsp").forward(request, response);
            } else {
                response.sendRedirect("../error.jsp");
                return;
            }
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
