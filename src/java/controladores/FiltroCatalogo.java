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
@WebServlet(name = "FiltroCatalogo", urlPatterns = {"/FiltroCatalogo"})
public class FiltroCatalogo extends HttpServlet {

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
        // Valores de los checkbox seleccionados
        String[] checkProv = null;
        String[] checkSer = null;
        String filtro = "";
        if (request.getParameterValues("series") != null) {
            checkSer = request.getParameterValues("series");
        }
        if (request.getParameterValues("proveedores") != null) {
            checkProv = request.getParameterValues("proveedores");
        }
        if (request.getParameter("filtro") != null) {
            filtro = request.getParameter("filtro");
        }
        String orden = request.getParameter("orden");

        double precioMinimo = Double.parseDouble(request.getParameter("precioMinimo"));
        double precioMaximo = Double.parseDouble(request.getParameter("precioMaximo"));
        precioMaximo+=0.011;
        String figurasJson;
        // Procesar los valores de los checkbox
        FiguraDAO fdao = new FiguraDAO();
        List<Figura> figuras;

        // Obtener las figuras correspondientes 
        figuras = fdao.getFigurasCatalogo(checkSer, checkProv, filtro, precioMinimo, precioMaximo, orden);

        fdao.cerrarConexion();
        // Convertir la lista de figuras a formato JSON
        Gson gson = new Gson();
        figurasJson = gson.toJson(figuras);
        // Configurar la respuesta HTTP como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Enviar la respuesta JSON al cliente
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
