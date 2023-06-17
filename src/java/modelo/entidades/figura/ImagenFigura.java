
package modelo.entidades.figura;

/**
 *
 * @author Angel
 */
public class ImagenFigura {

    private int id;
    private String url;
    private int idFigura;

    public ImagenFigura(int id,String url, int idFigura) {
        this.id = id;
        this.url = url;
        this.idFigura = idFigura;
    }
    
    public ImagenFigura(String url, int idFigura) {
        this.url = url;
        this.idFigura = idFigura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdFigura() {
        return idFigura;
    }

    public void setIdFigura(int idFigura) {
        this.idFigura = idFigura;
    }
    
    
}