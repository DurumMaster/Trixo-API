package trixo.api.trixo_api.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import trixo.api.trixo_api.Mappers.ProductMapper;
import trixo.api.trixo_api.dto.CustomerDto;
import trixo.api.trixo_api.dto.ProductDto;
import trixo.api.trixo_api.entities.Customer;
import trixo.api.trixo_api.entities.Image;
import trixo.api.trixo_api.entities.PaymentMethod;
import trixo.api.trixo_api.entities.Product;
import trixo.api.trixo_api.entities.Rating;
import trixo.api.trixo_api.repositories.CustomerRepository;
import trixo.api.trixo_api.repositories.PaymentRepository;
import trixo.api.trixo_api.repositories.ProductRepository;
import trixo.api.trixo_api.repositories.RatingRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public boolean addProduct(Product product, List<String> imageUrls) {
        product.setFechaCreacion(Timestamp.from(Instant.now()));
        product.setActivo(true);

        List<Image> imagenes = imageUrls.stream()
            .map(url -> {
                Image image = new Image();
                image.setUrl(url);
                image.setProducto(product);
                return image;
            })
        .toList();

        product.setImagenes(imagenes);
        productRepository.save(product);
        return true;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getActiveProducts() {
        List<Product> products = productRepository.findActiveProductsWithImages();
        return products.stream().map(ProductMapper::toDto).toList();
    }
    
    public boolean deleteRating(int ratingId) {
        if (ratingRepository.existsById(ratingId)) {
            ratingRepository.deleteById(ratingId);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addRating(Rating rating, int productId) {
        rating.setProduct(productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found")));
        Rating savedRating = ratingRepository.save(rating);
        Double avgRating = productRepository.getAverageRatingByProductId(productId);
        if (avgRating == null) avgRating = 0.0;

        Product product = rating.getProduct();
        product.setValoracion(avgRating);
        productRepository.save(product);

        if (savedRating != null) {
            return true;
        }
        return false;
    }

    public List<Rating> getRatingsByProductId(int productId) {
        return productRepository.findRatingsByProductId(productId);
    }

    public boolean reduceProductStock(int productId) {
        return productRepository.reduceStockBy1(productId) > 0;
    }

    public double getProductRating(int productId) {
        Double rating = productRepository.getAverageRatingByProductId(productId);
        return rating != null ? rating : 0.0;
    }

    public boolean setActiveToInactive(int productId) {
        return productRepository.deactivateProduct(productId) > 0;
    }

    public void insertUser(String stripeCustomerId, CustomerDto dto) {
        Customer customer = new Customer();
        customer.setStripeCustomerId(stripeCustomerId);
        customer.setEmail(dto.getEmail());
        customer.setNombre(dto.getName());
        customer.setGdprConsent(dto.isGdprConsent());
        customer.setFechaCreacion(new Timestamp(System.currentTimeMillis()));

        customerRepository.save(customer);
    }

    public CustomerDto getCustomer(String stripeCustomerId) {
        return customerRepository.findByStripeCustomerId(stripeCustomerId)
            .map(customer -> {
                CustomerDto dto = new CustomerDto();
                dto.setEmail(customer.getEmail());
                dto.setName(customer.getNombre());
                dto.setGdprConsent(customer.isGdprConsent());
                return dto;
        }).orElse(null);
    }

    public void insertMethod(String customerID, String paymentMethodId) {
        Customer customer = customerRepository.findByStripeCustomerId(customerID)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setCliente(customer);
        paymentMethod.setStripePaymentMethodId(paymentMethodId);
        paymentMethod.setFechaCreacion(new Timestamp(System.currentTimeMillis()));

        paymentRepository.save(paymentMethod);
    }

    public boolean hasPaymentMethod(String customerID, String paymentMethodId) {
        return paymentRepository
            .existsByCliente_StripeCustomerIdAndStripePaymentMethodId(customerID, paymentMethodId);
    }

}
