package ua.com.goit.dao;

import ua.com.goit.entity.Developer;
import ua.com.goit.entity.Project;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ProjectDao implements DataAccess<Integer, Project> {
    private static final ProjectDao PROJECT_DAO = new ProjectDao();
    private static final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private static final DeveloperDao developerDao = DeveloperDao.getInstance();

    private ProjectDao() {
    }

    public static ProjectDao getInstance() {
        return PROJECT_DAO;
    }

    @Override
    public Optional<Project> findById(Integer id) {
        String query = SQL.SELECT_BY_ID.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()) {
                return Optional.of(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("date_created", LocalDate.class),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getBigDecimal("cost")
                        ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Project save(Project project) {
        String query = SQL.INSERT.command;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.executeQuery();

            var rs = statement.getResultSet();
            while (rs.next()) {
                allProjects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("date_created", LocalDate.class),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getObject("cost", BigDecimal.class)
                ));
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

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
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
    public boolean update(Project project) {
        var query = SQL.UPDATE.command;
        int updatedRows = 0;

        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(query)) {
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
        try (var connection = connectionManager.getConnection();
             var st = connection.prepareStatement(query)) {
            if (st.executeQuery().next()) return st.getResultSet().getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return 0;
    }

    public List<Developer> getListOfInvolvedDevelopers(Integer id) {
        String query = SQL.GET_ALL_DEVS.command;
        List<Integer> developersIds = new ArrayList<>();
        List<Developer> devs = new ArrayList<>();

        try (var connection = connectionManager.getConnection();
             var st  = connection.prepareStatement(query)) {
            st.setInt(1,id);
            var resultSet = st.executeQuery();

            while (resultSet.next()) {
                developersIds.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        developersIds.stream().map(developerDao::findById)
                .forEach(developer -> developer.ifPresent(devs::add));

        if (devs.size() == 0) System.out.printf("Разработчиков проекта с id = %d не найдено\n", id);
        return devs;
    }

    public void printProjectInfo() {
        String query = SQL.GET_INFO.command;

        try (var connection = connectionManager.getConnection();
             var st  = connection.prepareStatement(query)) {
            var resultSet = st.executeQuery();

            if (resultSet.next()) {
                System.out.println("\nДата создания - название проекта - количество разработчиков на этом проекте.");
                System.out.print(resultSet.getDate(1).toLocalDate() + " - ");
                System.out.print(resultSet.getString(2) + " - ");
                System.out.print(resultSet.getInt(3) + "\n");
            } else {
                System.out.println("Информация не найдена");
            }

            while (resultSet.next()) {
                System.out.print(resultSet.getDate(1).toLocalDate() + " - ");
                System.out.print(resultSet.getString(2) + " - ");
                System.out.print(resultSet.getInt(3) + "\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
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

        COUNT("SELECT count(id) FROM projects"),

        GET_ALL_DEVS("SELECT developers_id\n" +
                "FROM projects\n" +
                "LEFT JOIN projects_developers pd on projects.id = pd.projects_id\n" +
                "LEFT JOIN developers d on pd.developers_id = d.id\n" +
                "WHERE projects_id = ?"),

        GET_INFO("SELECT p.date_created, p.name, count(d.id) as number_of_developers\n" +
                "FROM projects p\n" +
                "LEFT JOIN projects_developers pd on p.id = pd.projects_id\n" +
                "LEFT JOIN developers d on pd.developers_id = d.id\n" +
                "GROUP BY p.date_created, p.name");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
