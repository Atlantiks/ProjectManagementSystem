package ua.com.goit.command.project;

import ua.com.goit.command.Command;
import ua.com.goit.service.ProjectService;

public class FindProjectById implements Command {
    public static final String FIND_PROJECT_BY_ID = "find project by id";
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return FIND_PROJECT_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        PROJECT_SERVICE.findProjectById();
    }
}
