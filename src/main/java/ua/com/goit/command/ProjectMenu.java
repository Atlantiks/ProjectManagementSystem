package ua.com.goit.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.developer.CreateDeveloper;
import ua.com.goit.command.project.GetDevelopersList;
import ua.com.goit.command.project.GetDevelopersSalary;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class ProjectMenu implements Command {
    public static final String PROJECT_COMMANDS = "project service";
    @NonNull private View view;

    @Override
    public boolean canBeExecuted(String input) {
        return PROJECT_COMMANDS.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("===========PROJECT SERVICE MENU===========");
        view.write(String.format("Type %s to add new project", "\033[0;93m" + CreateDeveloper.CREATE_DEV + "\033[0m"));
        view.write(String.format("Type %s and then provide project id", "\033[0;93m" + GetDevelopersList.GET_DEVS_LIST + "\033[0m"));
        view.write(String.format("Type %s and then provide project id", "\033[0;93m" + GetDevelopersSalary.GET_DEVS_SALARY + "\033[0m"));
    }
}
