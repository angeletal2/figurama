
package modelo.entidades;

import modelo.entidades.figura.Figura;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modelo.dao.CestaDAO;
import modelo.dao.figura.FiguraDAO;
import modelo.dao.ListaDeseosDAO;
import modelo.dao.SerieDAO;
import modelo.dao.figura.ProveedorDAO;
import modelo.entidades.figura.Proveedor;

/**
 *
 * @author Angel
 */
public class CargarDatos {

    HttpSession sesion;

    public CargarDatos(HttpServletRequest request) {
        this.sesion = request.getSession();
    }

    public void iniciarDatos() {

    }

    public void cargarFiguras() {
        FiguraDAO fdao = new FiguraDAO();
        List<Figura> figuras = fdao.getListaFiguras();
        sesion.setAttribute("figuras", figuras);
        fdao.cerrarConexion();
    }

    public void cargarProveedores() {
        ProveedorDAO pdao = new ProveedorDAO();
        List<Proveedor> proveedores = pdao.getListaProveedores();
        sesion.setAttribute("proveedores", proveedores);
        pdao.cerrarConexion();
    }

    public void cargarFranquicias() {
        SerieDAO sdao = new SerieDAO();
        List<Serie> series = sdao.getListaSeries();
        sesion.setAttribute("franquicias", series);
        sdao.cerrarConexion();
    }

    public void cargarListaDeseos() {
        if (sesion.getAttribute("usuario") != null) {
            ListaDeseosDAO lddao = new ListaDeseosDAO();
            List<ArticuloListaDeseos> listaDeseos = lddao.obtenerListaDeseos(((Usuario) sesion.getAttribute("usuario")).getId());
            sesion.setAttribute("listaDeseos", listaDeseos);
            lddao.cerrarConexion();
        }
    }

    public void cargarCesta() {
        if (sesion.getAttribute("usuario") != null) {
            CestaDAO cdao = new CestaDAO();
            List<ArticuloCesta> articulos = cdao.obtenerCesta(((Usuario) sesion.getAttribute("usuario")).getId());
            if(!articulos.isEmpty()){
            Cesta cesta = new Cesta(articulos);
            sesion.setAttribute("cesta", cesta);
            cdao.cerrarConexion();
        }}
    }

}
