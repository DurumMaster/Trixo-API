package trixo.api.trixo_api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trixo.api.trixo_api.dto.ProductRegistration;
import trixo.api.trixo_api.entities.Product;
import trixo.api.trixo_api.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActiveProducts() {
        List<Product> products = new ArrayList<>();
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
    public ResponseEntity<String> reduceProductStock(@PathVariable int productId) {
        boolean success = productService.reduceProductStock(productId);
        if (!success) {
            return ResponseEntity.badRequest().body("Error reducing stock for product with ID: " + productId);
        }
        return ResponseEntity.ok("Stock reduced successfully for product with ID: " + productId);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRegistration product) {

        boolean res = productService.addProduct(product.getProduct(), product.getImages());
        if (!res) {
            return ResponseEntity.badRequest().body("Error adding product.");
        }
        
        return ResponseEntity.ok("Product added successfully.");
    }

    @PutMapping("/inactive")
    public ResponseEntity<String> deactivateProducts(){
        List<Product> products = productService.getActiveProducts();
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body("No active products to deactivate.");
        }
        for (Product product : products) {
            boolean success = productService.setActiveToInactive(product.getId());
            if (!success) {
                return ResponseEntity.badRequest().body("Error deactivating product with ID: " + product.getId());
            }
        }
        return ResponseEntity.ok("All active products have been deactivated successfully.");
    }
}
