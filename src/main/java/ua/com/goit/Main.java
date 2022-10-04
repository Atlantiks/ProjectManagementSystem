package ua.com.goit;

import ua.com.goit.command.*;
import ua.com.goit.command.company.CreateCompany;
import ua.com.goit.command.company.DeleteCompanyById;
import ua.com.goit.command.customer.CreateCustomer;
import ua.com.goit.command.customer.DeleteCustomerById;
import ua.com.goit.command.developer.*;
import ua.com.goit.command.project.*;
import ua.com.goit.command.skill.CreateSkill;
import ua.com.goit.command.skill.GetAllSkills;
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
        commands.add(new FindDeveloperById(view));
        commands.add(new DeleteDeveloperById(view));
        commands.add(new GetDevelopersWithSkillName(view));
        commands.add(new GetDevelopersWithSkillLevel(view));
        commands.add(new CreateDeveloper(view));

        commands.add(new ProjectMenu(view));
        commands.add(new CreateProject(view));
        commands.add(new FindProjectById(view));
        commands.add(new DeleteProjectById(view));
        commands.add(new GetDevelopersList(view));
        commands.add(new GetDevelopersSalary(view));
        commands.add(new GetProjectInfo(view));

        commands.add(new SkillMenu(view));
        commands.add(new CreateSkill(view));
        commands.add(new GetAllSkills(view));

        commands.add(new CompanyMenu(view));
        commands.add(new CreateCompany(view));
        commands.add(new DeleteCompanyById(view));

        commands.add(new CustomerMenu(view));
        commands.add(new CreateCustomer(view));
        commands.add(new DeleteCustomerById(view));
    }
}
