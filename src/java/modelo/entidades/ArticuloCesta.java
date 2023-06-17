
package modelo.entidades;

import java.util.Objects;
import modelo.entidades.figura.Figura;

/**
 *
 * @author Angel
 */
public class ArticuloCesta {

    private Figura figura;
    private int cantidad;

    public ArticuloCesta(Figura figura) {
        this.figura = figura;
    }
    
    public ArticuloCesta(Figura figura, int cantidad) {
        this.figura = figura;
        this.cantidad = cantidad;
    }

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public double getPrecioTotal(){
        return figura.getPrecioConDescuento()*cantidad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(figura.getId());
    }
   

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ArticuloCesta otro = (ArticuloCesta) obj;
        return this.figura.getId() == otro.figura.getId();
    }

}
