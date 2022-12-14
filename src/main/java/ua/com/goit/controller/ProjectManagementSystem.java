package ua.com.goit.controller;

import lombok.AllArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ExitException;
import ua.com.goit.view.View;

import java.util.List;

@AllArgsConstructor
public class ProjectManagementSystem {
    private final View view;
    private final List<Command> commands;

    public void run() {
        view.write("Hello, please enter help to see all commands");
        try {
            execute();
        } catch (ExitException e) {
            view.write("Thanks for your visit! See you next time...");
        }
    }

    private void execute() {
        while (true) {
            String input = view.read();
            boolean isInputCorrect = false;
            for (Command command : commands) {
                if (command.canBeExecuted(input)) {
                    try {
                        command.execute();
                    } catch (NumberFormatException e) {
                        view.write("Incorrect input type. Please use numeric types accordingly");
                    } catch (BlancFieldException e) {
                        view.write("Empty fields are not allowed here");
                    } catch (NotFoundException e) {
                        view.write(e.getMessage());
                    }
                    isInputCorrect = true;
                }
            }
            if (!isInputCorrect) {
                view.write("Command not found/not recognised.");
            }

            view.write("Please enter next command:");
        }
    }
}
