package ua.com.goit;

import ua.com.goit.command.Command;
import ua.com.goit.command.DeveloperMenu;
import ua.com.goit.command.Help;
import ua.com.goit.controller.ProjectManagementSystem;
import ua.com.goit.dao.*;
import ua.com.goit.entity.*;
import ua.com.goit.view.Console;
import ua.com.goit.view.View;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        View view = new Console(scanner);
        List<Command> commands = new ArrayList<>();

        commands.add(new Help(view));
        commands.add(new DeveloperMenu(view));

        //commands.add(new CreateDeveloper(view));
        //commands.add(new GetDevelopersWithSkillLevel(view));

        ProjectManagementSystem pms = new ProjectManagementSystem(view,commands);

        pms.run();

        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DeveloperDao devDao = DeveloperDao.getInstance();
        ProjectDao projectDao = ProjectDao.getInstance();
        CompanyDao companyDao = CompanyDao.getInstance();
        CustomerDao customerDao = CustomerDao.getInstance();
        SkillDao skillDao = SkillDao.getInstance();

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
