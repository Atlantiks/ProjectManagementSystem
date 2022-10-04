package ua.com.goit.command.project;

import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.service.ProjectService;

@RequiredArgsConstructor
public class GetDevelopersList implements Command {
    public static final String GET_DEVS_LIST = "get developers list";
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return GET_DEVS_LIST.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        PROJECT_SERVICE.getDevelopersList();
    }
}
