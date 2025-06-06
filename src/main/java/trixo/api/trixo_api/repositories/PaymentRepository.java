package trixo.api.trixo_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import trixo.api.trixo_api.entities.PaymentMethod;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentMethod, Integer> {
    @Query("SELECT COUNT(pm) > 0 FROM PaymentMethod pm WHERE pm.cliente.stripeCustomerId = ?1 AND pm.stripePaymentMethodId = ?2")
    boolean existsByCliente_StripeCustomerIdAndStripePaymentMethodId(String customerId, String paymentMethodId);

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.cliente.stripeCustomerId = ?1")
    PaymentMethod findByCliente_StripeCustomerId(String customerId);
}
