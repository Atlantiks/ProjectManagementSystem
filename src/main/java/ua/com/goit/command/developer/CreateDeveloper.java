package ua.com.goit.command.developer;

import ua.com.goit.command.Command;
import ua.com.goit.service.DeveloperService;

public class CreateDeveloper implements Command {
    public static final String CREATE_DEV = "create developer";
    private final DeveloperService DEVELOPER_SERVICE = DeveloperService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_DEV.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        DEVELOPER_SERVICE.createDeveloper();
    }
}
