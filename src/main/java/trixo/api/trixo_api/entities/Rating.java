package trixo.api.trixo_api.entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "valoraciones")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mensaje", nullable = false, length = 200)
    private String message;

    @Column(name = "valoracion", nullable = false)
    private double rating;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;

    @CreationTimestamp
    @Column(name = "fecha_creacion", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fecha_creacion;

    public Rating() {
    }

    public Rating(int id, String message, double rating, String userID, Timestamp fecha_creacion) {
        this.id = id;
        this.message = message;
        this.rating = rating;
        this.userID = userID;
        this.fecha_creacion = fecha_creacion;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public double getRating() {
        return rating;
    }

    public String getUserID() {
        return userID;
    }

    public Timestamp getFecha_creacion() {
        return fecha_creacion;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFecha_creacion(Timestamp fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
