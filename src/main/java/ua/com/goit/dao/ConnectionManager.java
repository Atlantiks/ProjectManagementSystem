package ua.com.goit.dao;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static final Properties PROPERTIES = new Properties();
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private static BlockingQueue<Connection> pool;
    private static List<Connection> allConnections;

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
        allConnections = new ArrayList<>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            var connection = INSTANCE.openConnection();
            var proxyConnection = (Connection)
                    Proxy.newProxyInstance(
                            ConnectionManager.class.getClassLoader(),
                            new Class[]{Connection.class},
                    (proxy, method, args) ->
                            method.getName().equals("close") ?
                            pool.add((Connection) proxy) : method.invoke(connection, args));
            pool.add(proxyConnection);
            allConnections.add(connection);
        }
    }

    public void closeConnectionPool() {
        allConnections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
