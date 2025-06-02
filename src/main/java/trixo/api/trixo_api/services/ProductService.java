package trixo.api.trixo_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;

import trixo.api.trixo_api.dto.CustomerDto;
import trixo.api.trixo_api.dto.ProductDto;
import trixo.api.trixo_api.entities.Product;
import trixo.api.trixo_api.entities.Rating;
import trixo.api.trixo_api.repositories.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public boolean addProduct(Product product, List<String> images) {
        return productRepository.addProduct(product, images);
    }

    public List<ProductDto> getActiveProducts(){
        return productRepository.getActiveProducts();
    }
    
    public boolean deleteRating(int ratingId){
        return productRepository.deleteRating(ratingId);
    }

    public boolean addRating(Rating rating, int productId) {
        return productRepository.addRating(rating, productId);
    }

    public List<Rating> getRatingsByProductId(int productId) {
        return productRepository.getProductRatings(productId);
    }

    public boolean reduceProductStock(int productId){
        return productRepository.reduceStockBy1(productId);
    }

    public double getProductRating(int productId) {
        return productRepository.getProductRating(productId);
    }

    public boolean setActiveToInactive(int productId) {
        return productRepository.setActiveToInactive(productId);
    }

    public Customer createCustomer(String email, String name) {
        return productRepository.createCustomer(email, name);
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency, String customerID) {
        return productRepository.createPaymentIntent(amount, currency, customerID);
    }

    public void insertUser(String id, CustomerDto request) {
        productRepository.insertUser(id, request);
    }

    public CustomerDto getCustomer(String customerID) {
        return productRepository.getCustomer(customerID);
    }

    public void insertMethod(String customerID, String paymentMethod) {
        productRepository.insertMethod(customerID, paymentMethod);
    }

    public boolean hasPaymentMethod(String customerID, String paymentMethod) {
        return productRepository.hasPaymentMethod(customerID, paymentMethod);
    }

}
