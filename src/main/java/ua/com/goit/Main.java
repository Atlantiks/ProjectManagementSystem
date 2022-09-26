package ua.com.goit;

import ua.com.goit.dao.*;
import ua.com.goit.entity.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        DeveloperDao devDao = new DeveloperDao(connectionManager);

        ProjectDao projectDao = new ProjectDao(connectionManager, devDao);
        CompanyDao companyDao = new CompanyDao(connectionManager);
        CustomerDao customerDao = new CustomerDao(connectionManager);
        SkillDao skillDao = new SkillDao(connectionManager, devDao);

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
