package ua.com.goit.dao;

import ua.com.goit.entity.Customer;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CustomerDao implements DataAccess<Integer, Customer> {
    private final Connection connection;

    public CustomerDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public boolean remove(Customer customer) {
        return false;
    }

    @Override
    public boolean removeById(Integer id) {
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    enum SQL {
        INSERT("INSERT INTO customers (first_name, last_name, company, address) " +
                "VALUES (?,?,?,?)"),

        SELECT_ALL ("SELECT id, first_name, last_name, company, address" +
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
