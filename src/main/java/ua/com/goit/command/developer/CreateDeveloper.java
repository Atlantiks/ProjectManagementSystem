package ua.com.goit.command.developer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import ua.com.goit.command.Command;
import ua.com.goit.service.DeveloperService;
import ua.com.goit.view.View;


@RequiredArgsConstructor
public class CreateDeveloper implements Command {
    public static final String CREATE_DEV = "create developer";
    private final DeveloperService DEVELOPER_SERVICE = DeveloperService.getInstance();
    @NonNull
    private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_DEV.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        DEVELOPER_SERVICE.createDeveloper();
    }
}
