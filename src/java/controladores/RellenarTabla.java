package controladores;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.PedidoDAO;
import modelo.dao.PersonajeDAO;
import modelo.dao.SerieDAO;
import modelo.dao.UsuarioDAO;
import modelo.dao.figura.FiguraDAO;
import modelo.dao.figura.MaterialDAO;
import modelo.dao.figura.ProveedorDAO;
import modelo.entidades.Pedido;
import modelo.entidades.Personaje;
import modelo.entidades.Serie;
import modelo.entidades.Usuario;
import modelo.entidades.figura.Figura;
import modelo.entidades.figura.Material;
import modelo.entidades.figura.Proveedor;

/**
 *
 * @author Angel
 */
@WebServlet(name = "RellenarTabla", urlPatterns = {"/RellenarTabla"})
public class RellenarTabla extends HttpServlet {

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

        String tablaAMostrar = request.getParameter("tabla");
        Gson gson = new Gson();

        String resultado = "";

        FiguraDAO fdao = new FiguraDAO();
        PersonajeDAO pjdao = new PersonajeDAO();
        ProveedorDAO pdao = new ProveedorDAO();
        MaterialDAO mdao = new MaterialDAO();
        PedidoDAO pddao = new PedidoDAO();
        SerieDAO sdao = new SerieDAO();
        UsuarioDAO udao = new UsuarioDAO();

        List<Figura> figuras = fdao.getListaFigurasAdmin();
        List<Pedido> pedidos = pddao.obtenerPedidos();
        List<Serie> series = sdao.getListaSeriesAdmin();
        List<Personaje> personajes = pjdao.getListaPersonajesAdmin();
        List<Material> materiales = mdao.getListaMaterialesAdmin();
        List<Proveedor> proveedores = pdao.getListaProveedoresAdmin();
        List<Usuario> usuarios = udao.getListaUsuarios();

        switch (tablaAMostrar) {

            case "Figura":

                List<Map<String, Object>> nuevaListaFiguras = new ArrayList();

                for (Figura figura : figuras) {

                    Map<String, Object> nuevaFigura = new HashMap();
                    nuevaFigura.put("Id", figura.getId());
                    nuevaFigura.put("Primera Imagen", figura.getImagenes().get(0).getUrl());
                    nuevaFigura.put("Segunda Imagen", figura.getImagenes().get(1).getUrl());
                    nuevaFigura.put("Tercera Imagen", figura.getImagenes().get(2).getUrl());
                    nuevaFigura.put("Personaje", figura.getPersonaje().getNombre());
                    nuevaFigura.put("Nombre", figura.getNombre());
                    nuevaFigura.put("Descripción", figura.getDescripcion());
                    nuevaFigura.put("Fecha de salida", figura.getFechaSalida());
                    nuevaFigura.put("Precio", figura.getPrecio());
                    nuevaFigura.put("Stock", figura.getStock());
                    nuevaFigura.put("Altura", figura.getAltura());
                    nuevaFigura.put("Descuento", figura.getPorcentajeDescuento());
                    nuevaFigura.put("Proveedor", figura.getProveedor().getNombre());
                    nuevaFigura.put("Material", figura.getMaterial().getNombre());
                    nuevaFigura.put("Personajes", personajes);
                    nuevaFigura.put("Materiales", materiales);
                    nuevaFigura.put("Proveedores", proveedores);
                    nuevaFigura.put("Baja", figura.getEsBaja());
                    nuevaListaFiguras.add(nuevaFigura);
                }

                resultado = gson.toJson(nuevaListaFiguras);

                break;
            case "Pedido":

                List<Map<String, Object>> nuevaListaPedidos = new ArrayList();

                for (Pedido pedido : pedidos) {

                    Map<String, Object> nuevoPedido = new HashMap();
                    nuevoPedido.put("Id", pedido.getId());
                    nuevoPedido.put("Fecha", pedido.getFecha());

                    nuevoPedido.put("Estado", pedido.getEstado());
                    nuevoPedido.put("Comprador", pedido.getEmailUsuario());

                    nuevoPedido.put("Dirección", pedido.getDireccion());
                    nuevoPedido.put("Detalles", pedido.getArticulos());
                    nuevoPedido.put("Total", pedido.getPrecioTotal());
                    nuevaListaPedidos.add(nuevoPedido);
                }

                resultado = gson.toJson(nuevaListaPedidos);

                break;

            case "Serie":
                List<Map<String, Object>> nuevaListaSeries = new ArrayList();

                for (Serie serie : series) {

                    Map<String, Object> nuevaSerie = new HashMap();
                    nuevaSerie.put("Id", serie.getId());
                    nuevaSerie.put("Imagen", serie.getUrl());
                    nuevaSerie.put("Nombre", serie.getNombre());
                    nuevaSerie.put("Baja", serie.getEsBaja());
                    nuevaListaSeries.add(nuevaSerie);
                }

                resultado = gson.toJson(nuevaListaSeries);

                break;

            case "Personaje":
                List<Map<String, Object>> nuevaListaPersonajes = new ArrayList();

                for (Personaje personaje : personajes) {

                    Map<String, Object> nuevoPersonaje = new HashMap();
                    nuevoPersonaje.put("Id", personaje.getId());
                    nuevoPersonaje.put("Imagen", personaje.getUrl());
                    nuevoPersonaje.put("Nombre", personaje.getNombre());
                    nuevoPersonaje.put("Serie", personaje.getNombreSerie());
                    nuevoPersonaje.put("Series", sdao.getListaSeries());
                    nuevoPersonaje.put("Baja", personaje.getEsBaja());

                    nuevaListaPersonajes.add(nuevoPersonaje);
                }

                resultado = gson.toJson(nuevaListaPersonajes);

                break;

            case "Material":

                List<Map<String, Object>> nuevaListaMateriales = new ArrayList();

                for (Material material : materiales) {

                    Map<String, Object> nuevoMaterial = new HashMap();
                    nuevoMaterial.put("Id", material.getId());
                    nuevoMaterial.put("Nombre", material.getNombre());
                    nuevoMaterial.put("Baja", material.getEsBaja());

                    nuevaListaMateriales.add(nuevoMaterial);
                }

                resultado = gson.toJson(nuevaListaMateriales);

                break;

            case "Proveedor":

                List<Map<String, Object>> nuevaListaProveedores = new ArrayList();

                for (Proveedor proveedor : proveedores) {

                    Map<String, Object> nuevoProveedor = new HashMap();
                    nuevoProveedor.put("Id", proveedor.getId());
                    nuevoProveedor.put("Nombre", proveedor.getNombre());
                    nuevoProveedor.put("Imagen", proveedor.getUrl());
                    nuevoProveedor.put("Baja", proveedor.getEsBaja());

                    nuevaListaProveedores.add(nuevoProveedor);
                }

                resultado = gson.toJson(nuevaListaProveedores);

                break;

            case "Usuario":

                List<Map<String, Object>> nuevaListaUsuarios = new ArrayList();

                for (Usuario usuario : usuarios) {

                    Map<String, Object> nuevoUsuario = new HashMap();
                    if (((Usuario) request.getSession().getAttribute("usuario")).getRol().equals("AdminMaestro")
                            && !usuario.getRol().equals("Anónimo")
                            && !((Usuario) request.getSession().getAttribute("usuario")).getEmail().equals(usuario.getEmail())) {

                        nuevoUsuario.put("Id", usuario.getId());
                        nuevoUsuario.put("Nombre", usuario.getNombre());
                        nuevoUsuario.put("Apellidos", usuario.getApellidos());
                        nuevoUsuario.put("Email", usuario.getEmail());
                        nuevoUsuario.put("Dirección", usuario.getDireccion());
                        nuevoUsuario.put("Teléfono", usuario.getTelefono());
                        nuevoUsuario.put("Rol", usuario.getRol());
                        nuevoUsuario.put("Baja", usuario.getEsBaja());
                        nuevoUsuario.put("Admin", "AdminMaestro");
                        nuevaListaUsuarios.add(nuevoUsuario);

                    } else if (usuario.getRol().equals("Común")) {
                        nuevoUsuario.put("Id", usuario.getId());
                        nuevoUsuario.put("Nombre", usuario.getNombre());
                        nuevoUsuario.put("Apellidos", usuario.getApellidos());
                        nuevoUsuario.put("Email", usuario.getEmail());
                        nuevoUsuario.put("Dirección", usuario.getDireccion());
                        nuevoUsuario.put("Teléfono", usuario.getTelefono());
                        nuevoUsuario.put("Rol", usuario.getRol());
                        nuevoUsuario.put("Baja", usuario.getEsBaja());
                        nuevoUsuario.put("Admin", "Admin");
                        nuevaListaUsuarios.add(nuevoUsuario);
                    }

                }
                resultado = gson.toJson(nuevaListaUsuarios);

                break;

        }

        pdao.cerrarConexion();
        fdao.cerrarConexion();
        pjdao.cerrarConexion();
        mdao.cerrarConexion();
        sdao.cerrarConexion();
        // Configura la respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Envía la respuesta JSON al cliente
        PrintWriter out = response.getWriter();
        out.print(resultado);
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
