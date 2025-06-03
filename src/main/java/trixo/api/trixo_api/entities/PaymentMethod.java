package trixo.api.trixo_api.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "cliente_metodos_pago", uniqueConstraints = {
        @UniqueConstraint(columnNames = "stripe_payment_method_id")
})
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "cliente_metodos_pago_ibfk_1"))
    private Customer cliente;

    @Column(name = "stripe_payment_method_id", nullable = false, length = 100)
    private String stripePaymentMethodId;

    @Column(name = "fecha_creacion", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaCreacion;

    // Constructors
    public PaymentMethod() {}

    public PaymentMethod(Customer cliente, String stripePaymentMethodId) {
        this.cliente = cliente;
        this.stripePaymentMethodId = stripePaymentMethodId;
        this.fechaCreacion = Timestamp.from(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public Customer getCliente() {
        return cliente;
    }

    public void setCliente(Customer cliente) {
        this.cliente = cliente;
    }

    public String getStripePaymentMethodId() {
        return stripePaymentMethodId;
    }

    public void setStripePaymentMethodId(String stripePaymentMethodId) {
        this.stripePaymentMethodId = stripePaymentMethodId;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
