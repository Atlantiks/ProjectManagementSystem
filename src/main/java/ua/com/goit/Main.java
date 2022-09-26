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
        Integer projectId = 1;
        var devsInProject = projectDao.getListOfInvolvedDevelopers(projectId);
        System.out.printf("Список всех разработчиков проекта с id = %s\n", "\033[0;93m" + projectId + "\033[0m");
        devsInProject.forEach(System.out::println);
        //task #2
        System.out.printf("\nЗарплата всех разработчиков проекта с id = %s : %s\n", "\033[0;93m" + projectId + "\033[0m",
                devsInProject.stream().map(Developer::getSalary)
                .reduce(BigDecimal.valueOf(0.0), BigDecimal::add));

        //task #3
        String skillName = "Java";
        System.out.printf("\nСписок всех разработчиков проекта cо знанием %s\n", "\033[0;93m" + skillName + "\033[0m");
        var x = skillDao.getDevelopersWithSkillName(skillName);
        x.forEach(System.out::println);

        //task #4
        String level = "middle";
        System.out.printf("\nСписок всех разработчиков уровня %s\n", "\033[0;93m" + level + "\033[0m");
        var y = skillDao.getDevelopersWithSkillLevel(level);
        y.forEach(System.out::println);

        //task #5
        projectDao.printProjectInfo();

        connectionManager.closeConnectionPool();
    }

}
