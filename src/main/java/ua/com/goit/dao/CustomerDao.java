package ua.com.goit.dao;

import jakarta.persistence.PersistenceException;
import ua.com.goit.entity.Customer;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.view.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CustomerDao implements DataAccess<Integer, Customer> {
    private static final CustomerDao CUSTOMER_DAO = new CustomerDao();
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    private CustomerDao() {
    }

    public static CustomerDao getInstance() {
        return CUSTOMER_DAO;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        String query = SQL.SELECT_BY_ID.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()) {
                return Optional.of(new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("company"),
                        rs.getString("address")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Customer save(Customer customer, View view) {
        String query = SQL.INSERT.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            statement.setObject(3,customer.getCompany());
            statement.setObject(4,customer.getAddress());

            statement.executeUpdate();

            try(ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    customer.setId(generatedKey.getInt(1));
                } else {
                    throw new RuntimeException("No id was returned back.");
                }
            } catch (SQLException e) {
                System.out.println("Couldn't create new customer in database");
                throw new RuntimeException(e.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return customer;
    }

    public Customer save(Customer customer) {
        String query = SQL.INSERT.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            statement.setObject(3,customer.getCompany());
            statement.setObject(4,customer.getAddress());

            statement.executeUpdate();

            try(ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    customer.setId(generatedKey.getInt(1));
                } else {
                    throw new DataBaseOperationException("No id was returned back.");
                }
            } catch (SQLException e) {
                throw new DataBaseOperationException("Couldn't create new customer in database");
            }

        } catch (Exception e) {
            throw new DataBaseOperationException(e.getMessage());
        }
        return customer;
    }

    public Customer saveWithHibernate(Customer customer) {
        var hibernateSession = connectionManager.getHibernateSession();

        try {
            hibernateSession.beginTransaction();
            hibernateSession.persist(customer);
            hibernateSession.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DataBaseOperationException("Error occurred adding new customer");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> allCustomers = new ArrayList<>();
        String query = SQL.SELECT_ALL.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.executeQuery();

            var rs = statement.getResultSet();
            while (rs.next()) {
                allCustomers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getObject("company",String.class),
                        rs.getObject("address", String.class)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return allCustomers;
    }

    @Override
    public boolean remove(Customer customer) {
        String query = SQL.DELETE_BY_FULLNAME.command;

        int updatedRows;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            updatedRows = statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return updatedRows > 0;
    }

    @Override
    public boolean removeById(Integer id) {
        String query = SQL.DELETE_BY_ID.command;
        int result;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result > 0;
    }

    @Override
    public boolean update(Customer customer) {
        var query = SQL.UPDATE.command;
        int updatedRows = 0;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            statement.setObject(3,customer.getCompany(),Types.VARCHAR);
            statement.setObject(4,customer.getAddress(),Types.LONGVARCHAR);

            statement.setInt(5,customer.getId());

            updatedRows = statement.executeUpdate();

        } catch (Exception e) {
            throw new DataBaseOperationException(e.getMessage());
        }
        return updatedRows > 0;
    }

    @Override
    public int count() {
        String query = SQL.COUNT.command;
        try (var connection = connectionManager.getConnection();
             var st = connection.prepareStatement(query)) {
            if (st.executeQuery().next()) return st.getResultSet().getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return 0;
    }

    enum SQL {
        INSERT("INSERT INTO customers (first_name, last_name, company, address) " +
                "VALUES (?,?,?,?)"),

        SELECT_ALL ("SELECT id, first_name, last_name, company, address " +
                "FROM customers " +
                "ORDER BY id"),

        SELECT_BY_ID("SELECT id, first_name, last_name, company, address FROM customers " +
                "WHERE id = ?"),

        UPDATE("UPDATE customers " +
                "SET first_name = ?, last_name = ?, company = ?, address = ? " +
                "WHERE id = ?"),

        DELETE_BY_ID("DELETE FROM customers WHERE id = ?"),

        DELETE_BY_FULLNAME("DELETE FROM customers WHERE first_name = ? AND last_name = ?"),

        COUNT("SELECT count(id) FROM customers");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
