package ua.com.goit.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import ua.com.goit.entity.Developer;

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
    private static SessionFactory sessionFactory;

    static {
        loadDriver();
        initVariables();
        initConnectionPool();
        initHibernate();
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

    public Session getHibernateSession() {
        return sessionFactory.openSession();
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

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
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

        System.out.println("\033[0;92mDatabase connection pool created\033[0m\n");
    }

    private static void initHibernate() {
        Configuration config = new Configuration();
        config.configure();

        config.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        config.addAnnotatedClass(Developer.class);

        sessionFactory = config.buildSessionFactory();
    }

    public void closeConnectionPool() {
        allConnections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        System.out.println("\n\033[0;93mDatabase connection pool closed\033[0m");
    }
}
