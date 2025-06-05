package trixo.api.trixo_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import trixo.api.trixo_api.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
    @Query("SELECT c FROM Customer c WHERE c.stripeCustomerId = ?1")
    Optional<Customer> findByStripeCustomerId(String stripeCustomerId);

    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    boolean existsByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Optional<Customer> findByEmail(String email);
}
