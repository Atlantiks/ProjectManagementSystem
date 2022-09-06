package ua.com.goit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionManager {
    private static final Properties PROPERTIES = new Properties();

    private ConnectionManager() {
    }

    public static Connection openConnection() {
        initVariables();
        try {
            return DriverManager.getConnection(
                    PROPERTIES.getProperty("db.url"),
                    PROPERTIES.getProperty("user"),
                    PROPERTIES.getProperty("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initVariables() {
        try (var paramStream = ConnectionManager.class.getClassLoader()
                             .getResourceAsStream("application.properties")) {
            PROPERTIES.load(paramStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PROPERTIES.setProperty("user",System.getProperty("user"));
        PROPERTIES.setProperty("password",System.getProperty("password"));
    }
}
