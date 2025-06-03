package trixo.api.trixo_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import trixo.api.trixo_api.entities.Product;
import trixo.api.trixo_api.entities.Rating;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.imagenes WHERE p.activo = true")
    List<Product> findActiveProductsWithImages();

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.stock = p.stock - 1 WHERE p.id = :productId AND p.stock > 0")
    int reduceStockBy1(int productId);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.product.id = :productId")
    Double getAverageRatingByProductId(@Param ("productId") int productId);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.activo = false WHERE p.id = :productId")
    int deactivateProduct(@Param ("productId") int productId);

    @Query("SELECT r FROM Rating r WHERE r.product.id = :productId")
    List<Rating> findRatingsByProductId(@Param ("productId") int productId);

}
    // public boolean addProduct(Product product, List<String> images) {
    //     Connection con = null;
    //     PreparedStatement psProduct = null;
    //     PreparedStatement psImage = null;
    //     ResultSet generatedKeys = null;

    //     try {
    //         con = SQLConnection.getConnection(); // Usa tu método de conexión
    //         con.setAutoCommit(false); // Inicia la transacción

    //         // Insertar producto
    //         psProduct = con.prepareStatement(Queries.ADD_PRODUCT, Statement.RETURN_GENERATED_KEYS);
    //         psProduct.setString(1, product.getNombre());
    //         psProduct.setDouble(2, product.getPrecio());
    //         psProduct.setString(3, product.getTalla());
    //         psProduct.setTimestamp(4, product.getFechaCreacion() != null ?
    //             new Timestamp(product.getFechaCreacion().toDate().getTime()) :
    //             new Timestamp(System.currentTimeMillis()));
    //         psProduct.setInt(5, product.getStock());
    //         psProduct.setBoolean(6, true);
    //         psProduct.setString(7, product.getDescripcion());
    //         psProduct.setString(8, product.getMateriales());
    //         psProduct.setString(9, product.getEnvio());
    //         psProduct.setDouble(10, 0);
    //         psProduct.setString(11, product.getUserID());

    //         int rowsAffected = psProduct.executeUpdate();

    //         if (rowsAffected == 0) {
    //             con.rollback();
    //             return false;
    //         }

    //         generatedKeys = psProduct.getGeneratedKeys();
    //         if (generatedKeys.next()) {
    //             int productoId = generatedKeys.getInt(1);

    //             psImage = con.prepareStatement(Queries.ADD_IMAGE);
    //             for (String imageUrl : images) {
    //                 psImage.setInt(1, productoId);
    //                 psImage.setString(2, imageUrl);
    //                 psImage.addBatch();
    //             }
    //             psImage.executeBatch();

    //             con.commit();
    //             return true;

    //         } else {
    //             con.rollback();
    //             return false;
    //         }

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         try {
    //             if (con != null) con.rollback();
    //         } catch (SQLException ex) {
    //             ex.printStackTrace();
    //         }
    //         return false;
    //     } finally {
    //         try {
    //             if (generatedKeys != null) generatedKeys.close();
    //             if (psProduct != null) psProduct.close();
    //             if (psImage != null) psImage.close();
    //             if (con != null) {
    //                 con.setAutoCommit(true);
    //                 con.close();
    //             }
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // public List<ProductDto> getActiveProducts() {
    //     Connection con = null;
    //     PreparedStatement ps = null;
    //     ResultSet rs = null;
    //     List<Product> products = new ArrayList<>();
    //     List<ProductDto> productDtos = new ArrayList<>();

    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.GET_ACTIVE_PRODUCTS);
    //         rs = ps.executeQuery();
    //         while (rs.next()) {
    //             Product product = new Product(
    //                 rs.getInt(ProductTable.ID),
    //                 rs.getString(ProductTable.NAME),
    //                 rs.getDouble(ProductTable.PRICE),
    //                 rs.getString(ProductTable.SIZE),
    //                 com.google.cloud.Timestamp.of(rs.getTimestamp(ProductTable.CREATION_DATE)),
    //                 rs.getInt(ProductTable.STOCK),
    //                 rs.getBoolean(ProductTable.ACTIVE),
    //                 rs.getString(ProductTable.DESCRIPTION),
    //                 rs.getString(ProductTable.MATERIALS),
    //                 rs.getString(ProductTable.SHIPPING),
    //                 rs.getDouble(ProductTable.RATING),
    //                 rs.getString(ProductTable.USER_ID)
    //             );
    //             products.add(product);
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             if (rs != null) rs.close();
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }

    //     for (Product product : products) {
    //         List<String> images = new ArrayList<>();
    //         Connection imgCon = null;
    //         PreparedStatement imgPs = null;
    //         ResultSet imgRs = null;
    //         try {
    //             imgCon = SQLConnection.getConnection();
    //             imgPs = imgCon.prepareStatement(Queries.GET_IMAGES_BY_PRODUCT_ID);
    //             imgPs.setInt(1, product.getId());
    //             imgRs = imgPs.executeQuery();
    //             while (imgRs.next()) {
    //                 images.add(imgRs.getString("url"));
    //             }
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         } finally {
    //             try {
    //                 if (imgRs != null) imgRs.close();
    //                 if (imgPs != null) imgPs.close();
    //                 if (imgCon != null) imgCon.close();
    //             } catch (SQLException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //         productDtos.add(new ProductDto(product, images));
    //     }

    //     return productDtos;
    // }

    // public boolean reduceStockBy1(int productId) {
    //     Connection con = null;
    //     PreparedStatement ps = null;

    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.REDUCE_STOCK_BY_1);
    //         ps.setInt(1, productId);
    //         int rowsAffected = ps.executeUpdate();
    //         return rowsAffected > 0;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     } finally {
    //         try {
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // public double getProductRating(int productId) {
    //     Connection con = null;
    //     PreparedStatement ps = null;
    //     ResultSet rs = null;

    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.GET_PRODUCT_AVG_RATING);
    //         ps.setInt(1, productId);
    //         rs = ps.executeQuery();

    //         if (rs.next()) {
    //             double avg = rs.getDouble("average_rating");
    //             if (rs.wasNull()) {
    //                 return 0.0;
    //             }
    //             return avg;
    //         } else {
    //             return 0.0;
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return 0.0;
    //     } finally {
    //         try {
    //             if (rs != null) rs.close();
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // public boolean setActiveToInactive(int productId) {
    //     Connection con = null;
    //     PreparedStatement ps = null;

    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.SET_ACTIVE_TO_INACTIVE);
    //         ps.setInt(1, productId);
    //         int rowsAffected = ps.executeUpdate();
    //         return rowsAffected > 0;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     } finally {
    //         try {
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // public boolean addRating(Rating rating, int productId) {
    //     Connection con = null;
    //     PreparedStatement ps = null;
    //     ResultSet generatedKeys = null;

    //     try {
    //         con = SQLConnection.getConnection();
    //         con.setAutoCommit(false);

    //         ps = con.prepareStatement(Queries.ADD_RATING, Statement.RETURN_GENERATED_KEYS);
    //         ps.setString(1, rating.getMessage());
    //         ps.setString(2, rating.getUserID());
    //         ps.setDouble(3, rating.getRating());
    //         int rowsAffected = ps.executeUpdate();

    //         if (rowsAffected == 0) {
    //             con.rollback();
    //             return false;
    //         }

    //         generatedKeys = ps.getGeneratedKeys();
    //         if (generatedKeys.next()) {
    //             int ratingId = generatedKeys.getInt(1);

    //             ps = con.prepareStatement(Queries.ADD_PRODUCT_RATING);
    //             ps.setInt(1, productId);
    //             ps.setInt(2, ratingId);
    //             ps.executeUpdate();
    //             con.commit();
                
    //             ps = con.prepareStatement(Queries.UPDATE_PRODUCT_RATING_AVG);
    //             ps.setInt(1, productId);
    //             ps.setInt(2, productId);
    //             ps.executeUpdate();
    //             con.commit();

    //             return true;                
    //         } else {
    //             con.rollback();
    //             return false;
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         try {
    //             if (con != null) con.rollback();
    //         } catch (SQLException ex) {
    //             ex.printStackTrace();
    //         }
    //         return false;
    //     } finally {
    //         try {
    //             if (generatedKeys != null) generatedKeys.close();
    //             if (ps != null) ps.close();
    //             if (con != null) {
    //                 con.setAutoCommit(true);
    //                 con.close();
    //             }
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // public boolean deleteRating(int ratingId) {
    //     Connection con = null;
    //     PreparedStatement ps = null;

    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.DELETE_RATING);
    //         ps.setInt(1, ratingId);
    //         int rowsAffected = ps.executeUpdate();
    //         return rowsAffected > 0;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     } finally {
    //         try {
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // public List<Rating> getProductRatings(int productId) {
    //     Connection con = null;
    //     PreparedStatement ps = null;
    //     ResultSet rs = null;
    //     List<Rating> ratings = new ArrayList<>();

    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.GET_PRODUCTS_RATING);
    //         ps.setInt(1, productId);
    //         rs = ps.executeQuery();

    //         while (rs.next()) {
    //             Rating rating = new Rating(
    //                 rs.getInt(Queries.RatingTable.ID),
    //                 rs.getString(Queries.RatingTable.MESSAGE),
    //                 rs.getDouble(Queries.RatingTable.RATING),
    //                 rs.getString(Queries.RatingTable.USER_ID)
    //             );
    //             ratings.add(rating);
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             if (rs != null) rs.close();
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    //     return ratings;
    // }

        // public Customer createCustomer(String email, String name) {
    //     try {
    //         CustomerCreateParams params = CustomerCreateParams.builder()
    //             .setEmail(email)
    //             .setName(name)
    //             .build();

    //         return Customer.create(params);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
    
    // public PaymentIntent createPaymentIntent(Long amount, String currency, String customerID, String paymentMethod) {
    //     try{
    //         PaymentIntentCreateParams.Builder builder = PaymentIntentCreateParams.builder()
    //                 .setAmount(amount)
    //                 .setCurrency(currency)
    //             .setPaymentMethod(paymentMethod);

    //         if(customerID != null && !customerID.isEmpty()){
    //             builder.setCustomer(customerID);
    //         }

    //         PaymentIntentCreateParams params = builder.build();
    //         return PaymentIntent.create(params);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // public void insertUser(String id, CustomerDto request) {
    //     Connection con = null;
    //     PreparedStatement ps = null;

    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.ADD_CLIENT);
    //         ps.setString(1, id);
    //         ps.setString(2, request.getEmail());
    //         ps.setString(3, request.getName());
    //         ps.setBoolean(4, request.isGdprConsent());
    //         ps.executeUpdate();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // public CustomerDto getCustomer(String customerID) {
    //     Connection con = null;
    //     PreparedStatement ps = null;
    //     ResultSet rs = null;
    //     CustomerDto customerDto = new CustomerDto();
    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.GET_CLIENT_BY_ID);
    //         ps.setString(1, customerID);
    //         rs = ps.executeQuery();

    //         if (rs.next()) {
    //             customerDto.setEmail(rs.getString(Queries.ClientTable.EMAIL));
    //             customerDto.setName(rs.getString(Queries.ClientTable.NOMBRE));
    //             customerDto.setGdprConsent(rs.getBoolean(Queries.ClientTable.GDPR_CONSENT));
    //         }
    //         return customerDto;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return null;
    //     } finally {
    //         try {
    //             if (rs != null) rs.close();
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //             return null;
    //         }
    //     }
    // }

    // public void insertMethod(String customerID, String paymentMethod) {
    //     Connection con = null;
    //     PreparedStatement ps = null;
    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.ADD_CLIENT_PAYMENT_METHOD);
    //         ps.setString(1, customerID);
    //         ps.setString(2, paymentMethod);
    //         ps.executeUpdate();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // public boolean hasPaymentMethod(String customerID, String paymentMethod) {
    //     Connection con = null;
    //     PreparedStatement ps = null;
    //     ResultSet rs = null;
    //     try {
    //         con = SQLConnection.getConnection();
    //         ps = con.prepareStatement(Queries.GET_METHOD_BY_CLIENT_ID);
    //         ps.setString(1, customerID);
    //         rs = ps.executeQuery();

    //         while (rs.next()) {
    //             if (rs.getString("stripe_payment_method_id").equals(paymentMethod)) {
    //                 return true;
    //             }
    //         }
    //         return false;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     } finally {
    //         try {
    //             if (rs != null) rs.close();
    //             if (ps != null) ps.close();
    //             if (con != null) con.close();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //             return false;
    //         }
    //     }
    // }
