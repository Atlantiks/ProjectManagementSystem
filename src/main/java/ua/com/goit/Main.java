package ua.com.goit;

import ua.com.goit.dao.*;
import ua.com.goit.entity.*;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        var connection = connectionManager.getConnection();

        DataAccess<Integer, Developer> devDao = new DeveloperDao(connection);
        DataAccess<Integer, Project> projectDao = new ProjectDao(connection);
        DataAccess<Integer, Company> companyDao = new CompanyDao(connection);
        DataAccess<Integer, Customer> customerDao = new CustomerDao(connection);
        DataAccess<Integer, Skill> skillDao = new SkillDao(connection);

        skillDao.update(new Skill(7,"C#","Founder"));

        skillDao.findAll().forEach(System.out::println);

    }

}
