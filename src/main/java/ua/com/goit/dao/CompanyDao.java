package ua.com.goit.dao;

import ua.com.goit.entity.Company;
import ua.com.goit.entity.Project;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDao implements DataAccess<Integer, Company>{
    private final Connection connection;

    public CompanyDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Company> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Company save(Company company) {
        return null;
    }

    @Override
    public List<Company> findAll() {
        List<Company> allCompanies = new ArrayList<>();
        String query = SQL.SELECT_ALL.command;

        try (var statement = connection.prepareStatement(query)) {
            statement.executeQuery();

            var rs = statement.getResultSet();
            while (rs.next()) {
                allCompanies.add(new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("country",String.class))
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return allCompanies;
    }

    @Override
    public boolean remove(Company company) {
        return false;
    }

    @Override
    public boolean removeById(Integer integer) {
        return false;
    }

    @Override
    public boolean update(Company company) {
        return false;
    }

    @Override
    public int count() {
        try (PreparedStatement st = connection.prepareStatement(SQL.COUNT.command)) {
            if (st.executeQuery().next()) return st.getResultSet().getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return 0;
    }

    enum SQL {
        INSERT("INSERT INTO companies (name, country) " +
                "VALUES (?,?)"),

        SELECT_ALL ("SELECT id, name, country " +
                "FROM companies " +
                "ORDER BY id"),

        SELECT_BY_ID("SELECT id, name, country FROM companies " +
                "WHERE id = ?"),

        UPDATE("UPDATE companies " +
                "SET name = ?, country = ? " +
                "WHERE id = ?"),

        DELETE_BY_ID("DELETE FROM companies WHERE id = ?"),

        DELETE_BY_NAME("DELETE FROM companies WHERE name = ?"),

        COUNT("SELECT count(id) FROM companies");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
