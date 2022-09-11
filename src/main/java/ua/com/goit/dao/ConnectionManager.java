package ua.com.goit.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static final Properties PROPERTIES = new Properties();
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private static BlockingQueue<Connection> pool;

    static {
        initVariables();
        initConnectionPool();
    }

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection openConnection() {
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

    private static void initConnectionPool() {
        var poolSize = Integer.parseInt(PROPERTIES.getProperty("db.pool.size"));

        pool = new ArrayBlockingQueue<>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            pool.add(INSTANCE.openConnection());
        }
    }
}