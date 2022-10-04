package ua.com.goit.command.project;

import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.service.ProjectService;

@RequiredArgsConstructor
public class DeleteProjectById implements Command {
    public static final String DEL_PROJECT_BY_ID = "delete project by id";
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return DEL_PROJECT_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        PROJECT_SERVICE.deleteProjectById();
    }
}
