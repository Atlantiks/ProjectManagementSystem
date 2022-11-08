package ua.com.goit.command.project;

import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.service.ProjectService;

@RequiredArgsConstructor
public class CreateProject implements Command {
    public static final String CREATE_PROJECT = "create project";
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_PROJECT.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        PROJECT_SERVICE.createProject();
    }
}
