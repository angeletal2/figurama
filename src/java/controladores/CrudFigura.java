package controladores;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.dao.PersonajeDAO;
import modelo.dao.figura.FiguraDAO;
import modelo.dao.figura.MaterialDAO;
import modelo.dao.figura.ProveedorDAO;
import modelo.entidades.figura.Figura;

/**
 *
 * @author Angel
 */
@WebServlet(name = "CrudFigura", urlPatterns = {"/CrudFigura"})
@MultipartConfig
public class CrudFigura extends HttpServlet {

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
        String accion = request.getParameter("accion");
        if (accion == null) {
            response.sendRedirect("index.jsp");
            return;
        } else {
            Figura figura;
            FiguraDAO fdao = new FiguraDAO();
            if (accion.equals("cambiarEstado")) {
                
                int idFigura = Integer.parseInt(request.getParameter("id"));
            figura = fdao.getFiguraPorId(idFigura);

                int estado;
                //Si es 1 es que si, 0 es que no
                if (figura.getEsBaja() == 1) {
                    estado = 0;
                    mensaje = "La figura '"+figura.getNombre()+"' ha sido dada de alta correctamente";
                } else {
                    estado = 1;
                    mensaje = "La figura '"+figura.getNombre()+"' ha sido dada de baja correctamente";
                }
                fdao.cambiarEstado(idFigura, estado);
            } else {

                MaterialDAO mdao = new MaterialDAO();
                PersonajeDAO pjdao = new PersonajeDAO();
                ProveedorDAO pdao = new ProveedorDAO();

                Part nombrePart = request.getPart("figuraNombre");
                String nombre = "";
                if (nombrePart != null) {
                    try ( InputStream inputStream = nombrePart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        nombre = result.toString(StandardCharsets.UTF_8.name());
                    }
                }

                Part personajePart = request.getPart("figuraPersonaje");
                String nombrePersonaje = "";
                if (personajePart != null) {
                    try ( InputStream inputStream = personajePart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        nombrePersonaje = result.toString(StandardCharsets.UTF_8.name());
                    }
                }

                Part descripcionPart = request.getPart("figuraDescripcion");
                String descripcion = "";
                if (descripcionPart != null) {
                    try ( InputStream inputStream = descripcionPart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        descripcion = result.toString(StandardCharsets.UTF_8.name());
                    }
                }

                Part precioPart = request.getPart("figuraPrecio");
                Double precio = 0.0;
                if (precioPart != null) {
                    try ( InputStream inputStream = precioPart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        precio = Double.valueOf(result.toString(StandardCharsets.UTF_8.name()));
                    }
                }

                Part alturaPart = request.getPart("figuraAltura");
                int altura = 0;
                if (alturaPart != null) {
                    try ( InputStream inputStream = alturaPart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        altura = Integer.parseInt(result.toString(StandardCharsets.UTF_8.name()));
                    }
                }

                Part stockPart = request.getPart("figuraStock");
                int stock = 0;
                if (stockPart != null) {
                    try ( InputStream inputStream = stockPart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        stock = Integer.parseInt(result.toString(StandardCharsets.UTF_8.name()));
                    }
                }

                Part descuentoPart = request.getPart("figuraDescuento");
                int descuento = 0;
                if (descuentoPart != null) {
                    try ( InputStream inputStream = descuentoPart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        descuento = Integer.parseInt(result.toString(StandardCharsets.UTF_8.name()));
                    }
                }

                Part proveedorPart = request.getPart("figuraProveedor");
                String nombreProveedor = "";
                if (proveedorPart != null) {
                    try ( InputStream inputStream = proveedorPart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        nombreProveedor = result.toString(StandardCharsets.UTF_8.name());
                    }
                }

                Part materialPart = request.getPart("figuraMaterial");
                String nombreMaterial = "";
                if (materialPart != null) {
                    try ( InputStream inputStream = materialPart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        nombreMaterial = result.toString(StandardCharsets.UTF_8.name());
                    }
                }

                Part fechaPart = request.getPart("figuraFecha");
                Date fecha = null;
                if (fechaPart != null) {
                    try ( InputStream inputStream = fechaPart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        fecha = inputFormat.parse(result.toString(StandardCharsets.UTF_8.name()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                    File uploads = new File("C:\\Users\\Angel\\Desktop\\Figurama\\web\\assets\\images\\figuras");

                // Obtén la parte de la imagen del archivo cargado
                Part filePart = request.getPart("figuraImagen1");
                // Verifica si la parte de la imagen está presente y si tiene un tamaño superior a 0
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = nombre.replace(" ", "_") + "_1.jpg";

                    InputStream fileContent = filePart.getInputStream();
                    File file = new File(uploads, fileName);

                    // Si el archivo existe en el servidor, eliminarlo
                    if (file.exists()) {
                        file.delete();
                    }

                    // Subir la nueva imagen
                    try ( InputStream input = fileContent) {
                        Files.copy(input, file.toPath());
                    }
                }

                // Obtén la parte de la imagen del archivo cargado
                Part filePart2 = request.getPart("figuraImagen2");
                // Verifica si la parte de la imagen está presente y si tiene un tamaño superior a 0
                if (filePart2 != null && filePart2.getSize() > 0) {
                    String fileName = nombre.replace(" ", "_") + "_2.jpg";

                    InputStream fileContent = filePart2.getInputStream();
                     File file = new File(uploads, fileName);

                    // Si el archivo existe en el servidor, eliminarlo
                    if (file.exists()) {
                        file.delete();
                    }

                    // Subir la nueva imagen
                    try ( InputStream input = fileContent) {
                        Files.copy(input, file.toPath());
                    }

                }

                // Obtén la parte de la imagen del archivo cargado
                Part filePart3 = request.getPart("figuraImagen3");
                // Verifica si la parte de la imagen está presente y si tiene un tamaño superior a 0
                if (filePart3 != null && filePart3.getSize() > 0) {
                    String fileName = nombre.replace(" ", "_") + "_3.jpg";

                    InputStream fileContent = filePart3.getInputStream();
                    File file = new File(uploads, fileName);

                    // Si el archivo existe en el servidor, eliminarlo
                    if (file.exists()) {
                        file.delete();
                    }

                    // Subir la nueva imagen
                    try ( InputStream input = fileContent) {
                        Files.copy(input, file.toPath());
                    }

                }

                if (accion.equals("modificar")) {

                    Part idPart = request.getPart("figuraId");
                    int id = -1;
                    if (idPart != null) {
                        try ( InputStream inputStream = idPart.getInputStream()) {
                            ByteArrayOutputStream result = new ByteArrayOutputStream();
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = inputStream.read(buffer)) != -1) {
                                result.write(buffer, 0, length);
                            }
                            id = Integer.parseInt(result.toString(StandardCharsets.UTF_8.name()));
                        }
                    }

                    figura = new Figura(id, nombre, descripcion, fecha, precio, stock, altura, pjdao.getPersonajePorNombre(nombrePersonaje.toLowerCase()).getId(), pdao.getProveedorPorNombre(nombreProveedor.toLowerCase()).getId(), descuento, mdao.getMaterialPorNombre(nombreMaterial.toLowerCase()).getId());
                    fdao.modificarFigura(figura);
                    mensaje = "La figura '" + nombre+"' ha sido modificada satisfactoriamente";

                } else if (accion.equals("anadir")) {
                    
                    if (!fdao.figuraExiste(nombre)) {

                       figura = new Figura(nombre, descripcion, fecha, precio, stock, altura, pjdao.getPersonajePorNombre(nombrePersonaje.toLowerCase()).getId(), pdao.getProveedorPorNombre(nombreProveedor.toLowerCase()).getId(), descuento, mdao.getMaterialPorNombre(nombreMaterial.toLowerCase()).getId());
                       fdao.anadirFigura(figura);
                    mensaje = "Se ha añadido la figura " + nombre;
                    } else {
                        mensaje = "Error, no se ha podido crear la figura de nombre '" + nombre + "' debido a que ya existe una figura que se llama así. Por favor, revise las figuras existentes y aségurese de ponerle un nombre que no exista.";
                    }
                }

                mdao.cerrarConexion();
                pdao.cerrarConexion();
                pjdao.cerrarConexion();
            }
            fdao.cerrarConexion();

        }

        request.getSession().setAttribute("mensaje", mensaje);
        request.getSession().setAttribute("tablaMostrada", "Figura");
        response.sendRedirect("admin");
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
