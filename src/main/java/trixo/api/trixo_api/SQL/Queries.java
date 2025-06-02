package trixo.api.trixo_api.SQL;

public class Queries {
    public static final String ADD_PRODUCT = "INSERT INTO " + ProductTable.PRODUCTS_TABLE + " (" +
            ProductTable.NAME + ", " +
            ProductTable.PRICE + ", " +
            ProductTable.SIZE + ", " +
            ProductTable.CREATION_DATE + ", " +
            ProductTable.STOCK + ", " +
            ProductTable.ACTIVE + ", " +
            ProductTable.DESCRIPTION + ", " +
            ProductTable.MATERIALS + ", " +
            ProductTable.SHIPPING + ", " +
            ProductTable.RATING + ", " +
            ProductTable.USER_ID +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String ADD_IMAGE = "INSERT INTO " + ImageProductTable.IMAGE_PRODUCTS_TABLE + " (" +
            ImageProductTable.PRODUCT_ID + ", " +
            ImageProductTable.URL +
            ") VALUES (?, ?)";

    public static final String ADD_RATING = "INSERT INTO " + RatingTable.RATINGS_TABLE + " (" +
            RatingTable.MESSAGE + ", " +
            RatingTable.USER_ID + ", " +
            RatingTable.RATING +
            ") VALUES (?, ?, ?)";

    public static final String ADD_PRODUCT_RATING = "INSERT INTO " + ProductRatingTable.PRODUCT_RATINGS_TABLE + " (" +
            ProductRatingTable.PRODUCT_ID + ", " +
            ProductRatingTable.RATING_ID +
            ") VALUES (?, ?)";

    public static final String ADD_CLIENT = "INSERT INTO " + ClientTable.CLIENT_TABLE + " (" +
            ClientTable.STRIPE_CUSTOMER_ID + ", " +
            ClientTable.NOMBRE + ", " +
            ClientTable.EMAIL + ", " +
            ClientTable.GDPR_CONSENT +
            ") VALUES (?, ?, ?, ?)";

    public static final String ADD_CLIENT_PAYMENT_METHOD = "INSERT INTO "
            + ClientPaymentMethodTable.CLIENT_PAYMENT_METHOD_TABLE + " (" +
            ClientPaymentMethodTable.CLIENTE_ID + ", " +
            ClientPaymentMethodTable.STRIPE_PAYMENT_METHOD_ID + ", " +
            ") VALUES (?, ?, ?)";

    public static final String GET_METHOD_BY_CLIENT_ID = "SELECT * FROM " + ClientPaymentMethodTable.CLIENT_PAYMENT_METHOD_TABLE +
            " WHERE " + ClientPaymentMethodTable.CLIENTE_ID + " = ?";
        
    public static final String GET_CLIENT_BY_ID = "SELECT * FROM " + ClientTable.CLIENT_TABLE +
            " WHERE " + ClientTable.ID + " = ?";

    public static final String GET_PRODUCT_AVG_RATING = "SELECT AVG(" + RatingTable.RATING + ") AS average_rating " +
            "FROM " + RatingTable.RATINGS_TABLE +
            " INNER JOIN " + ProductRatingTable.PRODUCT_RATINGS_TABLE +
            " ON " + RatingTable.ID + " = " + ProductRatingTable.RATING_ID +
            " WHERE " + ProductRatingTable.PRODUCT_ID + " = ?";

    public static final String GET_PRODUCTS_RATING = "SELECT * FROM " + ProductRatingTable.PRODUCT_RATINGS_TABLE +
            " INNER JOIN " + RatingTable.RATINGS_TABLE +
            " ON " + ProductRatingTable.RATING_ID + " = " + RatingTable.ID +
            " WHERE " + ProductRatingTable.PRODUCT_ID + " = ?";

    public static final String DELETE_RATING = "DELETE FROM " + RatingTable.RATINGS_TABLE +
            " WHERE " + RatingTable.ID + " = ?";

    public static final String GET_IMAGES_BY_PRODUCT_ID = "SELECT * FROM " + ImageProductTable.IMAGE_PRODUCTS_TABLE +
            " WHERE " + ImageProductTable.PRODUCT_ID + " = ?";

    public static final String GET_ACTIVE_PRODUCTS = "SELECT * FROM " + ProductTable.PRODUCTS_TABLE +
            " WHERE " + ProductTable.ACTIVE + " = true";

    public static final String REDUCE_STOCK_BY_1 = "UPDATE " + ProductTable.PRODUCTS_TABLE +
            " SET " + ProductTable.STOCK + " = " + ProductTable.STOCK + " - 1 " +
            " WHERE " + ProductTable.ID + " = ?";

    public static final String GET_PRODUCT_RATING = "SELECT AVG(" + ProductTable.RATING + ") AS average_rating " +
            "FROM " + ProductTable.PRODUCTS_TABLE +
            " WHERE " + ProductTable.ID + " = ?";

    public static final String SET_ACTIVE_TO_INACTIVE = "UPDATE " + ProductTable.PRODUCTS_TABLE +
            " SET " + ProductTable.ACTIVE + " = false " +
            " WHERE " + ProductTable.ID + " = ?";

    public static class ProductTable {
        public static final String PRODUCTS_TABLE = "productos";
        public static final String ID = "id";
        public static final String NAME = "nombre";
        public static final String PRICE = "precio";
        public static final String SIZE = "talla";
        public static final String CREATION_DATE = "fecha_creacion";
        public static final String STOCK = "stock";
        public static final String ACTIVE = "activo";
        public static final String DESCRIPTION = "descripcion";
        public static final String MATERIALS = "materiales";
        public static final String SHIPPING = "envio";
        public static final String RATING = "valoracion";
        public static final String USER_ID = "user_ID";
    }

    public static class ImageProductTable {
        public static final String IMAGE_PRODUCTS_TABLE = "imagenes_producto";
        public static final String ID = "id";
        public static final String PRODUCT_ID = "producto_id";
        public static final String URL = "url";
    }

    public static class RatingTable {
        public static final String RATINGS_TABLE = "valoraciones";
        public static final String ID = "id";
        public static final String MESSAGE = "mensaje";
        public static final String USER_ID = "user_id";
        public static final String RATING = "valoracion";
    }

    public static class ProductRatingTable {
        public static final String PRODUCT_RATINGS_TABLE = "productos_valoraciones";
        public static final String PRODUCT_ID = "id_producto";
        public static final String RATING_ID = "id_valoracion";
    }

    public static class ClientTable {
        public static final String CLIENT_TABLE = "clientes";
        public static final String ID = "id";
        public static final String STRIPE_CUSTOMER_ID = "stripe_customer_id";
        public static final String NOMBRE = "nombre";
        public static final String EMAIL = "email";
        public static final String FECHA_CREACION = "fecha_creacion";
        public static final String GDPR_CONSENT = "gdpr_consent";
    }

    public static class ClientPaymentMethodTable {
        public static final String CLIENT_PAYMENT_METHOD_TABLE = "cliente_metodos_pago";
        public static final String ID = "id";
        public static final String CLIENTE_ID = "cliente_id";
        public static final String STRIPE_PAYMENT_METHOD_ID = "stripe_payment_method_id";
        public static final String FECHA_CREACION = "fecha_creacion";
    }
}
