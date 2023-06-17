/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entidades.figura;

/**
 *
 * @author Angel
 */
public class Proveedor {

    private int id;
    private String nombre;
    private String url;
    private int esBaja;

    public Proveedor(int id, String nombre, String url, int esBaja) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
        this.esBaja = esBaja;

    }

    public Proveedor(String nombre, String url, int esBaja) {
        this.nombre = nombre;
        this.url = url;
        this.esBaja = esBaja;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getEsBaja() {
        return esBaja;
    }

    public void setEsBaja(int esBaja) {
        this.esBaja = esBaja;
    }
    
    

}
