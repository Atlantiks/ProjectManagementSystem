package ua.com.goit.dao;

import ua.com.goit.entity.Company;
import ua.com.goit.view.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CompanyDao implements DataAccess<Integer, Company> {
    private static final CompanyDao COMPANY_DAO = new CompanyDao();
    private static final ConnectionManager connectionManager = ConnectionManager.getInstance();

    private CompanyDao() {

    }

    public static CompanyDao getInstance() {
        return COMPANY_DAO;
    }

    @Override
    public Optional<Company> findById(Integer id) {
        String query = SQL.SELECT_BY_ID.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query);) {
            statement.setInt(1, id);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()) {
                return Optional.of(new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("country", String.class)
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Company save(Company company, View view) {
        String query = SQL.INSERT.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, company.getName());
            statement.setObject(2, company.getCountry());

            int resultRows = statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    company.setId(generatedKey.getInt(1));
                } else {
                    throw new RuntimeException("No id was returned back.");
                }
            } catch (SQLException e) {
                view.write("Couldn't create new company in database");
                view.write(e.getMessage());
            }

        } catch (SQLException e) {
            view.write("Couldn't create new company in database");
            view.write(e.getMessage());
        }
        return company;
    }

    @Override
    public List<Company> findAll() {
        List<Company> allCompanies = new ArrayList<>();
        String query = SQL.SELECT_ALL.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.executeQuery();

            var rs = statement.getResultSet();
            while (rs.next()) {
                allCompanies.add(new Company(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("country", String.class))
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

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, company.getName());
            updatedRows = statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return updatedRows > 0;
    }

    @Override
    public boolean removeById(Integer id) {
        String query = SQL.DELETE_BY_ID.command;
        int result = 0;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

    @Override
    public boolean update(Company company) {
        var query = SQL.UPDATE.command;
        int updatedRows;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, company.getName());
            statement.setObject(2, company.getCountry(), Types.VARCHAR);

            statement.setInt(3, company.getId());

            updatedRows = statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return updatedRows > 0;
    }

    @Override
    public int count() {
        try (var connection = connectionManager.getConnection();
             var st = connection.prepareStatement(SQL.COUNT.command)) {
            if (st.executeQuery().next()) return st.getResultSet().getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return 0;
    }

    public Optional<Integer> getCompanyIdFromCompanyName(String companyName) {
        var query = SQL.GET_COMPANY_ID_BY_ITS_NAME.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, companyName);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) return Optional.of(resultSet.getObject(1,Integer.class));

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return Optional.empty();
    }

    enum SQL {
        INSERT("INSERT INTO companies (name, country) " +
                "VALUES (?,?)"),

        SELECT_ALL("SELECT id, name, country " +
                "FROM companies " +
                "ORDER BY id"),

        SELECT_BY_ID("SELECT id, name, country FROM companies " +
                "WHERE id = ?"),

        UPDATE("UPDATE companies " +
                "SET name = ?, country = ? " +
                "WHERE id = ?"),

        DELETE_BY_ID("DELETE FROM companies WHERE id = ?"),

        DELETE_BY_NAME("DELETE FROM companies WHERE name = ?"),

        COUNT("SELECT count(id) FROM companies"),

        GET_COMPANY_ID_BY_ITS_NAME("SELECT id, name FROM companies " +
                "WHERE name = ?");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
