package trixo.api.trixo_api.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "stripe_customer_id"),
        @UniqueConstraint(columnNames = "email")
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "stripe_customer_id", nullable = false, length = 50)
    private String stripeCustomerId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "fecha_creacion", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaCreacion;

    @Column(name = "gdpr_consent", nullable = false)
    private boolean gdprConsent;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentMethod> metodosPago = new ArrayList<>();

    // Constructors
    public Customer() {}

    public Customer(String stripeCustomerId, String nombre, String email, boolean gdprConsent) {
        this.stripeCustomerId = stripeCustomerId;
        this.nombre = nombre;
        this.email = email;
        this.gdprConsent = gdprConsent;
        this.fechaCreacion = Timestamp.from(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isGdprConsent() {
        return gdprConsent;
    }

    public void setGdprConsent(boolean gdprConsent) {
        this.gdprConsent = gdprConsent;
    }

    public List<PaymentMethod> getMetodosPago() {
        return metodosPago;
    }

    public void setMetodosPago(List<PaymentMethod> metodosPago) {
        this.metodosPago = metodosPago;
    }
}
