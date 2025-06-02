package trixo.api.trixo_api.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import trixo.api.trixo_api.SQL.Queries;
import trixo.api.trixo_api.SQL.SQLConnection;
import trixo.api.trixo_api.SQL.Queries.ProductTable;
import trixo.api.trixo_api.entities.Product;

@Repository
public class ProductRepository {

    public boolean addProduct(Product product, List<String> images) {
        Connection con = null;
        PreparedStatement psProduct = null;
        PreparedStatement psImage = null;
        ResultSet generatedKeys = null;

        try {
            con = SQLConnection.getConnection(); // Usa tu método de conexión
            con.setAutoCommit(false); // Inicia la transacción

            // Insertar producto
            psProduct = con.prepareStatement(Queries.ADD_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            psProduct.setString(1, product.getNombre());
            psProduct.setDouble(2, product.getPrecio());
            psProduct.setString(3, product.getTalla());
            psProduct.setTimestamp(4, product.getFechaCreacion() != null ?
                new Timestamp(product.getFechaCreacion().toDate().getTime()) :
                new Timestamp(System.currentTimeMillis()));
            psProduct.setInt(5, product.getStock());
            psProduct.setBoolean(6, true);
            psProduct.setString(7, product.getDescripcion());
            psProduct.setString(8, product.getMateriales());
            psProduct.setString(9, product.getEnvio());
            psProduct.setDouble(10, 0);
            psProduct.setString(11, product.getUserID());

            int rowsAffected = psProduct.executeUpdate();

            if (rowsAffected == 0) {
                con.rollback();
                return false;
            }

            generatedKeys = psProduct.getGeneratedKeys();
            if (generatedKeys.next()) {
                int productoId = generatedKeys.getInt(1);

                // Insertar imágenes
                psImage = con.prepareStatement(Queries.ADD_IMAGE);
                for (String imageUrl : images) {
                    psImage.setInt(1, productoId);
                    psImage.setString(2, imageUrl);
                    psImage.addBatch();
                }
                psImage.executeBatch();

                con.commit();
                return true;

            } else {
                con.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (psProduct != null) psProduct.close();
                if (psImage != null) psImage.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Product> getActiveProducts() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();

        try {
            con = SQLConnection.getConnection();
            ps = con.prepareStatement(Queries.GET_ACTIVE_PRODUCTS);
            rs = ps.executeQuery();
            Product product = null;
            while (rs.next()) {
                product = new Product(
                    rs.getInt(ProductTable.ID),
                    rs.getString(ProductTable.NAME),
                    rs.getDouble(ProductTable.PRICE),
                    rs.getString(ProductTable.SIZE),
                    com.google.cloud.Timestamp.of(rs.getTimestamp(ProductTable.CREATION_DATE)),
                    rs.getInt(ProductTable.STOCK),
                    rs.getBoolean(ProductTable.ACTIVE),
                    rs.getString(ProductTable.DESCRIPTION),
                    rs.getString(ProductTable.MATERIALS),
                    rs.getString(ProductTable.SHIPPING),
                    rs.getDouble(ProductTable.RATING)
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    public boolean reduceStockBy1(int productId) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = SQLConnection.getConnection();
            ps = con.prepareStatement(Queries.REDUCE_STOCK_BY_1);
            ps.setInt(1, productId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public double getProductRating(int productId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = SQLConnection.getConnection();
            ps = con.prepareStatement(Queries.GET_PRODUCT_RATING);
            ps.setInt(1, productId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("average_rating");
            } else {
                return 0.0; // Si no hay valoraciones, retorna 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean setActiveToInactive(int productId) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = SQLConnection.getConnection();
            ps = con.prepareStatement(Queries.SET_ACTIVE_TO_INACTIVE);
            ps.setInt(1, productId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}

