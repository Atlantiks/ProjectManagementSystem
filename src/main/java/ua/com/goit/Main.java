package ua.com.goit;

import ua.com.goit.dao.*;
import ua.com.goit.entity.*;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        var connection = connectionManager.getConnection();

        DataAccess<Integer, Developer> devDao = new DeveloperDao(connection);
        DataAccess<Integer, Project> projectDao = new ProjectDao(connection);
        DataAccess<Integer, Company> companyDao = new CompanyDao(connection);
        DataAccess<Integer, Customer> customerDao = new CustomerDao(connection);
        DataAccess<Integer, Skill> skillDao = new SkillDao(connection);

        for (int i = 0; i < 22; i++) {
            projectDao.removeById(i);
        }

        projectDao.findAll().forEach(System.out::println);
        connectionManager.closeConnectionPool();
    }

}
