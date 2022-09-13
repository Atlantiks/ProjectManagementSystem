package ua.com.goit.dao;

import ua.com.goit.entity.Project;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDao implements DataAccess<Integer, Project> {
    private final Connection connection;

    public ProjectDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Project> findById(Integer id) {
        Project project = null;
        String query = SQL.SELECT_BY_ID.command;

        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()) {
                project = new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("date_created", LocalDate.class),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getBigDecimal("cost")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.ofNullable(project);
    }

    @Override
    public Project save(Project project) {
        String query = SQL.INSERT.command;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,project.getName());
            statement.setObject(2,project.getDate_created());
            statement.setObject(3,project.getDescription());
            statement.setObject(4,project.getStatus());
            statement.setObject(5,project.getCost());

            int resultRows = statement.executeUpdate();

            try(ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    project.setId(generatedKey.getInt(1));
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
        return project;
    }

    @Override
    public List<Project> findAll() {
        List<Project> allProjects = new ArrayList<>();
        String query = SQL.SELECT_ALL.command;

        try (var statement = connection.prepareStatement(query)) {
            statement.executeQuery();

            var rs = statement.getResultSet();
            while (rs.next()) {
                allProjects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("date_created", LocalDate.class),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getObject("cost", BigDecimal.class)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return allProjects;
    }

    @Override
    public boolean remove(Project project) {
        String query = SQL.DELETE_BY_NAME.command;

        int updatedRows = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,project.getName());
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
    public boolean update(Project project) {
        var query = SQL.UPDATE.command;
        int updatedRows = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,project.getName());
            statement.setObject(2,project.getDate_created(),Types.DATE);
            statement.setObject(3,project.getDescription(),Types.VARCHAR);
            statement.setObject(4,project.getStatus(),Types.VARCHAR);
            statement.setObject(5,project.getCost(),Types.NUMERIC);

            statement.setInt(6,project.getId());

            updatedRows = statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return updatedRows > 0;
    }

    @Override
    public int count() {
        String query = SQL.COUNT.command;
        try (PreparedStatement st = connection.prepareStatement(query)) {
            if (st.executeQuery().next()) return st.getResultSet().getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return 0;
    }

    enum SQL {
        INSERT("INSERT INTO projects (name, date_created, description, status, cost) " +
                "VALUES (?,?,?,?,?)"),

        SELECT_ALL ("SELECT id, name, date_created, description, status, cost " +
                "FROM projects " +
                "ORDER BY id"),

        SELECT_BY_ID("SELECT id, name, date_created, description, status, cost FROM projects " +
                "WHERE id = ?"),

        UPDATE("UPDATE projects " +
                "SET name = ?, date_created = ?, description = ?, status = ?, cost = ? " +
                "WHERE id = ?"),

        DELETE_BY_ID("DELETE FROM projects WHERE id = ?"),

        DELETE_BY_NAME("DELETE FROM projects WHERE name = ?"),

        COUNT("SELECT count(id) FROM projects");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
