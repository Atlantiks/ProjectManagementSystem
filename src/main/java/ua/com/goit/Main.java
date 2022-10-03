package ua.com.goit;

import ua.com.goit.command.*;
import ua.com.goit.command.developer.CreateDeveloper;
import ua.com.goit.command.developer.GetDevelopersWithSkillLevel;
import ua.com.goit.command.developer.GetDevelopersWithSkillName;
import ua.com.goit.command.project.CreateProject;
import ua.com.goit.command.project.GetDevelopersList;
import ua.com.goit.command.project.GetDevelopersSalary;
import ua.com.goit.command.project.GetProjectInfo;
import ua.com.goit.controller.ProjectManagementSystem;
import ua.com.goit.dao.*;
import ua.com.goit.view.Console;
import ua.com.goit.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        View view = new Console(scanner);
        List<Command> commands = new ArrayList<>();

        initializeCommands(view, commands);

        ProjectManagementSystem pms = new ProjectManagementSystem(view,commands);

        pms.run();

        connectionManager.closeConnectionPool();
    }

    private static void initializeCommands(View view, List<Command> commands) {
        commands.add(new Help(view));
        commands.add(new Exit());

        commands.add(new DeveloperMenu(view));
        commands.add(new GetDevelopersWithSkillName(view));
        commands.add(new GetDevelopersWithSkillLevel(view));
        commands.add(new CreateDeveloper(view));

        commands.add(new ProjectMenu(view));
        commands.add(new CreateProject(view));
        commands.add(new GetDevelopersList(view));
        commands.add(new GetDevelopersSalary(view));
        commands.add(new GetProjectInfo(view));

    }

}
