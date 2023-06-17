package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.dao.CestaDAO;
import modelo.dao.figura.FiguraDAO;
import modelo.entidades.ArticuloCesta;
import modelo.entidades.Cesta;
import modelo.entidades.Usuario;

/**
 *
 * @author Angel
 */
@WebServlet(name = "ActualizarCesta", urlPatterns = {"/ActualizarCesta"})
public class ActualizarCesta extends HttpServlet {

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
        int cantidad;

        if (accion.equals("eliminar") || accion.equals("vaciar")) {
            cantidad = 1;
        } else {
            cantidad = Integer.parseInt(request.getParameter("cantidad"));
        }
        String mensaje = "-2";

        //Si la cantidad no ha sido puesta a mano y no es 0 o menor
        HttpSession sesion = request.getSession();
        CestaDAO cdao = new CestaDAO();
        FiguraDAO fdao = new FiguraDAO();

        int idUsuario = -1;
        if (sesion.getAttribute("usuario") != null) {
            idUsuario = ((Usuario) sesion.getAttribute("usuario")).getId();
        }

        Cesta cesta;

        //Si ya tiene cesta, que se cargue sino que cree una
        if (sesion.getAttribute("cesta") != null) {
            cesta = (Cesta) sesion.getAttribute("cesta");
        } else {
            List<ArticuloCesta> articulos = new ArrayList();
            cesta = new Cesta(articulos);
            sesion.setAttribute("cesta", cesta);
        }

        ArticuloCesta articulo;

        //Añadimos a la cesta en la sesion/BD, no controlo stock porque lo pone el límite cuando cargan los artículos,
        //si cuando se añada a la cesta no queda stock del artículo se indicará en la cesta por tanto aquí se le puede dar paso.
        switch (accion) {
            case "anadir":
                //Comprobar si el articulo ya está en la cesta
                articulo = new ArticuloCesta(fdao.getFiguraPorId(id));
                if (fdao.figuraDisponible(id)) {
                    if (cesta.getTamano() != 0 && cesta.contiene(articulo)) {
                        for (int i = 0; i < cesta.getTamano(); i++) {
                            ArticuloCesta otro = cesta.getArticulo(i);
                            if (articulo.equals(otro)) {
                                articulo.setCantidad(otro.getCantidad() + cantidad);
                                //si hay stock disponible que se añada a la cesta, sino no
                                if (cdao.stockDisponible(articulo)) {

                                    //Actualizar cantidad del artículo en la sesión cesta
                                    cesta.setArticulo(i, articulo);

                                    //Actualizar cantidad del artículo en la BD si tiene sesión iniciada
                                    if (idUsuario != -1) {
                                        cdao.actualizarCantidad(articulo, idUsuario);
                                    }

                                    mensaje = String.valueOf(cesta.getTamano());

                                    break;
                                } else {
                                    //Cantidad imposible de añadir a la cesta por falta de stock
                                    mensaje = "-1";
                                }
                            }
                        }
                    } //Si aún no está añadido a la cesta
                    else {

                        articulo.setCantidad(cantidad);
                        if (idUsuario != -1) {
                            cdao.anadirArticuloCesta(articulo, idUsuario);
                        }

                        //Añadir artículo a la sesión cesta
                        cesta.anadir(articulo);
                        mensaje = String.valueOf(cesta.getTamano());

                    }
                } else {
                    mensaje = "-3";
                }

                break;

            case "modificar":
                // Obtener el valor actual del artículo de la sesión
                articulo = new ArticuloCesta(fdao.getFiguraPorId(id));

                // Recorremos la lista de artículos de la cesta
                for (int i = 0; i < cesta.getTamanoTodo(); i++) {
                    ArticuloCesta otro = cesta.getArticulo(i);

                    if (articulo.equals(otro)) {
                        if (fdao.figuraDisponible(id)) {

                            articulo.setCantidad(cantidad);
                            //Si hay stock disponible
                            if (cdao.stockDisponible(articulo)) {
                                // Si la cantidad es válida, se hacen los cambios en sesión y en BD si fuese necesario
                                articulo.setCantidad(cantidad);
                                cesta.setArticulo(i, articulo);

                                // Actualizar cantidad del artículo en la BD si tiene sesión iniciada
                                if (idUsuario != -1) {
                                    cdao.actualizarCantidad(articulo, idUsuario);
                                }
                                mensaje = String.valueOf(otro.getFigura().getStock());

                            } else {
                                int stockDisponible = fdao.getFiguraPorId(id).getStock();
                                // Si la cantidad es válida, se hacen los cambios en sesión y en BD si fuese necesario
                                articulo.setCantidad(stockDisponible);
                                cesta.setArticulo(i, articulo);

                                // Actualizar cantidad del artículo en la BD si tiene sesión iniciada
                                if (idUsuario != -1) {
                                    cdao.actualizarCantidad(articulo, idUsuario);
                                }
                                mensaje = String.valueOf(stockDisponible);
                            }
                        } else {
                            mensaje = "Vaya... los siguientes artículos han dejado de estar disponibles: \n";
                            for (int j = 0; j < cesta.getArticulosNormal().size(); j++) {
                                if (!fdao.figuraDisponible(cesta.getArticulo(j).getFigura().getId())) {
                                    mensaje += "\n-" + cesta.getArticulo(j).getFigura().getNombre() + "\n";
                                }
                            }
                            mensaje += "\nCuando vuelvan a estar disponibles los artículos aparecerán en su cesta, si no está logado hágalo para no perderlos";
                            break;
                        }
                    }
                }
                break;

            case "eliminar": {
                //Comprobar si el articulo ya está en la cesta
                articulo = new ArticuloCesta(fdao.getFiguraPorId(id));
                //Si a la cesta solo le queda un elemento
                if (cesta.getTamano() == 1) {
                    sesion.removeAttribute("cesta");
                    mensaje = "0";
                    if (idUsuario != -1) {
                        cdao.eliminarArticuloCesta(articulo, idUsuario);
                    }
                } else {
                    //Recorremos la lista de artículos de la cesta
                    for (int i = 0; i < cesta.getTamano(); i++) {
                        ArticuloCesta otro = cesta.getArticulo(i);
                        if (articulo.equals(otro)) {

                            //eliminar el articulo de la sesión
                            cesta.eliminarArticulo(i);

                            //eliminar el artículo de la bd
                            if (idUsuario != -1) {
                                cdao.eliminarArticuloCesta(articulo, idUsuario);
                            }
                        }
                        mensaje = String.valueOf(cesta.getTamano());
                        break;
                    }
                }
                break;
            }

            case "vaciar":
                //Comprobar que la cesta no tenga articulos de baja, que estos no los borre
                List<ArticuloCesta> articulosBaja = new ArrayList();

                mensaje = "Su compra se ha realizado satisfactoriamente pero los siguientes artículos no han podido ser comprados porque actualmente no se encuentran disponibles :\n";
                for (ArticuloCesta articuloComprobar : cesta.getArticulosNormal()) {
                    if (!fdao.figuraDisponible(articuloComprobar.getFigura().getId())) {
                        articulosBaja.add(articuloComprobar);
                        mensaje += "\n - " + articuloComprobar.getFigura().getNombre() + "\n";
                    }
                }

                mensaje += "\n Cuando vuelvan a estar disponibles estos aparecerán en su cesta, si no tiene cuenta regístrese o inicie sesión para no perderlos!";

                if (cesta.getArticulosNormal().size() - articulosBaja.size() == 0) {
                    mensaje = "Compra no realizada, todos los artículos de su cesta han sido dados de baja. Cuando vuelvan a estar disponibles aparecerán en su cesta, registrese para no perderla!";
                }

                cdao.vaciarCesta(idUsuario);
                sesion.removeAttribute("cesta");

                if (!articulosBaja.isEmpty()) {
                    sesion.setAttribute("cesta", new Cesta(articulosBaja));
                } else {
                    mensaje = "";
                }
                break;
            default:
                break;
        }
        cdao.cerrarConexion();
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
