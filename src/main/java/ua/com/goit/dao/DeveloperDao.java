package ua.com.goit.dao;

import ua.com.goit.entity.Developer;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDao implements DataAccess<Integer, Developer> {
    private Connection connection;


    public DeveloperDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Developer getById(Integer id) {
        Developer dev = null;
        String query = SQL.SELECT_BY_ID.command;

        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()) {
                dev = new Developer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("sex"),
                        rs.getObject("company_id",Integer.class),
                        rs.getObject("salary", BigDecimal.class));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return dev;
    }

    @Override
    public Developer save(Developer developer) {
        var query = SQL.INSERT.command;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,developer.getFirstName());
            statement.setString(2,developer.getLastName());
            statement.setString(3,developer.getSex());
            statement.setObject(4,developer.getCompanyId());
            statement.setBigDecimal(5,developer.getSalary());

            var x = statement.executeUpdate();

            try(ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    developer.setId(generatedKey.getInt(1));
                } else {
                    throw new RuntimeException("No id returned back.");
                }
            } catch (SQLException e) {
                System.out.println("Couldn't create new developer in database");
                throw new RuntimeException(e.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return developer;
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> allDevs = new ArrayList<>();
        String query = SQL.SELECT_ALL.command;

        try (var statement = connection.prepareStatement(query)) {
            statement.executeQuery();

            var rs = statement.getResultSet();
            while (rs.next()) {
                allDevs.add(new Developer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("sex"),
                        rs.getObject("company_id",Integer.class),
                        rs.getObject("salary", BigDecimal.class)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return allDevs;
    }

    @Override
    public boolean remove(Developer developer) {
        return false;
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
    public boolean update(Developer developer) {
        var query = SQL.UPDATE.command;
        int updatedRows = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,developer.getFirstName());
            statement.setString(2,developer.getLastName());
            statement.setString(3,developer.getSex());
            statement.setObject(4,developer.getCompanyId(),Types.INTEGER);
            statement.setBigDecimal(5,developer.getSalary());

            statement.setInt(6,developer.getId());

            updatedRows = statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return updatedRows > 0;
    }

    enum SQL {
        INSERT("INSERT INTO developers (first_name, last_name, sex, company_id, salary) " +
                "VALUES (?,?,?,?,?);"),
        SELECT_ALL ("SELECT id, first_name, last_name, sex, company_id, salary " +
                "FROM developers " +
                "ORDER BY id"),

        SELECT_BY_ID("SELECT id, first_name, last_name, sex, company_id, salary FROM developers " +
                "WHERE id = ?;"),

        UPDATE("UPDATE developers " +
                "SET first_name = ?, last_name = ?, sex = ?, company_id = ?, salary = ? " +
                "WHERE id = ?;"),

        DELETE_BY_ID("DELETE FROM developers WHERE id = ?;");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
