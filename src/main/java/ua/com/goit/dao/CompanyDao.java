package ua.com.goit.dao;

import ua.com.goit.entity.Company;
import ua.com.goit.entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDao implements DataAccess<Integer, Company> {
    private final Connection connection;

    public CompanyDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Company> findById(Integer id) {
        Company company = null;
        String query = SQL.SELECT_BY_ID.command;

        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()) {
                company = new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("country",String.class)
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.ofNullable(company);
    }

    @Override
    public Company save(Company company) {
        String query = SQL.INSERT.command;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1,company.getName());
            statement.setObject(2,company.getCountry());

            int resultRows = statement.executeUpdate();

            try(ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    company.setId(generatedKey.getInt(1));
                } else {
                    throw new RuntimeException("No id was returned back.");
                }
            } catch (SQLException e) {
                System.out.println("Couldn't create new project in database");
                throw new RuntimeException(e.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return company;
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
        String query = SQL.DELETE_BY_NAME.command;

        int updatedRows;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,company.getName());
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

        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result > 0;
    }

    @Override
    public boolean update(Company company) {
        var query = SQL.UPDATE.command;
        int updatedRows;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,company.getName());
            statement.setObject(2,company.getCountry(),Types.VARCHAR);

            statement.setInt(3,company.getId());

            updatedRows = statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return updatedRows > 0;
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
