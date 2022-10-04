package ua.com.goit.command.project;

import ua.com.goit.command.Command;
import ua.com.goit.service.ProjectService;

public class GetProjectInfo implements Command {
    public static final String GET_INFO = "get projects info";
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return GET_INFO.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        PROJECT_SERVICE.getProjectInfo();
    }
}
