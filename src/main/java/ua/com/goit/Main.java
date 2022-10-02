package ua.com.goit;

import ua.com.goit.command.Command;
import ua.com.goit.command.DeveloperMenu;
import ua.com.goit.command.Help;
import ua.com.goit.command.ProjectMenu;
import ua.com.goit.command.developer.CreateDeveloper;
import ua.com.goit.command.developer.GetDevelopersWithSkillLevel;
import ua.com.goit.command.developer.GetDevelopersWithSkillName;
import ua.com.goit.command.project.GetDevelopersList;
import ua.com.goit.command.project.GetDevelopersSalary;
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

        initializeCommands(view, commands);

        ProjectManagementSystem pms = new ProjectManagementSystem(view,commands);

        pms.run();

        ConnectionManager connectionManager = ConnectionManager.getInstance();

        ProjectDao projectDao = ProjectDao.getInstance();
        CompanyDao companyDao = CompanyDao.getInstance();
        CustomerDao customerDao = CustomerDao.getInstance();
        SkillDao skillDao = SkillDao.getInstance();

        //task #1

        var devsInProject = projectDao.getListOfInvolvedDevelopers(1);
        System.out.printf("Список всех разработчиков проекта с id = %s\n", "\033[0;93m" + 1 + "\033[0m");
        devsInProject.forEach(System.out::println);


        //task #5
        projectDao.printProjectInfo();

        connectionManager.closeConnectionPool();
    }

    private static void initializeCommands(View view, List<Command> commands) {
        commands.add(new Help(view));

        commands.add(new DeveloperMenu(view));
        commands.add(new GetDevelopersWithSkillName(view));
        commands.add(new GetDevelopersWithSkillLevel(view));
        commands.add(new CreateDeveloper(view));

        commands.add(new ProjectMenu(view));
        commands.add(new GetDevelopersList(view));
        commands.add(new GetDevelopersSalary(view));

    }

}
