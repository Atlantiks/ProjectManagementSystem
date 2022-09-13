package ua.com.goit.dao;

import ua.com.goit.entity.Company;
import ua.com.goit.entity.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillDao implements DataAccess<Integer, Skill> {
    private Connection connection;

    public SkillDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Skill> findById(Integer id) {
        String query = SQL.SELECT_BY_ID.command;

        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()) {
                return Optional.of(new Skill(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("level")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Skill save(Skill skill) {
        String query = SQL.INSERT.command;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1,skill.getName());
            statement.setObject(2,skill.getLevel());

            statement.executeUpdate();

            try(ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    skill.setId(generatedKey.getInt(1));
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
        return skill;
    }

    @Override
    public List<Skill> findAll() {
        List<Skill> allSkills = new ArrayList<>();
        String query = SQL.SELECT_ALL.command;

        try (var statement = connection.prepareStatement(query)) {
            statement.executeQuery();

            var rs = statement.getResultSet();
            while (rs.next()) {
                allSkills.add(new Skill(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("level"))
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return allSkills;
    }

    @Override
    public boolean remove(Skill skill) {
        String query = SQL.DELETE_BY_NAME.command;

        int updatedRows;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,skill.getName());
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
    public boolean update(Skill skill) {
        String query = SQL.UPDATE.command;
        int updatedRows;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,skill.getName());
            statement.setString(2,skill.getLevel());
            statement.setInt(3,skill.getId());

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
        INSERT("INSERT INTO skills (name, level) " +
                "VALUES (?,?)"),

        SELECT_ALL ("SELECT id, name, level " +
                "FROM skills " +
                "ORDER BY id"),

        SELECT_BY_ID("SELECT id, name, level FROM skills " +
                "WHERE id = ?"),

        UPDATE("UPDATE skills " +
                "SET name = ?, level = ? " +
                "WHERE id = ?"),

        DELETE_BY_ID("DELETE FROM skills WHERE id = ?"),

        DELETE_BY_NAME("DELETE FROM skills WHERE name = ?"),

        COUNT("SELECT count(id) FROM skills");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
