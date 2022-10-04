package ua.com.goit.command.project;

import ua.com.goit.command.Command;
import ua.com.goit.service.ProjectService;

public class GetDevelopersSalary implements Command {
    public static final String GET_DEVS_SALARY = "get salary of involved developers";
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return GET_DEVS_SALARY.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        PROJECT_SERVICE.getDevelopersSalary();
    }
}
