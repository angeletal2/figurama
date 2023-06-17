package controladores;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.dao.PersonajeDAO;
import modelo.dao.SerieDAO;
import modelo.entidades.Personaje;

/**
 *
 * @author Angel
 */
@WebServlet(name = "CrudPersonaje", urlPatterns = {"/CrudPersonaje"})
@MultipartConfig
public class CrudPersonaje extends HttpServlet {

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
            Personaje personaje;
            PersonajeDAO pdao = new PersonajeDAO();
            SerieDAO sdao = new SerieDAO();

            if (accion.equals("cambiarEstado")) {
                int idPersonaje = Integer.parseInt(request.getParameter("id"));
                personaje = pdao.getPersonajePorId(idPersonaje);
                int estado;
                //Si es 1 es que si, 0 es que no
                if (personaje.getEsBaja() == 1) {
                    estado = 0;
                    mensaje = "El personaje '" + personaje.getNombre() + "' ha sido dado de alta correctamente";
                } else {
                    estado = 1;
                    mensaje = "El personaje '" + personaje.getNombre() + "' ha sido dado de baja correctamente";
                }
                pdao.cambiarEstado(idPersonaje, estado);
            } else {

                Part nombrePart = request.getPart("personajeNombre");
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
                Part seriePart = request.getPart("personajeSerie");
                String nombreSerie = "";
                if (seriePart != null) {
                    try ( InputStream inputStream = seriePart.getInputStream()) {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        nombreSerie = result.toString(StandardCharsets.UTF_8.name());
                    }
                }

                File uploads = new File("C:\\Users\\Angel\\Desktop\\Figurama\\web\\assets\\images\\personajes");
                String fileName = nombre.toLowerCase().replace(" ", "_") + ".png";

                // Obtén la parte de la imagen del archivo cargado
                Part filePart = request.getPart("personajeImagen");
                // Verifica si la parte de la imagen está presente y si tiene un tamaño superior a 0
                if (filePart != null && filePart.getSize() > 0) {

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
                if (accion.equals("modificar")) {

                    Part idPart = request.getPart("personajeId");
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

                    personaje = new Personaje(id, nombre,sdao.getSeriePorNombre(nombreSerie).getId(), fileName, 0);
                    pdao.modificarPersonaje(personaje);
                    mensaje = "Los datos del personaje '" + nombre + "' han sido modificado satisfactoriamente";

                } else if (accion.equals("anadir")) {

                    if (!pdao.personajeExiste(nombre)) {

                        personaje = new Personaje(nombre, sdao.getSeriePorNombre(nombreSerie).getId(), fileName, 0);
                        pdao.anadirPersonaje(personaje);
                        mensaje = "Se ha añadido al personaje '" + nombre + "'";
                    } else {
                        mensaje = "Error, no se ha podido crear el personaje de nombre '" + nombre + "' debido a que ya existe un personaje que se llama así. Por favor, revise los personajes existentes y aségurese de ponerle un nombre que no exista.";
                    }
                }

            }
            pdao.cerrarConexion();
            sdao.cerrarConexion();
        }

        request.getSession().setAttribute("mensaje", mensaje);
        request.getSession().setAttribute("tablaMostrada", "Personaje");
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
