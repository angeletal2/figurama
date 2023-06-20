package modelo.entidades;

/**
 *
 * @author Angel
 */
public class Usuario {

    private int id;
    private String nombre;
    private String apellidos;
    private String contra;
    private String email;
    private String direccion;
    private String telefono;
    private String rol;
    private int esBaja;

    // Constructor vacio, se usa a la hora de guardar un usuario
    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String contra, String email, String direccion, String telefono, String rol, int esBaja) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contra = contra;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rol = rol;
        this.esBaja = esBaja;
    }

    // Constructor usado a la hora de guardar un usuario en la BD
    public Usuario(int id, String nombre, String apellidos, String contra, String email, String direccion, String telefono, String rol, int esBaja) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contra = contra;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rol = rol;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        if (direccion !=null && !direccion.isEmpty()) {
            return direccion.split("-")[0].trim();
        } else {
            return "";
        }
    }
    
    public String getProvincia() {
        if (direccion !=null && !direccion.isEmpty()) {
            return direccion.split("-")[1].trim();
        } else {
            return "";
        }
    }
    
    public String getMunicipio() {
        if (direccion !=null && !direccion.isEmpty()) {
            return direccion.split("-")[2].trim();
        } else {
            return "";
        }
    }
    
    public String getCalle() {
        if (direccion !=null && !direccion.isEmpty()) {
            return direccion.split("-")[3].trim();
        } else {
            return "";
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getEsBaja() {
        return esBaja;
    }

    public void setEsBaja(int esBaja) {
        this.esBaja = esBaja;
    }

}
