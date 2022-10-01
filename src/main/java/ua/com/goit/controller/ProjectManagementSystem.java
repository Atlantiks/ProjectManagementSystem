package ua.com.goit.controller;

import lombok.AllArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.exception.ExitException;
import ua.com.goit.view.View;

import java.util.List;

@AllArgsConstructor
public class ProjectManagementSystem {
    private final View view;
    private final List<Command> commands;

    public void run() {
        view.write("Hello, please enter help to see all command");
        try {
            execute();
        } catch (ExitException e) {}
    }

    private void execute() {
        while (true) {
            String input = view.read();
            boolean isInputCorrect = false;
            for (Command command : commands) {
                if (command.canBeExecuted(input)) {
                    command.execute();
                    isInputCorrect = true;
                }
            }
            if (!isInputCorrect) {
                view.write("Command not found. Please enter help to see all commands");
            }
        }
    }
}
