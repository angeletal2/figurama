/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entidades.figura;

/**
 *
 * @author Angel
 */
public class Material {
    
    private int id;
    private String nombre;
    private int esBaja;

    public Material(int id, String nombre, int esBaja) {
        this.id = id;
        this.nombre = nombre;
        this.esBaja= esBaja;
    }

    public Material(String nombre, int esBaja) {
        this.nombre = nombre;
                this.esBaja=esBaja;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEsBaja() {
        return esBaja;
    }

    public void setEsBaja(int esBaja) {
        this.esBaja = esBaja;
    }
    
    

    
}
