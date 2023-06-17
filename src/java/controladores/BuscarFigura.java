
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
import modelo.dao.figura.FiguraDAO;
import modelo.entidades.figura.Figura;

/**
 *
 * @author Angel
 */
@WebServlet(name = "BuscarFigura", urlPatterns = {"/BuscarFigura"})
public class BuscarFigura extends HttpServlet {

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
        String filtro = request.getParameter("filtro");

     
         FiguraDAO fdao = new FiguraDAO();
        List<Figura> figuras = fdao.getListaFiguras(filtro.toLowerCase(), "filtroTexto");

        // Convierte la lista de figuras a formato JSON
        Gson gson = new Gson();
        String figurasJson = gson.toJson(figuras);

        // Configura la respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        fdao.cerrarConexion();
        // Envía la respuesta JSON al cliente
        PrintWriter out = response.getWriter();
        out.print(figurasJson);
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

