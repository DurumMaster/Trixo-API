package trixo.api.trixo_api.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private double precio;

    private String talla;

    @Column(name = "user_ID")
    private String userID;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaCreacion;

    private int stock;

    private boolean activo;

    private String descripcion;

    private String materiales;

    private String envio;

    @Column(name = "valoracion")
    private double valoracion;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Image> imagenes = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Rating> ratings = new ArrayList<>();
    
    public Product() {}

    public Product(int id, String nombre, double precio, String talla, Timestamp fechaCreacion,
                   int stock, boolean activo, String descripcion, String materiales,
                   String envio, double valoracion, String userID) {
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
        this.valoracion = valoracion;
        this.userID = userID;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }

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

    public List<Image> getImagenes() { return imagenes; }
    public void setImagenes(List<Image> imagenes) { this.imagenes = imagenes; }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
