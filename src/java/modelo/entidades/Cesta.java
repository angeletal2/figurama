package modelo.entidades;

import java.util.ArrayList;
import java.util.List;
import modelo.dao.figura.FiguraDAO;

/**
 *
 * @author Angel
 */
public class Cesta {

    private List<ArticuloCesta> cesta;

    public Cesta(List<ArticuloCesta> cesta) {
        this.cesta = cesta;
    }

    public int getTamano() {
        int tam = 0;
        for (ArticuloCesta articulo : getArticulos()) {
            tam++;
        }
    return tam;
}
    
     public int getTamanoTodo() {
        
    return cesta.size();
}

public ArticuloCesta getArticulo(int i) {
        return cesta.get(i);
    }

    public void setArticulo(int indice, ArticuloCesta articulo) {
        cesta.set(indice, articulo);
    }

    public void anadir(ArticuloCesta articulo) {
        cesta.add(articulo);
    }

    public boolean contiene(ArticuloCesta articulo) {
        return cesta.contains(articulo);
    }

    public List<ArticuloCesta> getArticulos() {
        List<ArticuloCesta> sinBaja = new ArrayList();
        FiguraDAO fdao = new FiguraDAO();
        for (ArticuloCesta articulo : cesta) {
            if (fdao.figuraDisponible(articulo.getFigura().getId())) {
                sinBaja.add(articulo);
            }
        }
        fdao.cerrarConexion();
        return sinBaja;
    }
    
     public List<ArticuloCesta> getArticulosNormal() {
       List<ArticuloCesta> articulos = new ArrayList();
        for (ArticuloCesta articulo : cesta) {
            articulos.add(articulo);
        }
        return articulos;
    }

    public double getPrecioTotal() {
        double total = 0;
        for (ArticuloCesta articulo : getArticulos()) {

            total += articulo.getFigura().getPrecioConDescuento() * articulo.getCantidad();
        }

        return Math.floor(total * 100) / 100;
    }

    public double getIva() {
        return Math.floor(getPrecioTotal() * 0.21 * 100) / 100;
    }

    public double getPrecioConIva() {
        return Math.floor((getPrecioTotal() + getIva()) * 100) / 100;
    }

    public void eliminarArticulo(int i) {
        cesta.remove(i);
    }

}
