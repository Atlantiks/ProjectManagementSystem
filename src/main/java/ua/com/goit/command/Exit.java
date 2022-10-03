package ua.com.goit.command;

import ua.com.goit.exception.ExitException;

public class Exit implements Command {
    private static final String HELP = "exit";


    @Override
    public boolean canBeExecuted(String input) {
        return HELP.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        throw new ExitException();
    }
}
