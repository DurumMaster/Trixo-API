package trixo.api.trixo_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trixo.api.trixo_api.entities.Product;
import trixo.api.trixo_api.repositories.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public boolean addProduct(Product product, List<String> images) {
        return productRepository.addProduct(product, images);
    }

    public List<Product> getActiveProducts(){
        return productRepository.getActiveProducts();
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
}
