/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entidades;

import java.util.Objects;
import modelo.entidades.figura.Figura;

/**
 *
 * @author Angel
 */
public class ArticuloListaDeseos {

    private Figura figura;

    public ArticuloListaDeseos(Figura figura) {
        this.figura = figura;
    }

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
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
        ArticuloListaDeseos otro = (ArticuloListaDeseos) obj;
        return this.figura.getId() == otro.figura.getId();
    }

}
