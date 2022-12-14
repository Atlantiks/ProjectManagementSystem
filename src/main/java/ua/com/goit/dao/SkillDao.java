package ua.com.goit.dao;

import jakarta.persistence.PersistenceException;
import ua.com.goit.entity.Developer;
import ua.com.goit.entity.Skill;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.view.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class SkillDao implements DataAccess<Integer, Skill> {
    private static final SkillDao SKILL_DAO = new SkillDao();
    private static final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private static final DeveloperDao developerDao = DeveloperDao.getInstance();

    private SkillDao() {
    }

    public static SkillDao getInstance() {
        return SKILL_DAO;
    }

    @Override
    public Optional<Skill> findById(Integer id) {
        String query = SQL.SELECT_BY_ID.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
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

    public Optional<Skill> findByIdWithHibernate(Integer id) {
        return Optional.ofNullable(connectionManager.getHibernateSession().get(Skill.class, id));
    }

    @Override
    public Skill save(Skill skill) {
        String query = SQL.INSERT.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, skill.getName());
            statement.setObject(2, skill.getLevel());

            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    skill.setId(generatedKey.getInt(1));
                } else {
                    throw new DataBaseOperationException("No id was returned back.");
                }
            } catch (SQLException e) {
                throw new DataBaseOperationException("Couldn't create new skill in database");
            }

        } catch (SQLException e) {
            throw new DataBaseOperationException("Couldn't create new skill in database");
        }
        return skill;
    }

    public Skill saveWithHibernate(Skill skill) {
        var hibernateSession = connectionManager.getHibernateSession();

        try {
            hibernateSession.beginTransaction();
            hibernateSession.persist(skill);
            hibernateSession.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DataBaseOperationException("Error occurred adding new skill");
        }

        return skill;
    }

    @Override
    public List<Skill> findAll() {
        List<Skill> allSkills = new ArrayList<>();
        String query = SQL.SELECT_ALL.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
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

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, skill.getName());
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
    public boolean update(Skill skill) {
        String query = SQL.UPDATE.command;
        int updatedRows;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, skill.getName());
            statement.setString(2, skill.getLevel());
            statement.setInt(3, skill.getId());

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

    public List<Developer> getDevelopersWithSkillName(String skillName) {
        String query = SQL.GET_ALL_DEVS_BY_SKILL_NAME.command;
        List<Integer> developersIds = new ArrayList<>();
        List<Developer> devs = new ArrayList<>();

        try (var connection = connectionManager.getConnection();
             var st = connection.prepareStatement(query)) {
            st.setString(1, skillName);
            var resultSet = st.executeQuery();

            while (resultSet.next()) {
                developersIds.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        developersIds.stream().map(developerDao::findById)
                .forEach(developer -> developer.ifPresent(devs::add));

        if (devs.size() == 0)
            System.out.printf("\u001B[0;91m?????????????????????????? ???? ?????????????? \"%s\" ???? ?????????????? \u001B[0m\n", skillName);

        return devs;
    }

    public List<Developer> getDevelopersWithSkillLevel(String skillLevel) {
        String query = SQL.GET_ALL_DEVS_BY_SKILL_LEVEL.command;
        List<Integer> developersIds = new ArrayList<>();
        List<Developer> devs = new ArrayList<>();

        try (var connection = connectionManager.getConnection();
             var st = connection.prepareStatement(query)) {
            st.setString(1, skillLevel);
            var resultSet = st.executeQuery();

            while (resultSet.next()) {
                developersIds.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        developersIds.stream().map(developerDao::findById)
                .forEach(developer -> developer.ifPresent(devs::add));

        if (devs.size() == 0)
            System.out.printf("\u001B[0;91m?????????????????????????? ???????????? \"%s\" ???? ?????????????? \u001B[0m\n", skillLevel);

        return devs;
    }

    public boolean assignSkillToDev(Integer devId, Integer skillId) {
        String query = SQL.ADD_SKILL_TO_DEV.command;
        int insertedRows = 0;

        try (var connection = connectionManager.getConnection();
             var st = connection.prepareStatement(query)) {
            st.setInt(1, devId);
            st.setInt(2, skillId);

            insertedRows = st.executeUpdate();

        } catch (SQLException e) {
            return false;
        }

        return insertedRows > 0;
    }

    public List<Skill> findSkillsOfDeveloperWithName(String fullname) {
        String query = SQL.GET_ALL_SKILLS_OF_DEV.command;
        List<Skill> devSkills = new ArrayList<>();

        try (var connection = connectionManager.getConnection();
             var st = connection.prepareStatement(query)) {
            st.setString(1, fullname);
            var resultSet = st.executeQuery();

            while (resultSet.next()) {
                devSkills.add(
                        Skill.builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .level(resultSet.getString(3))
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new DataBaseOperationException(e.getMessage());
        }

        return devSkills;
    }

    enum SQL {
        INSERT("INSERT INTO skills (name, level) " +
                "VALUES (?,?)"),

        SELECT_ALL("SELECT id, name, level " +
                "FROM skills " +
                "ORDER BY id"),

        SELECT_BY_ID("SELECT id, name, level FROM skills " +
                "WHERE id = ?"),

        UPDATE("UPDATE skills " +
                "SET name = ?, level = ? " +
                "WHERE id = ?"),

        DELETE_BY_ID("DELETE FROM skills WHERE id = ?"),

        DELETE_BY_NAME("DELETE FROM skills WHERE name = ?"),

        COUNT("SELECT count(id) FROM skills"),

        GET_ALL_DEVS_BY_SKILL_NAME("SELECT d.id\n" +
                "FROM developers d\n" +
                "LEFT JOIN developers_skills ds on d.id = ds.developers_id\n" +
                "LEFT JOIN skills s on ds.skill_id = s.id\n" +
                "WHERE s.name = ?"),

        GET_ALL_DEVS_BY_SKILL_LEVEL("SELECT DISTINCT d.id\n" +
                "FROM developers d\n" +
                "LEFT JOIN developers_skills ds on d.id = ds.developers_id\n" +
                "LEFT JOIN skills s on ds.skill_id = s.id\n" +
                "WHERE s.level = ?"),

        ADD_SKILL_TO_DEV("INSERT INTO developers_skills (developers_id, skill_id)\n" +
                "VALUES (?, ?)"),

        GET_ALL_SKILLS_OF_DEV("SELECT s.id, s.name, s.level\n" +
                "FROM developers d\n" +
                "JOIN developers_skills ds on d.id = ds.developers_id\n" +
                "JOIN skills s on ds.skill_id = s.id\n" +
                "WHERE CONCAT(d.first_name, ' ', d.last_name) = ?");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
