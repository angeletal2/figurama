
package modelo.entidades;

import modelo.entidades.figura.Figura;

/**
 *
 * @author Angel
 */
public class ArticuloPedido {
    private int id;
    private int cantidad;
    private double precio;
    private int idPedido;
    private Figura figura;

    public ArticuloPedido(int id, int cantidad, double precio, int idPedido, Figura figura) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idPedido = idPedido;
        this.figura = figura;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }
    
    
}
