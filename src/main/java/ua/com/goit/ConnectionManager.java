package ua.com.goit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String USERNAME = System.getProperty("user");
    private static final String PASSWORD = System.getProperty("password");
    private static final String URL = "jdbc:postgresql://host.docker.internal:49153/go_it";

    private ConnectionManager() {
    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
