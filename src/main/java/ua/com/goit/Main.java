package ua.com.goit;

import ua.com.goit.command.*;
import ua.com.goit.command.company.CreateCompany;
import ua.com.goit.command.company.DeleteCompanyById;
import ua.com.goit.command.company.FindCompanyById;
import ua.com.goit.command.customer.CreateCustomer;
import ua.com.goit.command.customer.DeleteCustomerById;
import ua.com.goit.command.customer.FindCustomerById;
import ua.com.goit.command.developer.*;
import ua.com.goit.command.project.*;
import ua.com.goit.command.skill.AssignNewSkillToDeveloper;
import ua.com.goit.command.skill.CreateSkill;
import ua.com.goit.command.skill.ViewSkills;
import ua.com.goit.controller.ProjectManagementSystem;
import ua.com.goit.dao.*;
import ua.com.goit.service.*;
import ua.com.goit.view.Console;
import ua.com.goit.view.View;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        View view = new Console(scanner);
        List<Command> commands = new ArrayList<>();

        initializeServices(view);
        initializeCommands(view, commands);

        ProjectManagementSystem pms = new ProjectManagementSystem(view,commands);

        var hibernateSession = connectionManager.getHibernateSession();

        var byIdWithHibernate = DeveloperDao.getInstance().findByIdWithHibernate(1);

        pms.run();

        connectionManager.closeConnectionPool();
    }

    private static void initializeServices(View view) {
        DeveloperService.getInstance().setView(view);
        ProjectService.getInstance().setView(view);
        CompanyService.getInstance().setView(view);
        CustomerService.getInstance().setView(view);
        SkillService.getInstance().setView(view);
    }
    private static void initializeCommands(View view, List<Command> commands) {
        commands.add(new Help(view));
        commands.add(new Exit());

        commands.add(new DeveloperMenu(view));
        commands.add(new FindDeveloperById());
        commands.add(new DeleteDeveloperById());
        commands.add(new GetDevelopersWithSkillName());
        commands.add(new GetDevelopersWithSkillLevel());
        commands.add(new CreateDeveloper());

        commands.add(new ProjectMenu(view));
        commands.add(new CreateProject());
        commands.add(new FindProjectById());
        commands.add(new DeleteProjectById());
        commands.add(new GetDevelopersList());
        commands.add(new GetDevelopersSalary());
        commands.add(new GetProjectInfo());

        commands.add(new SkillMenu(view));
        commands.add(new CreateSkill());
        commands.add(new AssignNewSkillToDeveloper());
        commands.add(new ViewSkills());

        commands.add(new CompanyMenu(view));
        commands.add(new CreateCompany());
        commands.add(new FindCompanyById());
        commands.add(new DeleteCompanyById());

        commands.add(new CustomerMenu(view));
        commands.add(new CreateCustomer());
        commands.add(new FindCustomerById());
        commands.add(new DeleteCustomerById());
    }
}
