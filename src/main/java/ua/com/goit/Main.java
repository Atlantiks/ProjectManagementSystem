package ua.com.goit;

import ua.com.goit.dao.*;
import ua.com.goit.entity.Company;
import ua.com.goit.entity.Developer;
import ua.com.goit.entity.Project;

import java.util.List;
import java.util.Optional;


public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DataAccess<Integer, Developer> devDao = new DeveloperDao(connectionManager.getConnection());
        DataAccess<Integer, Project> projectDao = new ProjectDao(connectionManager.getConnection());
        DataAccess<Integer, Company> companyDao = new CompanyDao(connectionManager.getConnection());

        companyDao.findAll().stream().forEach(System.out::println);

    }

}
