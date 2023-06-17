package modelo.entidades.figura;

import modelo.entidades.ArticuloListaDeseos;
import java.util.Date;
import java.util.List;
import modelo.dao.PersonajeDAO;
import modelo.dao.SerieDAO;
import modelo.dao.figura.FiguraDAO;
import modelo.dao.figura.ImagenFiguraDAO;
import modelo.dao.figura.MaterialDAO;
import modelo.dao.figura.ProveedorDAO;
import modelo.entidades.Personaje;
import modelo.entidades.Serie;

/**
 *
 * @author Angel
 */
public class Figura {

    private int id;
    private String nombre;
    private String descripcion;
    private Date fechaSalida;
    private double precio;
    private int stock;
    private int altura;
    private int idPersonaje;
    private int idProveedor;
    private int idMaterial;
    private int porcentajeDescuento;
    private int esBaja;
    private List<ImagenFigura> imagenes;
    private String primeraImagen;
    private double precioConDescuento;

    public Figura() {
    }

    public Figura(int id) {
        this.id = id;
    }

    
       public Figura(String nombre, String descripcion, Date fechaSalida, double precio, int stock, int altura, int idPersonaje, int idProveedor, int porcentajeDescuento, int idMaterial) {
        this.nombre = nombre;
        this.descripcion=descripcion;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
        this.stock = stock;
        this.altura = altura;
        this.idPersonaje = idPersonaje;
        this.idProveedor = idProveedor;
        this.idMaterial = idMaterial;
        this.porcentajeDescuento = porcentajeDescuento;
      
    }
    
    public Figura(int id, String nombre, String descripcion, Date fechaSalida, double precio, int stock, int altura, int idPersonaje, int idProveedor, int porcentajeDescuento, int idMaterial, int esBaja) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
        this.stock = stock;
        this.altura = altura;
        this.idPersonaje = idPersonaje;
        this.idProveedor = idProveedor;
        this.idMaterial = idMaterial;
        this.porcentajeDescuento = porcentajeDescuento;
        ImagenFiguraDAO ifd = new ImagenFiguraDAO();
        imagenes = ifd.obtenerImagenes(id);
        this.esBaja=esBaja;

        this.primeraImagen = imagenes.get(0).getUrl();

        double valorDescuento = 0;
        if (porcentajeDescuento != 0) {
            valorDescuento = precio * (porcentajeDescuento / 100.0);
        }

        precioConDescuento = Math.floor((precio - valorDescuento) * 100) / 100.0;
    }
    
    
      public Figura(int id, String nombre, String descripcion, Date fechaSalida, double precio, int stock, int altura, int idPersonaje, int idProveedor, int porcentajeDescuento, int idMaterial) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
        this.stock = stock;
        this.altura = altura;
        this.idPersonaje = idPersonaje;
        this.idProveedor = idProveedor;
        this.idMaterial = idMaterial;
        this.porcentajeDescuento = porcentajeDescuento;
        ImagenFiguraDAO ifd = new ImagenFiguraDAO();
        imagenes = ifd.obtenerImagenes(id);

        this.primeraImagen = imagenes.get(0).getUrl();

        double valorDescuento = 0;
        if (porcentajeDescuento != 0) {
            valorDescuento = precio * (porcentajeDescuento / 100.0);
        }

        precioConDescuento = Math.floor((precio - valorDescuento) * 100) / 100.0;
    }
    
     public Figura(String nombre, String descripcion, Date fechaSalida, double precio, int stock, int altura, int idPersonaje, int idProveedor, int porcentajeDescuento, int idMaterial, int esBaja) {
        this.nombre = nombre;
        this.descripcion=descripcion;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
        this.stock = stock;
        this.altura = altura;
        this.idPersonaje = idPersonaje;
        this.idProveedor = idProveedor;
        this.idMaterial = idMaterial;
        this.porcentajeDescuento = porcentajeDescuento;
      
    }
     
    

    public boolean estaEnListaDeseos(List<ArticuloListaDeseos> listaDeseos) {
        return listaDeseos.contains(new ArticuloListaDeseos(this));
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

    public String getDescripcion() {
        return descripcion.replace("\n", System.getProperty("line.separator"));
    }

    public void setDescripcion(String descripcion) {

        this.descripcion = descripcion;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getIdPersonaje() {
        return idPersonaje;
    }

    public void setIdPersonaje(int idPersonaje) {
        this.idPersonaje = idPersonaje;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public List<ImagenFigura> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenFigura> imagenes) {
        this.imagenes = imagenes;
    }

    public String getPrimeraImagen() {
        return primeraImagen;
    }

    public void setPrimeraImagen(String primeraImagen) {
        this.primeraImagen = primeraImagen;
    }

    public int getEsBaja() {
        return esBaja;
    }

    public void setEsBaja(int esBaja) {
        this.esBaja = esBaja;
    }
    
    

    public List<Material> getMateriales() {
        MaterialDAO mdao = new MaterialDAO();
        List<Material> materiales=mdao.getListaMateriales(id);
        mdao.cerrarConexion();
        return materiales;

    }

    public Proveedor getProveedor() {
        ProveedorDAO pdao = new ProveedorDAO();
        Proveedor proveedor = pdao.getProveedorPorId(idProveedor);
        pdao.cerrarConexion();
        return proveedor;
    }

    public Material getMaterial() {
        MaterialDAO mdao = new MaterialDAO();
        Material material = mdao.getMaterialPorId(idMaterial);
        mdao.cerrarConexion();
        return material;
    }

    public Personaje getPersonaje() {
        PersonajeDAO pjdao = new PersonajeDAO();
        Personaje p = pjdao.getPersonajePorId(idPersonaje);
        pjdao.cerrarConexion();
        return p;
    }

    public Serie getSerie() {
        SerieDAO sdao = new SerieDAO();
        Serie s = sdao.getSeriePorId(getPersonaje().getIdSerie());
        sdao.cerrarConexion();
        return s;
    }

    public double getPrecioConDescuento() {
        return precioConDescuento;
    }

    public double getIva() {
        return Math.floor(precioConDescuento * 0.21 * 100) / 100;
    }

    public double getPrecioConIva() {
        return Math.floor((precioConDescuento + getIva()) * 100) / 100;
    }
    
     public int getNumeroVentas(){
        FiguraDAO fdao = new FiguraDAO();
        int res = fdao.getNumeroVentas(id);
        fdao.cerrarConexion();
        return res;
    }
}
