/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import modelo.dao.CestaDAO;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Cesta;
import modelo.entidades.Usuario;
import modelo.entidades.figura.Figura;

/**
 *
 * @author Angel
 */
@WebServlet(name = "Comprar", urlPatterns = {"/Comprar"})
public class Comprar extends HttpServlet {

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

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        CestaDAO cdao = new CestaDAO();
        Cesta cesta = (Cesta) request.getSession().getAttribute("cesta");
        int idUsuario;
        if (usuario == null) {
            idUsuario = 1;
        } else {
            idUsuario = usuario.getId();
        }

        
        List<Figura> figuras = cdao.comprar(cesta, idUsuario, request.getParameter("direccion"));
        //Si la lista está vacía es que no se ha podido realizar la compra así que se le guarda la cesta con los stocks maximos de cada articulo
        if (!figuras.isEmpty()) {

            //Modificar cesta en sesion con el array de id, cantidad

            for (int i = 0; i < cesta.getTamano(); i++) {
                for (Figura figura : figuras) {
                    //Modificar el stock de las figuras que esten en la lista de errores
                    if (cesta.getArticulo(i).getFigura().equals(figura)) {
                        cesta.getArticulo(i).getFigura().setStock(figura.getStock());

                        cesta.getArticulo(i).setCantidad(figura.getStock());
                    }
                }
            }
            request.getSession().setAttribute("cesta", cesta);
            if (usuario != null) {
                cdao.guardarCesta(cesta.getArticulos(), usuario.getId());
            }
        }
        cdao.cerrarConexion();
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
