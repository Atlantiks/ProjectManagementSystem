package ua.com.goit.dao;

import ua.com.goit.entity.Developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class DeveloperDao implements DataAccess<Integer, Developer> {
    private Connection connection;


    @Override
    public Developer getById(Integer integer) {
        return null;
    }

    @Override
    public Developer save(Developer developer) {
        var query = SQL.INSERT.command;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,developer.getFirstName());
            statement.setString(2,developer.getLastName());
            statement.setString(3,developer.getSex());
            statement.setObject(4,developer.getCompanyId());
            statement.setDouble(5,developer.getSalary());

            var x = statement.executeUpdate();


        } catch (Exception e) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public List<Developer> get() {
        return null;
    }

    @Override
    public boolean remove(Developer developer) {
        return false;
    }

    @Override
    public boolean removeById(Integer integer) {
        return false;
    }

    @Override
    public boolean update(Developer developer) {
        return false;
    }

    enum SQL {
        INSERT
                ("INSERT INTO developers (first_name, last_name, sex, company_id, salary) " +
                "VALUES (?,?,?,?,?);");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
