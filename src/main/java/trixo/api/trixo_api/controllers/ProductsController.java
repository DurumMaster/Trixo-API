package trixo.api.trixo_api.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;

import trixo.api.trixo_api.Mappers.ProductMapper;
import trixo.api.trixo_api.dto.CustomerDto;
import trixo.api.trixo_api.dto.PaymentDto;
import trixo.api.trixo_api.dto.ProductDto;
import trixo.api.trixo_api.entities.Rating;
import trixo.api.trixo_api.services.ProductService;
import trixo.api.trixo_api.services.StripeService;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    
    @Autowired
    private ProductService productService;

    @Autowired
    private StripeService stripeService;

    @GetMapping("/active")
    public ResponseEntity<List<ProductDto>> getActiveProducts() {
        List<ProductDto> products = new ArrayList<>();
        products = productService.getActiveProducts();
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body(products);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}/rating")
    public ResponseEntity<Double> getProductRating(@PathVariable int productId) {
        double rating = productService.getProductRating(productId);
        if (rating < 0) {
            return ResponseEntity.badRequest().body(rating);
        }
        return ResponseEntity.ok(rating);
    }

    @PutMapping("/{productId}/reduce")
    public ResponseEntity<Boolean> reduceProductStock(@PathVariable int productId) {
        boolean success = productService.reduceProductStock(productId);
        if (!success) {
            return ResponseEntity.badRequest().body(success);
        }
        return ResponseEntity.ok(success);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductDto product) {

        boolean res = productService.addProduct(ProductMapper.toEntity(product), product.getImages());
        if (!res) {
            return ResponseEntity.badRequest().body("Error adding product.");
        }
        
        return ResponseEntity.ok("Product added successfully.");
    }

    @PutMapping("/inactive")
    public ResponseEntity<String> deactivateProducts(){
        List<ProductDto> products = productService.getActiveProducts();
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body("No active products to deactivate.");
        }
        for (ProductDto product : products) {
            boolean success = productService.setActiveToInactive(product.getId());
            if (!success) {
                return ResponseEntity.badRequest().body("Error deactivating product with ID: " + product.getId());
            }
        }
        return ResponseEntity.ok("All active products have been deactivated successfully.");
    }

    @GetMapping("/{productId}/ratings")
    public ResponseEntity<List<Rating>> getRatingsByProductId(@PathVariable int productId) {
        List<Rating> ratings = productService.getRatingsByProductId(productId);
        if (ratings.isEmpty()) {
            return ResponseEntity.badRequest().body(ratings);
        }
        return ResponseEntity.ok(ratings);
    }

    @PostMapping("/{productId}/ratings")
    public ResponseEntity<String> addRating(@PathVariable int productId, @RequestBody Rating rating) {
        boolean success = productService.addRating(rating, productId);
        if (!success) {
            return ResponseEntity.badRequest().body("Error adding rating.");
        }
        return ResponseEntity.ok("Rating added successfully.");
    }

    @DeleteMapping("/{ratingId}/rating")
    public ResponseEntity<String> deleteRating(@PathVariable int ratingId) {
        boolean success = productService.deleteRating(ratingId);
        if (!success) {
            return ResponseEntity.badRequest().body("Error deleting rating.");
        }
        return ResponseEntity.ok("Rating deleted successfully.");
    }

    //STRIPE

    @PostMapping("/customer")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto request) {
        try {
            Customer customer = stripeService.createCustomer(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", customer.getId());
            response.put("email", customer.getEmail());
            response.put("name", customer.getName());

            productService.insertUser(customer.getId(), request);

            return ResponseEntity.status(HttpStatus.SC_CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                                 .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("{paymentMethod}/payments")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentDto request, @PathVariable String paymentMethod) {
        try {
            Map<String, Object> automaticPaymentMethods = new HashMap<>();
            automaticPaymentMethods.put("enabled", true);
            automaticPaymentMethods.put("allow_redirects", "never"); // 🔑 evita los métodos que requieren redirección

            Map<String, Object> params = new HashMap<>();
            params.put("amount", request.getAmount()); // o el valor que uses
            params.put("currency", "eur"); // o "usd"
            params.put("customer", request.getCustomerID());
            params.put("payment_method", paymentMethod);
            params.put("setup_future_usage", "off_session");
            params.put("automatic_payment_methods", automaticPaymentMethods); // 👈 esto es nuevo

            PaymentIntent pi = PaymentIntent.create(params);

            CustomerDto customer = productService.getCustomer(request.getCustomerID());

            if(customer != null){
                if(customer.isGdprConsent()){
                    if(!productService.hasPaymentMethod(request.getCustomerID(), pi.getPaymentMethod())){
                        productService.insertMethod(request.getCustomerID(), pi.getPaymentMethod());
                    }
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("paymentIntentId", pi.getId());
            response.put("clientSecret", pi.getClientSecret());
            return ResponseEntity.status(HttpStatus.SC_CREATED).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                                 .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{email}/customer")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable String email) {
        String customerId = productService.getCustomerIdByEmail(email);
        CustomerDto customer = stripeService.getStripeCustomer(customerId);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{customerId}/payment-method")
    public ResponseEntity<Boolean> getPaymentIntent(@PathVariable String customerId) {
        try {
            PaymentIntent paymentIntent = stripeService.getPaymentIntent(customerId);
            if (paymentIntent == null) {
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(false);
            } else {
                return ResponseEntity.ok(true);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                                 .body(null);
        }
    }

    @PutMapping("/{customerId}/customer")
    public ResponseEntity<String> updateCustomer(@PathVariable String customerId, @RequestBody CustomerDto customerDto) {
        try {
            boolean updated = stripeService.updateCustomer(customerId, customerDto);
            if (!updated) {
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Customer not found.");
            }
            return ResponseEntity.ok("Customer updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                                 .body("Error updating customer: " + e.getMessage());
        }
    }
}
