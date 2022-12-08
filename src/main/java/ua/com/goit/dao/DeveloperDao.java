package ua.com.goit.dao;

import org.hibernate.Session;
import ua.com.goit.entity.Customer;
import ua.com.goit.entity.Developer;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.view.View;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class DeveloperDao implements DataAccess<Integer, Developer> {
    private static final DeveloperDao DEV_DAO = new DeveloperDao();
    private static final ConnectionManager connectionManager = ConnectionManager.getInstance();

    private DeveloperDao() {
    }

    public static DeveloperDao getInstance() {
        return DEV_DAO;
    }

    @Override
    public Optional<Developer> findById(Integer id) {
        String query = SQL.SELECT_BY_ID.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()) {
                return Optional.of(new Developer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("sex"),
                        null,
                        //rs.getObject("company_id", Integer.class),
                        rs.getObject("salary", BigDecimal.class),
                        null));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.empty();
    }

    public Optional<Developer> findByIdWithHibernate(Integer id) {
        return Optional.ofNullable(connectionManager.getHibernateSession().get(Developer.class, id));
    }

    public Optional<Developer> findByFullName(String fullname) {
        String query = SQL.SELECT_BY_FULLNAME.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, fullname);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()) {
                return Optional.of(new Developer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("sex"),
                        null,
                        //rs.getObject("company_id", Integer.class),
                        rs.getObject("salary", BigDecimal.class),
                        null));
            }
        } catch (Exception e) {
            throw new DataBaseOperationException(e.getMessage());
        }

        return Optional.empty();
    }


    @Override
    public Developer save(Developer developer) {
        String query = SQL.INSERT.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setString(3, developer.getSex());
            //statement.setObject(4, developer.getCompanyId());
            statement.setBigDecimal(5, developer.getSalary());

            int updatedRows = statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    developer.setId(generatedKey.getInt(1));
                } else {
                    throw new DataBaseOperationException("No id was returned back for new Developer.");
                }
            } catch (SQLException e) {
                throw new DataBaseOperationException("Couldn't create new developer in database");
            }

        } catch (SQLException e) {
            throw new DataBaseOperationException("Couldn't create new developer in database");
        }
        return developer;
    }

    public Developer saveWithHibernate(Developer developer) {
        var x = connectionManager.getHibernateSession();

        x.beginTransaction();

        x.persist(developer);

        x.getTransaction().commit();

        return developer;
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> allDevs = new ArrayList<>();
        String query = SQL.SELECT_ALL.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.executeQuery();

            var rs = statement.getResultSet();
            while (rs.next()) {
                allDevs.add(Developer.builder()
                        .id(rs.getInt("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .sex(rs.getString("sex"))
                        .salary(rs.getObject("salary", BigDecimal.class))
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return allDevs;
    }

    @Override
    public boolean remove(Developer developer) {
        String query = SQL.DELETE.command;

        int updatedRows = 0;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());

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
    public boolean update(Developer developer) {
        String query = SQL.UPDATE.command;
        int updatedRows = 0;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setString(3, developer.getSex());
            // statement.setObject(4, developer.getCompanyId(), Types.INTEGER);
            statement.setBigDecimal(5, developer.getSalary());

            statement.setInt(6, developer.getId());

            updatedRows = statement.executeUpdate();

        } catch (Exception e) {
            throw new DataBaseOperationException(e.getMessage());
        }
        return updatedRows > 0;
    }

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
        INSERT("INSERT INTO developers (first_name, last_name, sex, company_id, salary) " +
                "VALUES (?,?,?,?,?)"),

        SELECT_ALL("SELECT id, first_name, last_name, sex, company_id, salary " +
                "FROM developers " +
                "ORDER BY id"),

        SELECT_BY_ID("SELECT id, first_name, last_name, sex, company_id, salary FROM developers " +
                "WHERE id = ?"),

        SELECT_BY_FULLNAME("SELECT id, first_name, last_name, sex, company_id, salary FROM developers d " +
                "WHERE CONCAT(d.first_name, ' ',d.last_name) = ?"),

        UPDATE("UPDATE developers " +
                "SET first_name = ?, last_name = ?, sex = ?, company_id = ?, salary = ? " +
                "WHERE id = ?"),

        DELETE_BY_ID("DELETE FROM developers WHERE id = ?"),

        DELETE("DELETE FROM developers " +
                "WHERE first_name = ? AND last_name = ?"),

        COUNT("SELECT count(id) FROM developers");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
