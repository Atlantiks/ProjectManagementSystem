package ua.com.goit;

import ua.com.goit.dao.*;
import ua.com.goit.entity.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        var connection = connectionManager.getConnection();

        DeveloperDao devDao = new DeveloperDao(connection);

        ProjectDao projectDao = new ProjectDao(connection, devDao);
        CompanyDao companyDao = new CompanyDao(connection);
        CustomerDao customerDao = new CustomerDao(connection);
        SkillDao skillDao = new SkillDao(connection, devDao);

        //task #1
        var devsInProject = projectDao.getListOfInvolvedDevelopers(6);

        //task #1
        System.out.println(devsInProject.stream().map(Developer::getSalary)
                .reduce(BigDecimal.valueOf(0.0), BigDecimal::add));


        var x = skillDao.getDevelopersWithSkillName("Java");
        x.forEach(System.out::println);

        var y = skillDao.getDevelopersWithSkillLevel("Star");
        y.forEach(System.out::println);

        projectDao.printProjectInfo();

        connectionManager.closeConnectionPool();
    }

}
