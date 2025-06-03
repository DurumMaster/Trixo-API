package trixo.api.trixo_api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDto {
    private int id;
    private String nombre;
    private double precio;
    private String talla;
    private LocalDateTime fechaCreacion;
    private int stock;
    private boolean activo;
    private String descripcion;
    private String materiales;
    private String envio;
    private String userID;
    private double valoracion;
    private List<String> images;

    public ProductDto() {}

    public ProductDto(int id, String nombre, double precio, String talla,
                      LocalDateTime fechaCreacion, int stock, boolean activo,
                      String descripcion, String materiales, String envio, String userID,
                      double valoracion, List<String> images) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.talla = talla;
        this.fechaCreacion = fechaCreacion;
        this.stock = stock;
        this.activo = activo;
        this.descripcion = descripcion;
        this.materiales = materiales;
        this.envio = envio;
        this.userID = userID;
        this.valoracion = valoracion;
        this.images = images;
    }
    // Getters y Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMateriales() { return materiales; }
    public void setMateriales(String materiales) { this.materiales = materiales; }

    public String getEnvio() { return envio; }
    public void setEnvio(String envio) { this.envio = envio; }

    public double getValoracion() { return valoracion; }
    public void setValoracion(double valoracion) { this.valoracion = valoracion; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
