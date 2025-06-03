package trixo.api.trixo_api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "imagenes_producto")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonBackReference
    private Product producto;

    private String url;

    public Image() {}

    public Image(int id, Product producto, String url) {
        this.id = id;
        this.producto = producto;
        this.url = url;
    }

    public Image(Product producto, String url) {
        this.producto = producto;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public Product getProducto() {
        return producto;
    }

    public String getUrl() {
        return url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
