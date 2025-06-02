package trixo.api.trixo_api.SQL;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SQLConnection {

    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream inputStream = SQLConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (inputStream == null) {
                throw new RuntimeException("Archivo 'application.properties' no encontrado en resources.");
            }

            prop.load(inputStream);

            url = prop.getProperty("spring.datasource.url");
            user = prop.getProperty("spring.datasource.username");
            password = prop.getProperty("spring.datasource.password");

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar configuración de base de datos", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo establecer la conexión a la base de datos", e);
        }
    }
}
