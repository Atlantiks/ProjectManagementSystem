package ua.com.goit.command;

import lombok.AllArgsConstructor;
import ua.com.goit.view.View;

@AllArgsConstructor
public class Help implements Command {
    private static final String HELP = "help";
    private final View view;

    @Override
    public boolean canBeExecuted(String input) {
        return HELP.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("===========HELP MENU===========");
        view.write(String.format("Type %s to see tasks for developers", "\033[0;93m" + DeveloperMenu.DEV_COMMANDS + "\033[0m"));
        view.write(String.format("Type %s to see tasks for projects", "\033[0;93m" + ProjectMenu.PROJECT_COMMANDS + "\033[0m"));

        view.write(String.format("\n .. or type %s to quit application","\033[0;93m" + "exit" + "\033[0m"));
        view.write("===============================");
    }
}
